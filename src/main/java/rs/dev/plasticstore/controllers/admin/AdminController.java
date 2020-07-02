package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.dev.plasticstore.model.Image;
import rs.dev.plasticstore.model.Mail;
import rs.dev.plasticstore.model.OrderStatus;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.model.ProductAttributes;
import rs.dev.plasticstore.model.ProductColor;
import rs.dev.plasticstore.model.Subcategory;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.category.SubcategoryService;
import rs.dev.plasticstore.services.checkout.CheckoutService;
import rs.dev.plasticstore.services.color.ColorService;
import rs.dev.plasticstore.services.customer.CustomerService;
import rs.dev.plasticstore.services.guest.GuestService;
import rs.dev.plasticstore.services.image.ImageService;
import rs.dev.plasticstore.services.mail.EmailService;
import rs.dev.plasticstore.services.product.ProductService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/administration")
public class AdminController {

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
                var imageDir = rootDir + "/images";
                var imageFile = new File(imageDir, image.getName());
                if(!Files.exists(Paths.get(imageDir))) {
                    try {
                        Files.createDirectory(Paths.get(imageDir));
                    } catch(IOException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    multipartFile.transferTo(imageFile);
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
            if(product.getDiscounted_prices().size() > 0) product_attributes.setDiscounted_price(product.getDiscounted_prices().get(i));
            product.getProductAttributes().add(product_attributes);
        }

        productService.saveProduct(product);
        redirectAttributes.addAttribute("message", "Proizvod je uspešno sačuvan");
        redirectAttributes.addFlashAttribute("alertClass", "alert-success");

        return "redirect:/administration/new_product";
    }

    @GetMapping("/confirm_order/{id}")
    public String confirmOrder(@PathVariable String id, HttpServletRequest request, @AuthenticationPrincipal UserPrincipal principal) {
        var order = checkoutService.findOrderById(Integer.parseInt(id));
        order.setOrderStatus(OrderStatus.SENT);
        checkoutService.saveOrder(order);
        var email = "";
        if(principal != null) email = customerService.findCustomerById(order.getCustomer_id()).getEmail();
        else email = guestService.findGuestById(order.getGuest().getId()).getEmail();

        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        Mail mail = new Mail();
        mail.setFrom("plastika.draskovic@gmail.com");
        mail.setTo(email);
        mail.setSubject("Vaša porudžbina je poslata");
        mail.setHome_link(appUrl);
        mail.setOrder(order);
        try {
            emailService.sendOrderConfirmationEmail(mail);
        } catch(MessagingException e) {
            e.printStackTrace();
        }

        return "redirect:/administration/order_list";
    }

    @GetMapping("/delete_customer/{id}")
    public String deleteCustomer(@PathVariable String id) {
        var orders = checkoutService.findAllOrdersByCustomerId(Integer.parseInt(id));
        orders.forEach(order -> checkoutService.deleteOrder(order.getId()));
        customerService.deleteCustomerById(Integer.parseInt(id));
        return "redirect:/administration/customer_list";
    }

    @GetMapping("/delete_guest/{id}")
    public String deleteGuest(@PathVariable String id) {
        var orders = checkoutService.findAllOrdersByGuestId(Integer.parseInt(id));
        orders.forEach(order -> checkoutService.deleteOrder(order.getId()));
        guestService.deleteGuestById(Integer.parseInt(id));
        return "redirect:/administration/guest_list";
    }

    @GetMapping("/delete_order/{id}")
    public String deleteOrder(@PathVariable String id) {
        checkoutService.deleteOrder(Integer.parseInt(id));
        return "redirect:/administration/order_list";
    }

    @GetMapping("/delete_product/{id}")
    public String deleteProduct(@PathVariable String id) {
        var product = productService.findProductById(Integer.parseInt(id));
        product.getImages().forEach(image -> {
            File imageFile = new File(rootDir + "/images", image.getName());
            if(imageFile.exists()) imageFile.delete();
        });
        productService.deleteProduct(Integer.parseInt(id));
        return "redirect:/administration/product_list";
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

    @GetMapping("/new_product")
    public String getNewProduct(@RequestParam(value = "message", required = false) String message, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("allColors", colorService.findAll());
        model.addAttribute("product", new Product());
        model.addAttribute("message", message);
        return "administration/product/adminProductNew";
    }

    @GetMapping(value = "/sub_categories")
    @ResponseBody
    public ArrayList<Subcategory> getSubCategories(@RequestParam String category) {
        return (ArrayList<Subcategory>) subcategoryService.findSubcategoriesByCategoryId(Integer.parseInt(category));
    }

    @GetMapping("/customer_details/{id}")
    public String showCustomerDetails(@PathVariable String id, Model model) {
        model.addAttribute("customer", customerService.findCustomerById(Integer.parseInt(id)));
        return "administration/customer/customer_details";
    }

    @GetMapping("/customer_list")
    public String showCustomerList(Model model) {
        var customers = customerService.findAll();
        customers.forEach(customer -> customer.setOrders_num(checkoutService.findAllOrdersByCustomerId(customer.getId()).size()));
        model.addAttribute("customers", customers);
        return "administration/customer/customer_list";
    }

    @GetMapping("/guest_details/{id}")
    public String showGuestDetails(@PathVariable String id, Model model) {
        model.addAttribute("guest", guestService.findGuestById(Integer.parseInt(id)));
        return "administration/customer/guest_details";
    }

    @GetMapping("/guest_list")
    public String showGuestList(Model model) {
        model.addAttribute("guests", guestService.findAll());
        return "administration/customer/guest_list";
    }

    @GetMapping("/order_details/{id}")
    public String showOrderDetails(@PathVariable String id, Model model) {
        model.addAttribute("order", checkoutService.findOrderById(Integer.parseInt(id)));
        return "administration/order/order_details";
    }

    @GetMapping("/order_list")
    public String showOrderList(Model model) {
        var ordersDB = checkoutService.findAll();
        ordersDB.forEach(order -> {
            if(order.getCustomer_id() != 0) order.setCustomer(customerService.findCustomerById(order.getCustomer_id()));
        });
        model.addAttribute("orders", checkoutService.findAll());
        return "administration/order/order_list";
    }

    @GetMapping("/product_list")
    public String showProductList(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", productService.findAll());
        return "administration/product/adminProductList";
    }

    @Autowired
    EmailService emailService;
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
    @Autowired
    CheckoutService checkoutService;
    @Autowired
    CustomerService customerService;
    @Autowired
    GuestService guestService;
}