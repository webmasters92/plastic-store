package rs.dev.plasticstore.controllers.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.dev.plasticstore.model.*;
import rs.dev.plasticstore.services.cart.CartService;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.color.ColorService;
import rs.dev.plasticstore.services.product.ProductService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    ProductService productService;

    @Autowired
    ColorService colorService;

    @Autowired
    CartService cartService;

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add_to_cart")
    @ResponseBody
    public int addToCart(@RequestBody CartItem cartItem, HttpSession session, @AuthenticationPrincipal UserPrincipal principal) {
        var product = productService.findProductById(cartItem.getProduct_id());
        var product_color = colorService.findColorsByName(cartItem.getColor());
        var cart = (Cart) session.getAttribute("cart");
        if(cart == null) {
            cart = new Cart();
        }
        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setProduct_color(product_color);
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getPrice());
        cart.setCustomerId(0);
        cart.getCartItems().add(cartItem);
        cart.setTotal(cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));
        session.setAttribute("cart", cart);
        if(principal != null) {
            var cartDb = cartService.findCartByCustomerId(principal.getUserId());
            if(cartDb != null) {
                if(!cartDb.getCartItems().contains(cartItem)) cartDb.getCartItems().add(cartItem);
                cartDb.setCustomerId(principal.getUserId());
                cartDb.setTotal(cartDb.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));
                session.setAttribute("cart", cartDb);
                cartService.saveCart(cartDb);
            } else {
                cart.setCustomerId(principal.getUserId());
                cartService.saveCart(cart);
            }
        }
        return product.getId();
    }

    @GetMapping(value = "/show_cart")
    public String showCart(Model model, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        var cart = new Cart();
        if(principal != null) {
            cart = cartService.findCartByCustomerId(principal.getUserId());
            session.setAttribute("cart", cart);
        } else {
            cart = (Cart) session.getAttribute("cart");
            if(cart == null) {
                cart = new Cart();
                session.setAttribute("cart", cart);
            }
        }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("cart", cart);
        return "webapp/cart/cart";
    }

    @GetMapping(value = "/delete_cart_item/{size}/{price}/{color}")
    public String deleteFromCart(@PathVariable String size, @PathVariable String price, @PathVariable String color, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        if(principal != null) {
            var cartDB = cartService.findCartByCustomerId(principal.getUserId());
            cartDB.getCartItems().removeIf(cartItem -> cartItem.getSize().equals(size) && cartItem.getPrice() == Integer.parseInt(price) && cartItem.getColor().equals(color));
            cartDB.setTotal(cartDB.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));
            cartService.saveCart(cartDB);
            session.setAttribute("cart", cartDB);
        } else {
            var cart = (Cart) session.getAttribute("cart");
            cart.getCartItems().removeIf(cartItem -> cartItem.getSize().equals(size) && cartItem.getPrice() == Integer.parseInt(price) && cartItem.getColor().equals(color));
            cart.setTotal(cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart/show_cart";
    }

    @GetMapping(value = "/delete_minicart_item/{size}/{price}/{color}")
    public String deleteFromMiniCart(@PathVariable String size, @PathVariable String price, @PathVariable String color, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        if(principal != null) {
            var cartDB = cartService.findCartByCustomerId(principal.getUserId());
            cartDB.getCartItems().removeIf(cartItem -> cartItem.getSize().equals(size) && cartItem.getPrice() == Integer.parseInt(price) && cartItem.getColor().equals(color));
            cartDB.setTotal(cartDB.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));
            cartService.saveCart(cartDB);
            session.setAttribute("cart", cartDB);
        } else {
            var cart = (Cart) session.getAttribute("cart");
            cart.getCartItems().removeIf(cartItem -> cartItem.getSize().equals(size) && cartItem.getPrice() == Integer.parseInt(price) && cartItem.getColor().equals(color));
            cart.setTotal(cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));
            session.setAttribute("cart", cart);
        }
        return "webapp/cart/minicart_fragment :: minicart";
    }

    @GetMapping(value = "/refresh_minicart")
    public String refreshMinicartFragment() {
        return "webapp/cart/minicart_fragment :: minicart";
    }

}
