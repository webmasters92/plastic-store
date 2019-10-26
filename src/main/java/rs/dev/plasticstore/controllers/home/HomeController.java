package rs.dev.plasticstore.controllers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.product.ProductService;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String showHomePage(Model model){
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/shop/home";
    }
}
