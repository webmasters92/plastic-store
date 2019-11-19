package rs.dev.plasticstore.controllers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.product.ProductService;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("popular_products", productService.findPopularProducts());
        model.addAttribute("new_products", productService.findNewProducts());
        model.addAttribute("sale_products", productService.findProductsOnSale());
        model.addAttribute("product", productService.findProductById(2));

        return "webapp/shop/home";
    }
}
