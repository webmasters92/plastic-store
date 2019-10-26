package rs.dev.plasticstore.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.dev.plasticstore.services.category.CategoryService;

@Controller
public class UserController {

    @Autowired
    CategoryService categoryService;

    @GetMapping
    @RequestMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/user/register";
    }

    @GetMapping
    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/user/login";
    }
}
