package rs.dev.plasticstore.controllers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import rs.dev.plasticstore.Utils.MinMax;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.product.ProductService;

import java.util.ArrayList;

@Controller
public class HomeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @RequestMapping("/")
    public String showHomePage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("popular_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findPopularProducts()));
        model.addAttribute("new_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findNewProducts()));
        model.addAttribute("sale_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findProductsOnSale()));
        model.addAttribute("product", productService.findProductById(2));
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
