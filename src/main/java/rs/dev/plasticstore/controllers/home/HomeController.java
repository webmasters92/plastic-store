package rs.dev.plasticstore.controllers.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import rs.dev.plasticstore.model.Cart;
import rs.dev.plasticstore.model.Image;
import rs.dev.plasticstore.model.Message;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.model.ProductAttributes;
import rs.dev.plasticstore.model.ProductColor;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.model.Wishlist;
import rs.dev.plasticstore.services.cart.CartService;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.category.SubcategoryService;
import rs.dev.plasticstore.services.color.ColorService;
import rs.dev.plasticstore.services.message.MessageService;
import rs.dev.plasticstore.services.product.ProductService;
import rs.dev.plasticstore.services.wishlist.WishListService;
import rs.dev.plasticstore.utils.MinMax;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashSet;

@Controller
public class HomeController {

    public void addProducts() {
        var categories = categoryService.findAll();

        for(int i = 1; i <= 1000; i++) {
            Product product = new Product();
            if(i < 100) {
                product.setCategory(categories.get(0));
                if(i > 0 && i < 20) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(1).get());
                } else if(i > 20 && i < 50) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(2).get());
                } else if(i > 50 && i < 70) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(3).get());
                } else if(i > 70 && i < 100) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(4).get());
                }

            } else if(i > 100 && i < 200) {
                product.setCategory(categories.get(1));
                if(i > 100 && i < 120) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(5).get());
                } else if(i > 120 && i < 150) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(6).get());
                } else if(i > 150 && i < 170) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(7).get());
                } else if(i > 170 && i < 200) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(8).get());
                }
            } else if(i > 200 && i < 300) {
                product.setCategory(categories.get(2));
                if(i > 200 && i < 220) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(9).get());
                } else if(i > 220 && i < 250) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(10).get());
                } else if(i > 250 && i < 270) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(11).get());
                } else if(i > 270 && i < 300) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(12).get());
                }
            } else if(i > 300 && i < 400) {
                product.setCategory(categories.get(3));
                if(i > 300 && i < 320) {
                    product.setSale(true);
                    product.setSubcategory(subcategoryService.findSubCategoryById(15).get());
                } else if(i > 320 && i < 350) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(16).get());
                } else if(i > 350 && i < 370) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(17).get());
                } else if(i > 370 && i < 400) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(18).get());
                }
            } else if(i > 400 && i < 500) {
                product.setCategory(categories.get(4));
                if(i > 400 && i < 420) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(19).get());
                } else if(i > 420 && i < 450) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(20).get());
                } else if(i > 450 && i < 470) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(21).get());
                } else if(i > 470 && i < 500) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(22).get());
                }
            } else if(i > 500 && i < 600) {
                product.setCategory(categories.get(5));
                if(i > 500 && i < 520) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(37).get());
                } else if(i > 520 && i < 550) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(38).get());
                } else if(i > 550 && i < 570) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(39).get());
                } else if(i > 570 && i < 600) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(40).get());
                }
            } else if(i > 600 && i < 700) {
                product.setCategory(categories.get(6));

                if(i > 600 && i < 620) {
                    product.setSale(true);
                    product.setSubcategory(subcategoryService.findSubCategoryById(44).get());
                } else if(i > 620 && i < 650) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(45).get());
                } else if(i > 650 && i < 670) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(46).get());
                } else if(i > 670 && i < 700) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(47).get());
                }
            } else if(i > 700 && i < 800) {
                product.setCategory(categories.get(4));
                if(i > 700 && i < 720) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(29).get());
                } else if(i > 720 && i < 750) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(30).get());
                } else if(i > 750 && i < 770) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(31).get());
                } else if(i > 770 && i < 800) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(32).get());
                }
            } else if(i > 800 && i < 900) {
                product.setCategory(categories.get(5));
                if(i > 800 && i < 220) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(41).get());
                } else if(i > 820 && i < 850) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(42).get());
                } else if(i > 850 && i < 870) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(43).get());
                } else if(i > 870 && i < 900) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(43).get());
                }
            } else {
                product.setCategory(categories.get(6));
                if(i > 900 && i < 920) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(48).get());
                } else if(i > 920 && i < 950) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(49).get());
                } else if(i > 950 && i < 970) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(50).get());
                } else if(i > 970) {
                    product.setSubcategory(subcategoryService.findSubCategoryById(51).get());
                }
            }

            var color1 = colorService.findColorsById(1);
            var color2 = colorService.findColorsById(3);
            var color3 = colorService.findColorsById(5);
            var color4 = colorService.findColorsById(9);
            var productColor1 = new ProductColor();
            var productColor2 = new ProductColor();
            var productColor3 = new ProductColor();
            var productColor4 = new ProductColor();
            productColor1.setCode(color1.getCode());
            productColor2.setCode(color2.getCode());
            productColor3.setCode(color3.getCode());
            productColor4.setCode(color4.getCode());
            productColor1.setName(color1.getName());
            productColor2.setName(color2.getName());
            productColor3.setName(color3.getName());
            productColor4.setName(color4.getName());
            productColor1.setProduct(product);
            productColor2.setProduct(product);
            productColor3.setProduct(product);
            productColor4.setProduct(product);
            product.getProductColors().add(productColor1);
            product.getProductColors().add(productColor2);
            product.getProductColors().add(productColor3);
            product.getProductColors().add(productColor4);

            for(int j = 0; j < 3; j++) {
                var product_attributes = new ProductAttributes();
                if(j == 0) {
                    product_attributes.setSize("100x200");
                    product_attributes.setPrice(250);
                    //                    if(product.getDiscounted_prices().size() > 0) product_attributes.setDiscounted_price(product.getDiscounted_prices().get(i));
                    product.getProductAttributes().add(product_attributes);
                } else if(j == 1) {
                    product_attributes.setSize("300x500");
                    product_attributes.setPrice(460);
                    //                    if(product.getDiscounted_prices().size() > 0) product_attributes.setDiscounted_price(product.getDiscounted_prices().get(i));
                    product.getProductAttributes().add(product_attributes);
                } else if(j == 2) {
                    product_attributes.setSize("700x900");
                    product_attributes.setPrice(865);
                    //                    if(product.getDiscounted_prices().size() > 0) product_attributes.setDiscounted_price(product.getDiscounted_prices().get(i));
                    product.getProductAttributes().add(product_attributes);
                }
            }
            var image1 = new Image();
            var image2 = new Image();
            var image3 = new Image();
            var image4 = new Image();
            image1.setName("saksija-1.jpg");
            image2.setName("saksija-2.jpg");
            image3.setName("saksija-3.jpg");
            image4.setName("saksija-4.jpg");
            image1.setUrl("/images/saksija-1.jpg");
            image2.setUrl("/images/saksija-2.jpg");
            image3.setUrl("/images/saksija-3.jpg");
            image4.setUrl("/images/saksija-4.jpg");
            image1.setProduct(product);
            image2.setProduct(product);
            image3.setProduct(product);
            image4.setProduct(product);
            product.getImages().add(image1);
            product.getImages().add(image2);
            product.getImages().add(image3);
            product.getImages().add(image4);

            product.setAvailable(true);
            product.setAverageRating(4);
            product.setCode(i);
            //  product.setDate_created(new LocalDateTime());
            product.setDescription("Ovo je opis proizvoda");
            product.setManufacturer("Proizvodjac");
            product.setName("Proizvod " + i);
            productService.saveProduct(product);
        }
    }

    @PostMapping("/send_message")
    public String sendMessage(@ModelAttribute Message message, RedirectAttributes redirectAttributes) {
        messageService.saveMessage(message);
        redirectAttributes.addFlashAttribute("success_message", "Hvala na Vašem pitanju. Potrudićemo se da odgovorimo u najkraćem roku!");
        return "redirect:/contact";
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

    @RequestMapping("/about")
    public String showAboutUsPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/shop/about";
    }

    @RequestMapping("/contact")
    public String showContactPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("message", new Message());
        return "webapp/shop/contact";
    }

    @GetMapping("/faqs")
    public String showFAQSPage(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "webapp/shop/faqs";
    }

    @RequestMapping("/")
    public String showHomePage(Model model, HttpSession session, @AuthenticationPrincipal UserPrincipal principal) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("popular_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findPopularProducts()));
        model.addAttribute("new_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findNewProducts()));
        model.addAttribute("sale_products", setMinMaxPriceToProducts((ArrayList<Product>) productService.findProductsOnSale()));

        //ako je user u pitanju
        if(principal != null) {
            var session_cart = (Cart) session.getAttribute("cart");
            Cart user_cart;
            if(session_cart != null && session_cart.getCartItems().size() > 0) {
                user_cart = session_cart;
            } else user_cart = cartService.findCartByCustomerId(principal.getUserId());

            if(user_cart == null) {
                user_cart = new Cart();
                user_cart.setTotal(0);
                session.setAttribute("cart", user_cart);
            } else session.setAttribute("cart", user_cart);

            var products_db = new ArrayList<Wishlist>();
            var products = new HashSet<Product>();
            products_db.addAll(wishListService.findWishListByUserId(principal.getUserId()));
            for(Wishlist wishlist : products_db) {
                products.add(productService.findProductById(wishlist.getProductId()));
            }
            session.setAttribute("wishlist_products", products);
        }
        //ako je gost u pitanju
        if(session.getAttribute("cart") == null) {
            var cart = new Cart();
            cart.setTotal(0);
            session.setAttribute("cart", cart);
        }
        return "webapp/shop/home";
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
    SubcategoryService subcategoryService;
    @Autowired
    ColorService colorService;

    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @Autowired
    WishListService wishListService;
    @Autowired
    CartService cartService;
}
