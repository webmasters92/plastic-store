package rs.dev.plasticstore.controllers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.dev.plasticstore.model.Cart;
import rs.dev.plasticstore.model.Message;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.model.Wishlist;
import rs.dev.plasticstore.services.cart.CartService;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.message.MessageService;
import rs.dev.plasticstore.services.product.ProductService;
import rs.dev.plasticstore.services.wishlist.WishListService;
import rs.dev.plasticstore.utils.MinMax;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;

@Controller
public class HomeController {

    @PostMapping("/send_message")
    public String sendMessage(@ModelAttribute Message message) {
        messageService.saveMessage(message);
        return "redirect:/";
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

    @RequestMapping("/")
    public String showHomePage(Model model, HttpSession session, @AuthenticationPrincipal UserPrincipal principal) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("popular_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findPopularProducts()));
        model.addAttribute("new_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findNewProducts()));
        model.addAttribute("sale_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findProductsOnSale()));

        if(principal != null) {
            var cartDB = (Cart) session.getAttribute("cart");
            if(cartDB.getCartItems().size() > 0) {
                cartDB = (Cart) session.getAttribute("cart");
            } else cartDB = cartService.findCartByCustomerId(principal.getUserId());

            if(cartDB == null) {
                var cart = new Cart();
                cart.setTotal(0);
                session.setAttribute("cart", cart);
            } else session.setAttribute("cart", cartDB);

            var products_db = new ArrayList<Wishlist>();
            var products = new HashSet<Product>();
            products_db.addAll(wishListService.findWishListByUserId(principal.getUserId()));
            for(Wishlist wishlist : products_db) {
                products.add(productService.findProductById(wishlist.getProductId()));
            }
            session.setAttribute("wishlist_products", products);
        }
        if(session.getAttribute("cart") == null) {
            var cart = new Cart();
            cart.setTotal(0);
            session.setAttribute("cart", cart);
        }
        return "webapp/shop/home";
    }

    @RequestMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("message", new Message());
        return "webapp/shop/contact";
    }

    @RequestMapping("/about")
    public String showAboutUsPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/shop/about";
    }

    @RequestMapping("/selling_terms")
    public String showSellingTerms(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/shop/selling_terms";
    }

    @RequestMapping("/shipping_info")
    public String showShippingInfo(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/shop/shipping_information";
    }

    @Autowired
    MessageService messageService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    WishListService wishListService;
    @Autowired
    CartService cartService;
}
