package rs.dev.plasticstore.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.dev.plasticstore.model.Customer;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.model.Role;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.model.Wishlist;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.checkout.CheckoutService;
import rs.dev.plasticstore.services.product.ProductService;
import rs.dev.plasticstore.services.user.CustomerService;
import rs.dev.plasticstore.services.wishlist.WishListService;

import java.util.ArrayList;
import java.util.HashSet;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @PostMapping
    @RequestMapping("/edit_customer")
    public String editUserInformation(Model model, @ModelAttribute Customer customer, @AuthenticationPrincipal UserPrincipal principal) {
        model.addAttribute("categories", categoryService.findAll());
        String encodedPassword = "";
        if(!customer.getNew_password().isEmpty())
            encodedPassword = bCryptPasswordEncoder.encode(customer.getNew_password());
        else encodedPassword = customer.getPassword();
        Customer customer1 = customerService.findCustomerById(principal.getUserId());
        customer1.setFirstName(customer.getFirstName());
        customer1.setLastName(customer.getLastName());
        customer1.setEmail(customer.getEmail());
        customer1.setPhoneNumber(customer.getPhoneNumber());
        customer1.setCountry(customer.getCountry());
        customer1.setZipCode(customer.getZipCode());
        customer1.setAddress(customer.getAddress());
        customer1.setCity(customer.getCity());
        customer1.setPassword(encodedPassword);
        customerService.save(customer1);
        return "redirect:/customer/my_account";
    }

    @PostMapping
    @RequestMapping("/registerUser")
    public String register(Customer customer) {
        String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
        customer.getRoles().add(new Role("ROLE_USER"));
        customerService.save(customer);
        return "redirect:/customer/login";
    }

    @GetMapping
    @RequestMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/customer/login";
    }

    @GetMapping
    @RequestMapping("/my_account")
    public String showMyAccount(Model model, @AuthenticationPrincipal UserPrincipal principal) {
        var wishlist_db = new ArrayList<Wishlist>();
        var products = new HashSet<Product>();
        wishlist_db.addAll(wishListService.findWishListByUserId(principal.getUserId()));
        for(Wishlist wishlist : wishlist_db) {
            products.add(productService.findProductById(wishlist.getProductId()));
        }

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("customer", customerService.findCustomerById(principal.getUserId()));
        model.addAttribute("orders", checkoutService.findAllOrdersByCustomerId(principal.getUserId()));
        model.addAttribute("products", products);
        return "webapp/customer/my_account";
    }

    @GetMapping
    @RequestMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("customer", new Customer());
        return "webapp/customer/register";
    }

    @Autowired
    CategoryService categoryService;
    @Autowired
    CustomerService customerService;
    @Autowired
    WishListService wishListService;
    @Autowired
    ProductService productService;
    @Autowired
    PasswordEncoder bCryptPasswordEncoder;
    @Autowired
    CheckoutService checkoutService;
}
