package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.product.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @ResponseBody
    @GetMapping
    @RequestMapping(value = "/checkCode", produces = "application/json")
    public String checkProductCode(@RequestParam(value = "code") int code) {
        var product = productService.findProductByCode(code);
        if(product.isEmpty()) return "{\"valid\":true}";
        return "{\"valid\":false}";
    }

    @GetMapping
    @RequestMapping(value = "/product_list_category/{id}")
    public String showProductListByCategory(@PathVariable String id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findProductsByCategoryId(Integer.parseInt(id)));
        return "webapp/product/product_list";
    }

    @GetMapping
    @RequestMapping(value = "/product_list_sub_category/{id}")
    public String showProductListBySubCategory(@PathVariable String id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findProductsBySubCategoryId(Integer.parseInt(id)));
        return "webapp/product/product_list";
    }
}
