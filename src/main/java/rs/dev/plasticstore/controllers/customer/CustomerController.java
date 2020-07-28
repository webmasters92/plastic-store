package rs.dev.plasticstore.controllers.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.dev.plasticstore.model.Customer;
import rs.dev.plasticstore.model.Mail;
import rs.dev.plasticstore.model.OrderStatus;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.model.Role;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.model.Wishlist;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.checkout.CheckoutService;
import rs.dev.plasticstore.services.customer.CustomerService;
import rs.dev.plasticstore.services.mail.EmailService;
import rs.dev.plasticstore.services.product.ProductService;
import rs.dev.plasticstore.services.wishlist.WishListService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @GetMapping(value = "/order_cancel/{id}")
    public String cancelOrder(@PathVariable String id, Model model, RedirectAttributes redir) {
        var order = checkoutService.findOrderById(Integer.parseInt(id));
        order.setOrderStatus(OrderStatus.CANCELED);
        checkoutService.saveOrder(order);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("order", order);
        redir.addFlashAttribute("active_tab", "orders_panel");
        return "redirect:/customer/my_account";
    }

    // Display forgotPassword page
    @GetMapping
    @RequestMapping(value = "/forgot")
    public ModelAndView displayForgotPasswordPage(ModelAndView modelAndView) {
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("webapp/customer/forgot_password");
        return modelAndView;
    }

    // Display form to reset password
    @GetMapping
    @RequestMapping(value = "/reset")
    public ModelAndView displayResetPasswordPage(ModelAndView modelAndView, @RequestParam("token") String token) {
        Optional<Customer> customer = customerService.findCustomerByResetToken(token);
        if(customer.isPresent()) {
            modelAndView.addObject("customer", customer.get());
            modelAndView.addObject("token", token);
        } else modelAndView.addObject("errorMessage", "Uups!  Link za resetovanje šifre nije validan.");
        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("webapp/customer/reset_password");
        return modelAndView;
    }

    @PostMapping
    @RequestMapping("/edit_customer")
    public String editUserInformation(Model model, @ModelAttribute Customer customer, @AuthenticationPrincipal UserPrincipal principal, BindingResult result, RedirectAttributes redirectAttributes) {
        model.addAttribute("categories", categoryService.findAll());
        String encodedPassword = "";
        if(!customer.getNew_password().isEmpty()) encodedPassword = bCryptPasswordEncoder.encode(customer.getNew_password());
        else encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
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

        redirectAttributes.addFlashAttribute("message", "Došlo je do greške prilikom čuvanja podataka.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-danger");
        if(result.hasErrors()) return "redirect:/customer/my_account";

        redirectAttributes.addFlashAttribute("message", "Uspešno ste promenili podatke.");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");
        return "redirect:/customer/my_account";
    }

    // Going to reset page without a token redirects to login page
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ModelAndView handleMissingParams(MissingServletRequestParameterException ex) {
        return new ModelAndView("redirect:/customer/login");
    }

    // Process form submission from forgotPassword page
    @PostMapping
    @RequestMapping(value = "/process_forgot_password")
    public ModelAndView processForgotPasswordForm(ModelAndView modelAndView, @RequestParam("email") String userEmail, HttpServletRequest request) {
        // Lookup user in database by e-mail
        Customer customer = customerService.findCustomerByEmail(userEmail);

        if(customer == null) {
            modelAndView.addObject("errorMessage", "Ne postoji nalog sa ovom email adresom!");
        } else {
            // Generate random 36-character string token for reset password
            customer.setResetToken(UUID.randomUUID().toString());

            // Save token to database
            customerService.save(customer);

            String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

            Mail mail = new Mail();
            mail.setFrom("miosic5@gmail.com");
            mail.setTo(customer.getEmail());
            mail.setSubject("Zahtev za resetovanje šifre");
            mail.setReset_link(appUrl + "/customer/reset?token=" + customer.getResetToken());
            mail.setHome_link(appUrl);

            try {
                emailService.sendResetPasswordEmail(mail);
            } catch(MessagingException e) {
                e.printStackTrace();
            }

            // Add success message to view
            modelAndView.addObject("successMessage", "Link za resetovanje šifre je poslat na vaš email " + userEmail);
        }

        modelAndView.addObject("categories", categoryService.findAll());
        modelAndView.setViewName("webapp/customer/forgot_password");
        return modelAndView;
    }

    @PostMapping
    @RequestMapping("/registerUser")
    public ModelAndView register(ModelAndView modelAndView, @Valid Customer customer, BindingResult bindingResult, Model model) {
        var customerByUsername = customerService.findCustomerByUsername(customer.getUsername());
        if(customerByUsername != null) {
            modelAndView.addObject("username_error", "Korisnik sa ovim korisničkim imenom već postoji");
            modelAndView.addObject("categories", categoryService.findAll());
            modelAndView.setViewName("webapp/customer/register");
            bindingResult.reject("username");
        }

        var customerByEmail = customerService.findCustomerByEmail(customer.getEmail());
        if(customerByEmail != null) {
            modelAndView.addObject("email_error", "Korisnik sa ovim email-om već postoji");
            modelAndView.addObject("categories", categoryService.findAll());
            modelAndView.setViewName("webapp/customer/register");
            bindingResult.reject("email");
        }

        if(bindingResult.hasErrors()) {
            modelAndView.setViewName("webapp/customer/register");
        } else {
            String encodedPassword = bCryptPasswordEncoder.encode(customer.getPassword());
            customer.setPassword(encodedPassword);
            customer.getRoles().add(new Role("ROLE_USER"));
            customer.setDateCreated(new Date());
            customerService.save(customer);
            modelAndView.addObject("categories", categoryService.findAll());
            modelAndView.setViewName("webapp/customer/login");
        }
        return modelAndView;

    }

    // Process reset password form
    @PostMapping
    @RequestMapping(value = "/process_reset_password")
    public ModelAndView setNewPassword(ModelAndView modelAndView, @ModelAttribute Customer customer, @RequestParam("token") String token, RedirectAttributes redir) {

        // Find the user associated with the reset token
        Optional<Customer> user = customerService.findCustomerByResetToken(token);

        if(customer.getPassword().isEmpty()) {
            modelAndView.addObject("errorMessage", "Šifra ne može biti prazna.");
            modelAndView.addObject("token", token);
            modelAndView.setViewName("/webapp/customer/reset_password");
        } else if(customer.getPassword().length() < 8) {
            modelAndView.addObject("errorMessage", "Šifra mora sadržati najmanje 8 karaktera.");
            modelAndView.addObject("token", token);
            modelAndView.setViewName("/webapp/customer/reset_password");
        } else if(!customer.getPassword().equals(customer.getNew_password())) {
            modelAndView.addObject("errorMessage", "Molim vas potvrdite vašu šifru.");
            modelAndView.addObject("token", token);
            modelAndView.setViewName("/webapp/customer/reset_password");
        } else {
            if(user.isPresent()) {
                Customer resetCustomer = user.get();
                resetCustomer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
                resetCustomer.setResetToken(null);

                customerService.save(resetCustomer);

                // In order to set a model attribute on a redirect, we must use
                redir.addFlashAttribute("successMessage", "Uspešno ste resetovali vašu šifru. Sada se možete ulogovati.");

                modelAndView.setViewName("redirect:/customer/login");
                return modelAndView;
            } else {
                modelAndView.addObject("errorMessage", "Uups!  Link za resetovanje šifre nije validan.");
                modelAndView.setViewName("/webapp/customer/reset_password");
            }
        }

        return modelAndView;
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
        wishlist_db.addAll(wishListService.findWishListByCustomerId(principal.getUserId()));
        for(Wishlist wishlist : wishlist_db) {
            products.add(productService.findProductById(wishlist.getProductId()));
        }

        model.addAttribute("categories", categoryService.findAll());
        var customer = customerService.findCustomerById(principal.getUserId());
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
    @Autowired
    EmailService emailService;
}
