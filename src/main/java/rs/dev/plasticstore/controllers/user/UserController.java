package rs.dev.plasticstore.controllers.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.dev.plasticstore.model.Role;
import rs.dev.plasticstore.model.User;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.user.UserService;

@Controller
public class UserController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    @RequestMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("user", new User());
        return "webapp/user/register";
    }

    @PostMapping
    @RequestMapping("/registerUser")
    public String register(User newUser) {
        String encodedPassword = bCryptPasswordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.getRoles().add(new Role("ROLE_USER"));
        userService.save(newUser);
        return "redirect:/login";
    }

    @GetMapping
    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/user/login";
    }
}
