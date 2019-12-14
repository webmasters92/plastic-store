package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.dev.plasticstore.model.*;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.category.SubcategoryService;
import rs.dev.plasticstore.services.color.ColorService;
import rs.dev.plasticstore.services.image.ImageService;
import rs.dev.plasticstore.services.product.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/administration")
public class AdminController {

    public static String rootDir = System.getProperty("user.dir");

    @Autowired
    CategoryService categoryService;

    @Autowired
    SubcategoryService subcategoryService;

    @Autowired
    ProductService productService;

    @Autowired
    ColorService colorService;

    @Autowired
    ImageService imageService;

    @GetMapping("/product_list")
    public String showProductList(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findAll());
        return "administration/product/adminProductList";
    }

    @GetMapping("/new_product")
    public String getNewProduct(@RequestParam(value = "message", required = false) String message, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("allColors", colorService.findAll());
        model.addAttribute("product", new Product());
        model.addAttribute("message", message);
        return "administration/product/adminProductNew";
    }

    @PostMapping("/save_product")
    public String addProduct(@ModelAttribute Product product, BindingResult result, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("message", "Proizvod nije uspešno sačuvan");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        if(result.hasErrors()) return "redirect:/administration/new_product";

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

        if(update.get()) product.getImages().addAll(productService.findProductById(product.getId()).getImages());

        product.getSelectedColors().forEach(colorID -> {
            var color = colorService.findColorsById(Integer.parseInt(colorID));
            var productColor = new ProductColor();
            productColor.setCode(color.getCode());
            productColor.setName(color.getName());
            productColor.setProduct(product);
            product.getProductColors().add(productColor);
        });

        for(int i = 0; i < product.getSizes().size(); i++) {
            var product_attributes = new ProductAttributes();
            product_attributes.setSize(product.getSizes().get(i));
            product_attributes.setPrice(product.getPrices().get(i));
            if(product.getDiscounted_prices().size() > 0)
                product_attributes.setDiscounted_price(product.getDiscounted_prices().get(i));
            product.getProductAttributes().add(product_attributes);
        }

        productService.saveProduct(product);
        redirectAttributes.addAttribute("message", "Proizvod je uspešno sačuvan");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/administration/new_product";
    }

    @GetMapping("/edit_product/{id}")
    public String editProduct(@PathVariable String id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("allColors", colorService.findAll());
        var product = productService.findProductById(Integer.parseInt(id));
        product.getProductColors().forEach(productColor -> {
            var color = colorService.findColorsByName(productColor.getName());
            product.getSelectedColors().add(String.valueOf(color.getId()));
        });

        product.getProductAttributes().forEach(productAttributes -> {
            product.getSizes().add(productAttributes.getSize());
            product.getPrices().add(productAttributes.getPrice());
            product.getDiscounted_prices().add(productAttributes.getDiscounted_price());
        });
        model.addAttribute("product", product);
        model.addAttribute("editing", true);
        return "administration/product/adminProductNew";
    }

    @GetMapping("/delete_product/{id}")
    public String deleteProduct(@PathVariable String id) {
        var product = productService.findProductById(Integer.parseInt(id));
        product.getImages().forEach(image -> {
            File imageFile = new File(rootDir + "/images", image.getName());
            if(imageFile.exists()) imageFile.delete();
        });
        productService.deleteProduct(Integer.parseInt(id));
        return "redirect:/product_list";
    }

    @GetMapping(value = "/sub_categories")
    @ResponseBody
    public ArrayList<Subcategory> getSubCategories(@RequestParam String category) {
        return (ArrayList<Subcategory>) subcategoryService.findSubcategoriesByCategoryId(Integer.parseInt(category));
    }
}