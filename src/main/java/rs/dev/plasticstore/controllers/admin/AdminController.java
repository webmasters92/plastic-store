package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

@Controller
@RequestMapping("/")
public class AdminController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ImageService imageService;

    @Value("${image.path}")
    String imagePath;

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

        var images = new ArrayList<Image>();
        product.getImgData().forEach(multipartFile -> {
            try {
                var image = new Image();
                image.setName(multipartFile.getOriginalFilename());
                image.setUrl(imagePath + "/" + multipartFile.getOriginalFilename());
                Files.write(Paths.get(imagePath + File.separator + multipartFile.getOriginalFilename()), multipartFile.getBytes());
                images.add(image);
                product.getImages().add(image);
            } catch(IOException e) {
                e.printStackTrace();
            }
        });

        productService.saveProduct(product);
        images.forEach(image -> imageService.saveImage(image));

        return "redirect:/newProduct";
    }

    @GetMapping("/editProduct/{id}")
    public String editProduct(@PathVariable String id, Model model) {

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", productService.findProductById(Integer.parseInt(id)));
        return "administration/product/adminProductNew";
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable String id, Model model) {

        productService.deleteProduct(Integer.parseInt(id));
        return "redirect:/productList";
    }

}