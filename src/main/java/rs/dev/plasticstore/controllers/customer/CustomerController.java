package rs.dev.plasticstore.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.dev.plasticstore.model.Customer;
import rs.dev.plasticstore.model.Role;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.user.CustomerService;

@Controller
public class CustomerController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CustomerService customerService;

    @Autowired
    PasswordEncoder bCryptPasswordEncoder;

    @GetMapping
    @RequestMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("customer", new Customer());
        return "webapp/user/register";
    }

    @PostMapping
    @RequestMapping("/registerUser")
    public String register(Customer customer) {
        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer.getRoles().add(new Role("ROLE_USER"));
        customerService.save(customer);
        return "redirect:/login";
    }

    @GetMapping
    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/user/login";
    }
}
