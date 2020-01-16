package rs.dev.plasticstore.controllers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.dev.plasticstore.Utils.MinMax;
import rs.dev.plasticstore.model.*;
import rs.dev.plasticstore.services.cart.CartService;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.product.ProductService;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    CartService cartService;

    @RequestMapping("/")
    public String showHomePage(Model model, HttpSession session, @AuthenticationPrincipal UserPrincipal principal) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("popular_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findPopularProducts()));
        model.addAttribute("new_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findNewProducts()));
        model.addAttribute("sale_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findProductsOnSale()));
        if(principal != null) {
            var cartDB = cartService.findCartByCustomerId(principal.getUserId());
            if(cartDB == null) {
                var cart = new Cart();
                cart.setTotal(0);
                session.setAttribute("cart", cart);
            } else session.setAttribute("cart", cartDB);
        }
        if(session.getAttribute("cart") == null) {
            var cart = new Cart();
            cart.setTotal(0);
            session.setAttribute("cart", cart);
        }
        return "webapp/shop/home";
    }

    private ArrayList<Product> setMinMaxPriceToProducts(ArrayList<Product> products) {
        products.forEach(product -> {
            product.getProductAttributes().forEach(productAttributes -> product.getPrices().add(productAttributes.getPrice()));
            product.getProductAttributes().forEach(productAttributes -> product.getDiscounted_prices().add(productAttributes.getDiscounted_price()));
            product.setMinPrice(MinMax.findMin(product.getPrices()));
            product.setMaxPrice(MinMax.findMax(product.getPrices()));
            product.setMinDiscountedPrice(MinMax.findMin(product.getDiscounted_prices()));
            product.setMaxDiscountedPrice(MinMax.findMax(product.getDiscounted_prices()));
        });
        return products;
    }
}
