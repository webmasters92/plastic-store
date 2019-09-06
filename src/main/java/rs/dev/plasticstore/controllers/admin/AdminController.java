package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.dev.plasticstore.model.Image;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.image.ImageService;
import rs.dev.plasticstore.services.product.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/")
public class AdminController {

    public static String rootDir = System.getProperty("user.dir");

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ImageService imageService;

    @GetMapping("/productList")
    public String showProductList(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        var products = productService.findAll();

        model.addAttribute("products", products);
        return "administration/product/adminProductList";
    }

    @GetMapping("/newProduct")
    public String getNewProduct(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", new Product());
        return "administration/product/adminProductNew";
    }

    @PostMapping("/saveProduct")
    public String addProduct(@ModelAttribute Product product) {
        AtomicBoolean update = new AtomicBoolean(false);
        product.getImgData().forEach(multipartFile -> {
            if(!multipartFile.isEmpty()) {
                var image = new Image();
                image.setName(multipartFile.getOriginalFilename());
                image.setUrl("/images/" + multipartFile.getOriginalFilename());
                image.setProduct(product);
                product.getImages().add(image);
                var imageFile = new File(rootDir + "/images", image.getName());
                try {
                    if(!imageFile.exists()) multipartFile.transferTo(imageFile);
                } catch(IOException e) {
                    e.printStackTrace();
                }
            } else update.set(true);
        });
        if(update.get()) product.getImages().addAll(productService.findProductById(product.getId()).get().getImages());
        productService.saveProduct(product);
        return "redirect:/newProduct";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable String id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", productService.findProductById(Integer.parseInt(id)).get());
        model.addAttribute("editing", true);
        return "administration/product/adminProductNew";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable String id) {
        var product = productService.findProductById(Integer.parseInt(id)).get();
        product.getImages().forEach(image -> {
            File imageFile = new File(rootDir + "/images", image.getName());
            if(imageFile.exists()) imageFile.delete();
        });
        productService.deleteProduct(Integer.parseInt(id));
        return "redirect:/productList";
    }
}