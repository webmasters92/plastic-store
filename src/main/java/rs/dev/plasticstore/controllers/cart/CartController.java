package rs.dev.plasticstore.controllers.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.dev.plasticstore.model.Cart;
import rs.dev.plasticstore.model.CartItem;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.services.cart.CartService;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.color.ColorService;
import rs.dev.plasticstore.services.product.ProductService;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    @PostMapping("/add_to_cart")
    @ResponseBody
    public int addToCart(@RequestBody CartItem cartItem, HttpSession session, @AuthenticationPrincipal UserPrincipal principal) {
        var product = productService.findProductById(cartItem.getProduct_id());
        var product_color = colorService.findColorsByName(cartItem.getColor());
        var cart = (Cart) session.getAttribute("cart");
        if(cart == null) cart = new Cart();

        cartItem.setCart(cart);
        cartItem.setProduct(product);
        cartItem.setProduct_id(product.getId());
        cartItem.setProduct_color(product_color);
        cartItem.setTotalPrice(cartItem.getQuantity() * cartItem.getPrice());

        cart.setCustomerId(0);
        cart.getCartItems().add(cartItem);
        cart.getCartItems().forEach(cartItem1 -> {
            if(cartItem1.equals(cartItem)) {
                cartItem1.setQuantity(cartItem.getQuantity());
                cartItem1.setTotalPrice(cartItem.getQuantity() * cartItem.getPrice());
            }
        });
        cart.setTotal(cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));

        session.setAttribute("cart", cart);
        if(principal != null) {
            var cartDb = cartService.findCartByCustomerId(principal.getUserId());
            if(cartDb != null) {
                cartDb.getCartItems().add(cartItem);
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

    @GetMapping(value = "/delete_cart_item/{size}/{price}/{color}/{product_code}")
    public String deleteFromCart(@PathVariable String product_code, @PathVariable String size, @PathVariable String price, @PathVariable String color, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        var cart = (Cart) session.getAttribute("cart");
        if(principal != null) cartService.saveCart(cart);
        cart.getCartItems().removeIf(cartItem -> cartItem.getSize().equals(size) && cartItem.getPrice() == Integer.parseInt(price) && cartItem.getColor().equals(color) && cartItem.getProduct().getCode() == Integer.parseInt(product_code));
        cart.setTotal(cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));
        session.setAttribute("cart", cart);

        return "redirect:/cart/show_cart";
    }

    @GetMapping(value = "/delete_minicart_item/{size}/{price}/{color}/{product_code}")
    public String deleteFromMiniCart(@PathVariable String product_code, @PathVariable String size, @PathVariable String price, @PathVariable String color, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        var cart = (Cart) session.getAttribute("cart");
        cart.getCartItems().removeIf(cartItem -> cartItem.getSize().equals(size) && cartItem.getPrice() == Integer.parseInt(price) && cartItem.getColor().equals(color) && cartItem.getProduct().getCode() == Integer.parseInt(product_code));
        cart.setTotal(cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));

        if(principal != null) cartService.saveCart(cart);
        session.setAttribute("cart", cart);

        return "webapp/cart/minicart_fragment :: minicart";
    }

    @GetMapping(value = "/refresh_minicart")
    public String refreshMinicartFragment() {
        return "webapp/cart/minicart_fragment :: minicart";
    }

    @GetMapping(value = "/update_minicart/{id}/{value}/{quantity}")
    public String refreshMinicartFragment(@PathVariable String id, @PathVariable String value, @PathVariable String quantity, HttpSession session) {
        var item_id = Integer.parseInt(id.split("-")[0]);
        var item_price = Integer.parseInt(id.split("-")[2]);
        var item_size = id.split("-")[1];
        var item_color = id.split("-")[3];
        int item_total = Integer.parseInt(value);
        int item_quantity = Integer.parseInt(quantity);
        var cart = (Cart) session.getAttribute("cart");
        if(cart != null) {
            cart.getCartItems().forEach(cartItem -> {
                if(cartItem.getProduct().getId() == item_id && cartItem.getPrice() == item_price && cartItem.getSize().equals(item_size) && cartItem.getColor().equals(item_color)) {
                    cartItem.setQuantity(item_quantity);
                    cartItem.setTotalPrice(item_total);
                }
            });
            cart.setTotal(cart.getCartItems().stream().map(CartItem::getTotalPrice).reduce(0, Integer::sum));

            session.setAttribute("cart", cart);
        }
        return "webapp/cart/minicart_fragment :: minicart";
    }

    @GetMapping(value = "/show_cart")
    public String showCart(Model model, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        Cart cart;
        if(principal != null) {
            if(session.getAttribute("cart") != null) cart = (Cart) session.getAttribute("cart");
            else cart = cartService.findCartByCustomerId(principal.getUserId());
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

    @GetMapping(value = "/update_cart_summary")
    public String updateCartSummary() {
        return "webapp/cart/cart_summary :: summary";
    }

    @Autowired
    ProductService productService;
    @Autowired
    ColorService colorService;
    @Autowired
    CartService cartService;
    @Autowired
    CategoryService categoryService;

}
