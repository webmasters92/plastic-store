package rs.dev.plasticstore.controllers.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.dev.plasticstore.model.Cart;
import rs.dev.plasticstore.model.Customer;
import rs.dev.plasticstore.model.Guest;
import rs.dev.plasticstore.model.Mail;
import rs.dev.plasticstore.model.Order;
import rs.dev.plasticstore.model.OrderItem;
import rs.dev.plasticstore.model.OrderPayment;
import rs.dev.plasticstore.model.OrderStatus;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.services.cart.CartService;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.checkout.CheckoutService;
import rs.dev.plasticstore.services.color.ColorService;
import rs.dev.plasticstore.services.guest.GuestService;
import rs.dev.plasticstore.services.mail.EmailService;
import rs.dev.plasticstore.services.product.ProductService;
import rs.dev.plasticstore.services.user.CustomerService;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {

    @PostMapping(value = "/place_order")
    public String placeOrder(@ModelAttribute Order order, @AuthenticationPrincipal UserPrincipal principal, HttpSession session, Model model, HttpServletRequest request) {
        if(principal != null) {
            order.setCustomer_id(principal.getUserId());
        } else guestService.save(order.getGuest());

        var cart = (Cart) session.getAttribute("cart");
        cart.getCartItems().forEach(cartItem -> {
            var orderItem = new OrderItem();
            orderItem.setColor(cartItem.getColor());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setProduct_color(cartItem.getProduct_color());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(cartItem.getSize());
            orderItem.setTotalPrice(cartItem.getTotalPrice());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        });

        order.setOrderTotal(cart.getTotal());
        order.setDateCreated(new Date());
        order.setOrderStatus(OrderStatus.PORUČENA);
        order.setOrder_payment(OrderPayment.CASH_ON_DELIVERY);

        checkoutService.saveOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("categories", categoryService.findAll());

        String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

        Mail mail = new Mail();
        mail.setFrom("plastika.draskovic@gmail.com");
        mail.setTo("milosic5@gmail.com");
        mail.setSubject("Obaveštenje o prijemu porudžbine broj " + order.getId());
        mail.setHome_link(appUrl);
        mail.setOrder(order);

        try {
            emailService.sendOrderEmail(mail);
        } catch(MessagingException e) {
            e.printStackTrace();
        }

        return "webapp/checkout/order_confirmed";
    }

    @GetMapping(value = "/show_checkout")
    public String showCheckout(Model model, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        var order = new Order();
        if(principal != null) {
            Customer customer = customerService.findCustomerByUsername(principal.getUsername());
            customer.setCountry("Srbija");
            order.setCustomer(customer);
            order.setCustomer_id(customer.getId());
        } else order.setGuest(new Guest());

        var cart = (Cart) session.getAttribute("cart");
        if(cart != null) {
            cart.getCartItems().forEach(cartItem -> {
                var orderItem = new OrderItem();
                orderItem.setId(cartItem.getId());
                orderItem.setColor(cartItem.getColor());
                orderItem.setPrice(cartItem.getPrice());
                orderItem.setProduct(cartItem.getProduct());
                orderItem.setProduct_color(cartItem.getProduct_color());
                orderItem.setQuantity(cartItem.getQuantity());
                orderItem.setSize(cartItem.getSize());
                orderItem.setTotalPrice(cartItem.getTotalPrice());
                orderItem.setOrder(order);
                order.getOrderItems().add(orderItem);
            });

            order.setOrderTotal(cart.getTotal());
        }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("order", order);
        return "webapp/checkout/checkout";
    }

    @GetMapping(value = "/order_details/{id}")
    public String showCheckout(@PathVariable String id, Model model) {
        var order = checkoutService.findOrderById(Integer.parseInt(id));

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("order", order);
        return "webapp/checkout/order_details";
    }

    @Autowired
    ProductService productService;
    @Autowired
    ColorService colorService;
    @Autowired
    CartService cartService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    EmailService emailService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CheckoutService checkoutService;
    @Autowired
    GuestService guestService;
}
