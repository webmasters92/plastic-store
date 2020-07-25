package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import rs.dev.plasticstore.model.Colors;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.model.ProductColor;
import rs.dev.plasticstore.model.Review;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.model.Wishlist;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.category.SubcategoryService;
import rs.dev.plasticstore.services.color.ColorService;
import rs.dev.plasticstore.services.product.ProductService;
import rs.dev.plasticstore.services.review.ReviewService;
import rs.dev.plasticstore.services.wishlist.WishListService;
import rs.dev.plasticstore.utils.MinMax;
import rs.dev.plasticstore.utils.Sorting;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/product")
public class ProductController {

    @ResponseBody
    @GetMapping(value = "/add_product_to_wishlist/{id}")
    public String addProductToWishList(@PathVariable String id, HttpSession session, @AuthenticationPrincipal UserPrincipal principal) {
        if(principal != null) {
            var wishlistDB = wishListService.findWishListByCustomerId(principal.getUserId());
            var wishlist = new Wishlist();
            wishlist.setUserId(principal.getUserId());
            wishlist.setProductId(Integer.parseInt(id));
            boolean same = false;
            for(Wishlist wishlist1 : wishlistDB)
                if(wishlist.getProductId() == wishlist1.getProductId()) {
                    same = true;
                    break;
                }
            if(!same) wishListService.saveWishList(wishlist);
        }
        var product = productService.findProductById(Integer.parseInt(id));
        var products = new HashSet<Product>();
        products = (HashSet<Product>) session.getAttribute("wishlist_products");
        if(products == null) {
            products = new HashSet<>();
            session.setAttribute("wishlist_products", products);
        }
        products.add(product);

        findMinMaxPriceAndRating(products);
        session.setAttribute("wishlist_products", products);
        return product.getName();
    }

    @PostMapping("/add_review")
    public String addReview(@RequestBody Review review, @AuthenticationPrincipal UserPrincipal principal) {
        if(principal != null) review.setUserId(principal.getUserId());
        reviewService.saveReview(review);
        return "redirect:/product/single_product/" + review.getProductId();
    }

    @ResponseBody
    @GetMapping(value = "/checkCode", produces = "application/json")
    public String checkProductCode(@RequestParam(value = "code") int code) {
        var product = productService.findProductByCode(code);
        if(product.isEmpty()) return "{\"valid\":true}";
        return "{\"valid\":false}";
    }

    @GetMapping(value = "/delete_wishlist/{id}")
    public String deleteWishListProduct(@PathVariable String id, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        var products = new HashSet<Product>();
        if(principal != null) {
            wishListService.deleteWishListByCustomerId(principal.getUserId(), Integer.parseInt(id));
        } else {
            products = (HashSet<Product>) session.getAttribute("wishlist_products");
            products.removeIf(product -> product.getId() == Integer.parseInt(id));
            session.setAttribute("wishlist_products", products);
        }
        return "redirect:/product/show_wishlist";
    }

    private void findMinMaxPriceAndRating(List<Product> list) {
        list.forEach(product -> {
            product.getProductAttributes().forEach(productAttributes -> product.getPrices().add(productAttributes.getPrice()));
            product.getProductAttributes().forEach(productAttributes -> product.getDiscounted_prices().add(productAttributes.getDiscounted_price()));
            product.setMinPrice(MinMax.findMin(product.getPrices()));
            product.setMaxPrice(MinMax.findMax(product.getPrices()));
            product.setMinDiscountedPrice(MinMax.findMin(product.getDiscounted_prices()));
            product.setMaxDiscountedPrice(MinMax.findMax(product.getDiscounted_prices()));
            product.setAverageRating(Math.round(reviewService.findAverageRatingByProductId(product.getId()).orElse(0)));
        });
    }

    private void findMinMaxPriceAndRating(Set<Product> set) {
        set.forEach(product -> {
            product.getProductAttributes().forEach(productAttributes -> product.getPrices().add(productAttributes.getPrice()));
            product.getProductAttributes().forEach(productAttributes -> product.getDiscounted_prices().add(productAttributes.getDiscounted_price()));
            product.setMinPrice(MinMax.findMin(product.getPrices()));
            product.setMaxPrice(MinMax.findMax(product.getPrices()));
            product.setMinDiscountedPrice(MinMax.findMin(product.getDiscounted_prices()));
            product.setMaxDiscountedPrice(MinMax.findMax(product.getDiscounted_prices()));
            product.setAverageRating(Math.round(reviewService.findAverageRatingByProductId(product.getId()).orElse(0)));

        });
    }

    @GetMapping(value = "/refresh_pagination/{id}/{page}/{size}/{minPrice}/{maxPrice}/{sort}/{colors}")
    public String refreshPagination(@PathVariable String id, @PathVariable String page, @PathVariable String size, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String sort, @PathVariable String colors, Model model) {
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        var colors_db = colorService.findAll();
        var selected_colors = new ArrayList<String>();
        var str = "";
        if(colors.equals("empty")) {
            for(Colors colors1 : colors_db) {
                str += " " + colors1.getName();
            }
            selected_colors = new ArrayList<>(Arrays.asList(str.trim().split(" ")));
        } else selected_colors = new ArrayList<>(Arrays.asList(colors.replace("empty", "").trim().split(" ")));
        Page<Product> pageableProduct = productService.findProductsByPriceAndCategory(Integer.parseInt(id), min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/pagination_fragment :: pagination_fragment";
    }

    @GetMapping(value = "/refresh_search_pagination/{search}/{page}/{size}")
    public String refreshPaginationBySearch(@PathVariable String search, @PathVariable String page, @PathVariable String size, Model model) {
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int minPrice = productService.findMinProductPrice();
        int maxPrice = productService.findMaxProductPrice();
        Page<Product> pageableProduct = productService.findProductsBySearch(search, minPrice, maxPrice, PageRequest.of(pageNumber, page_size));
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/pagination_fragment :: pagination_fragment";
    }

    @GetMapping(value = "/refresh_pagination_by_subcategory/{id}/{page}/{size}/{minPrice}/{maxPrice}/{sort}/{colors}")
    public String refreshPaginationBySubCategory(@PathVariable String id, @PathVariable String page, @PathVariable String size, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String sort, @PathVariable String colors, Model model) {
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        var colors_db = colorService.findAll();
        var selected_colors = new ArrayList<String>();
        var str = "";
        if(colors.equals("empty")) {
            for(Colors colors1 : colors_db) {
                str += " " + colors1.getName();
            }
            selected_colors = new ArrayList<>(Arrays.asList(str.trim().split(" ")));
        } else selected_colors = new ArrayList<>(Arrays.asList(colors.replace("empty", "").trim().split(" ")));
        Page<Product> pageableProduct = productService.findProductsByPriceAndSubCategory(Integer.parseInt(id), min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/pagination_fragment :: pagination_fragment";
    }

    @GetMapping(value = "/refresh_colors/{id}/{page}/{size}/{minPrice}/{maxPrice}/{sort}")
    public String refreshProductColors(@PathVariable String id, @PathVariable String page, @PathVariable String size, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String sort, Model model) {
        int category_id = Integer.parseInt(id);
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        var colors = colorService.findAll();
        var selected_colors = new ArrayList<String>();
        var str = "";

        for(Colors color : colors) {
            str += " " + color.getName();
        }
        selected_colors = new ArrayList<>(Arrays.asList(str.trim().split(" ")));

        Page<Product> pageableProduct = productService.findProductsByPriceAndCategory(category_id, min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        var list = new ArrayList<>(pageableProduct.getContent());

        while(pageableProduct.hasNext()) {
            Page<Product> nextPageOfProducts = productService.findProductsByPriceAndCategory(category_id, min, max, selected_colors, pageableProduct.nextPageable());
            list.addAll(nextPageOfProducts.getContent());
            pageableProduct = nextPageOfProducts;
        }

        var map = new HashMap<ProductColor, Integer>();
        for(Product product : list) {
            for(ProductColor productColor : product.getProductColors()) {
                map.merge(productColor, 1, Integer::sum);
            }
        }

        model.addAttribute("colorMap", map);

        return "webapp/product/product_colors_fragment :: product_colors";
    }

    @GetMapping(value = "/refresh_colors_by_subcategory/{id}/{page}/{size}/{minPrice}/{maxPrice}/{sort}")
    public String refreshProductColorsBySubCategory(@PathVariable String id, @PathVariable String page, @PathVariable String size, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String sort, Model model) {
        int category_id = Integer.parseInt(id);
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        var colors = colorService.findAll();
        var selected_colors = new ArrayList<String>();
        var str = "";

        for(Colors color : colors) {
            str += " " + color.getName();
        }
        selected_colors = new ArrayList<>(Arrays.asList(str.trim().split(" ")));

        Page<Product> pageableProduct = productService.findProductsByPriceAndSubCategory(category_id, min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        var list = new ArrayList<>(pageableProduct.getContent());

        while(pageableProduct.hasNext()) {
            Page<Product> nextPageOfProducts = productService.findProductsByPriceAndCategory(category_id, min, max, selected_colors, pageableProduct.nextPageable());
            list.addAll(nextPageOfProducts.getContent());
            pageableProduct = nextPageOfProducts;
        }

        var map = new HashMap<ProductColor, Integer>();
        for(Product product : list) {
            for(ProductColor productColor : product.getProductColors()) {
                map.merge(productColor, 1, Integer::sum);
            }
        }

        model.addAttribute("colorMap", map);

        return "webapp/product/product_colors_fragment :: product_colors";
    }

    @GetMapping(value = "/refresh_product_header/{id}/{page}/{size}/{minPrice}/{maxPrice}/{colors}")
    public String refreshProductHeader(@PathVariable String id, @PathVariable String page, @PathVariable String size, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String colors, Model model) {
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        var colors_db = colorService.findAll();
        var selected_colors = new ArrayList<String>();
        var str = "";
        if(colors.equals("empty")) {
            for(Colors colors1 : colors_db) {
                str += " " + colors1.getName();
            }
            selected_colors = new ArrayList<>(Arrays.asList(str.trim().split(" ")));
        } else selected_colors = new ArrayList<>(Arrays.asList(colors.replace("empty", "").trim().split(" ")));
        Page<Product> pageableProduct = productService.findProductsByPriceAndCategory(Integer.parseInt(id), min, max, selected_colors, PageRequest.of(pageNumber, page_size));
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("offset", pageableProduct.getPageable().getOffset());
        return "webapp/product/products_header_fragment :: products_header";
    }

    @GetMapping(value = "/refresh_searched_product_header/{search}/{page}/{size}")
    public String refreshProductHeader(@PathVariable String search, @PathVariable String page, @PathVariable String size, Model model) {
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int minPrice = productService.findMinProductPrice();
        int maxPrice = productService.findMaxProductPrice();
        Page<Product> pageableProduct = productService.findProductsBySearch(search, minPrice, maxPrice, PageRequest.of(pageNumber, page_size));
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("offset", pageableProduct.getPageable().getOffset());
        return "webapp/product/products_header_fragment :: products_header";
    }

    @GetMapping(value = "/refresh_product_header_by_subcategory/{id}/{page}/{size}/{minPrice}/{maxPrice}/{colors}")
    public String refreshProductHeaderBySubCategory(@PathVariable String id, @PathVariable String page, @PathVariable String size, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String colors, Model model) {
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        var colors_db = colorService.findAll();
        var selected_colors = new ArrayList<String>();
        var str = "";
        if(colors.equals("empty")) {
            for(Colors colors1 : colors_db) {
                str += " " + colors1.getName();
            }
            selected_colors = new ArrayList<>(Arrays.asList(str.trim().split(" ")));
        } else selected_colors = new ArrayList<>(Arrays.asList(colors.replace("empty", "").trim().split(" ")));
        Page<Product> pageableProduct = productService.findProductsByPriceAndSubCategory(Integer.parseInt(id), min, max, selected_colors, PageRequest.of(pageNumber, page_size));
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("offset", pageableProduct.getPageable().getOffset());
        return "webapp/product/products_header_fragment :: products_header";
    }

    @ResponseBody
    @GetMapping(value = "/products_max_price_by_category/{id}")
    public int returnMaxPriceByCategory(@PathVariable String id) {
        return productService.findMaxProductPriceByCategory(Integer.parseInt(id));
    }

    @ResponseBody
    @GetMapping(value = "/products_max_price_by_subcategory/{id}")
    public int returnMaxPriceBySubcategory(@PathVariable String id) {
        return productService.findMaxProductPriceBySubCategory(Integer.parseInt(id));
    }

    @ResponseBody
    @GetMapping(value = "/products_min_price_by_category/{id}")
    public int returnMinPriceByCategory(@PathVariable String id) {
        return productService.findMinProductPriceByCategory(Integer.parseInt(id));
    }

    @ResponseBody
    @GetMapping(value = "/products_min_price_by_subcategory/{id}")
    public int returnMinPriceBySubcategory(@PathVariable String id) {
        return productService.findMinProductPriceBySubCategory(Integer.parseInt(id));
    }

    @GetMapping(value = "/searchProductsByName")
    public String searchProductsByName(@RequestParam(value = "search", required = false) String name, Model model) {
        int page = 0;
        int size = 20;
        int minPrice = productService.findMinProductPrice();
        int maxPrice = productService.findMaxProductPrice();
        Page<Product> pageableProduct = productService.findProductsBySearch(name, minPrice, maxPrice, PageRequest.of(page, size, Sorting.returnSortedOrder("name-asc")));
        findMinMaxPriceAndRating(pageableProduct.getContent());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("search", name);
        return "webapp/product/product_list_searched";
    }

    @GetMapping(value = "/product_list_category/{id}")
    public String showProductListByCategory(@PathVariable String id, Model model, HttpSession session) {
        int page = 0;
        int size = 20;
        Page<Product> pageableProduct = productService.findProductsByCategoryId(Integer.parseInt(id), PageRequest.of(page, size, Sort.by("name").ascending()));
        var map = new HashMap<ProductColor, Integer>();

        findMinMaxPriceAndRating(pageableProduct.getContent());

        for(Product product : pageableProduct.getContent()) {
            for(ProductColor productColor : product.getProductColors()) {
                map.merge(productColor, 1, Integer::sum);
            }
        }

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selected_category", categoryService.findCategoryById(Integer.parseInt(id)).get());
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("colorMap", map);
        model.addAttribute("avgRating", Math.round(reviewService.findAverageRatingByProductId(Integer.parseInt(id)).orElse(0)));
        return "webapp/product/product_list";
    }

    @GetMapping(value = "/product_list_sub_category/{id}")
    public String showProductListBySubCategory(@PathVariable String id, Model model) {
        var subCategory = subcategoryService.findSubCategoryById(Integer.parseInt(id)).get();
        Page<Product> pageableProduct = productService.findProductsBySubCategoryId(subCategory.getId(), PageRequest.of(0, 20));
        var map = new HashMap<ProductColor, Integer>();
        for(Product product : pageableProduct.getContent()) {
            for(ProductColor productColor : product.getProductColors()) {
                map.merge(productColor, 1, (a, b) -> Integer.sum(a, b));
            }
        }
        findMinMaxPriceAndRating(pageableProduct.getContent());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selected_category", subCategory.getCategory());
        model.addAttribute("selected_subcategory", subCategory);
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("colorMap", map);
        return "webapp/product/product_list";
    }

    @GetMapping(value = "/product_list_category_fragment/{id}/{page}/{size}/{minPrice}/{maxPrice}/{sort}/{colors}")
    public String showProductListFragment(@PathVariable String id, @PathVariable String page, @PathVariable String size, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String sort, @PathVariable String colors, Model model) {
        int category_id = Integer.parseInt(id);
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        var colors_db = colorService.findAll();
        var selected_colors = new ArrayList<String>();
        var str = "";
        if(colors.equals("empty")) {
            for(Colors colors1 : colors_db) {
                str += " " + colors1.getName();
            }
            selected_colors = new ArrayList<>(Arrays.asList(str.trim().split(" ")));
        } else selected_colors = new ArrayList<>(Arrays.asList(colors.replace("empty", "").trim().split(" ")));

        Page<Product> pageableProduct = productService.findProductsByPriceAndCategory(category_id, min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        findMinMaxPriceAndRating(pageableProduct.getContent());
        model.addAttribute("selected_category", categoryService.findCategoryById(Integer.parseInt(id)).get());
        model.addAttribute("products", pageableProduct.getContent());
        return "webapp/product/product_list_fragment :: product_list_fragment";
    }

    @GetMapping(value = "/product_list_subcategory_fragment/{id}/{page}/{size}/{minPrice}/{maxPrice}/{sort}/{colors}")
    public String showProductListFragmentBySubCategory(@PathVariable String id, @PathVariable String page, @PathVariable String size, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String sort, @PathVariable String colors, Model model) {
        int subcategory_id = Integer.parseInt(id);
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        var colors_db = colorService.findAll();
        var selected_colors = new ArrayList<String>();
        var str = "";
        if(colors.equals("empty")) {
            for(Colors colors1 : colors_db) {
                str += " " + colors1.getName();
            }
            selected_colors = new ArrayList<>(Arrays.asList(str.trim().split(" ")));
        } else selected_colors = new ArrayList<>(Arrays.asList(colors.replace("empty", "").trim().split(" ")));

        Page<Product> pageableProduct = productService.findProductsByPriceAndSubCategory(subcategory_id, min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        findMinMaxPriceAndRating(pageableProduct.getContent());
        model.addAttribute("selected_category", categoryService.findCategoryById(Integer.parseInt(id)).get());
        model.addAttribute("products", pageableProduct.getContent());
        return "webapp/product/product_list_fragment :: product_list_fragment";
    }

    @GetMapping(value = "/products_by_sub_category/{id}")
    public String showProductsBySubCategory(@PathVariable String id, Model model) {
        var products = productService.findProductsBySubCategoryId(Integer.parseInt(id));
        findMinMaxPriceAndRating(products);
        model.addAttribute("slider_products", products);
        return "webapp/product/product_slider_fragment :: slider_fragment";
    }

    @GetMapping(value = "/product_list_searched_fragment/{search}/{page}/{size}/{sort}")
    public String showSearchedProductsByName(@PathVariable String search, @PathVariable String page, @PathVariable String size, @PathVariable String sort, Model model) {
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int minPrice = productService.findMinProductPrice();
        int maxPrice = productService.findMaxProductPrice();
        Page<Product> pageableProduct = productService.findProductsBySearch(search, minPrice, maxPrice, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        findMinMaxPriceAndRating(pageableProduct.getContent());
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/product_list_searched_fragment :: searched_products";
    }

    @GetMapping(value = "/single_product/{id}")
    public String showSingleProduct(@PathVariable String id, Model model) {
        var product = productService.findProductById(Integer.parseInt(id));
        var similar_products = productService.findSimilarProductsByProductId(product.getCategory().getId());
        findMinMaxPriceAndRating(similar_products);

        var reviews = reviewService.findReviewByProductId(Integer.parseInt(id));

        int[] ratingArray = new int[5];
        int rating1 = 0;
        int rating2 = 0;
        int rating3 = 0;
        int rating4 = 0;
        int rating5 = 0;
        for(Review review : reviews) {
            switch(review.getRating()) {
                case 1:
                    rating1++;
                    break;
                case 2:
                    rating2++;
                    break;
                case 3:
                    rating3++;
                    break;
                case 4:
                    rating4++;
                    break;
                case 5:
                    rating5++;
                    break;
            }
        }
        ratingArray[0] = rating1;
        ratingArray[1] = rating2;
        ratingArray[2] = rating3;
        ratingArray[3] = rating4;
        ratingArray[4] = rating5;

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("single_product", product);
        model.addAttribute("selected_product", new Product());
        model.addAttribute("similar_products", similar_products);
        model.addAttribute("reviews", reviews);
        model.addAttribute("ratings", ratingArray);
        model.addAttribute("avgRating", Math.round(reviewService.findAverageRatingByProductId(Integer.parseInt(id)).orElse(0)));
        return "webapp/product/single_product";
    }

    @GetMapping(value = "/product_modal/{id}")
    public String showSingleProductModal(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.findProductById(Integer.parseInt(id)));
        model.addAttribute("avgRating", Math.round(reviewService.findAverageRatingByProductId(Integer.parseInt(id)).orElse(0)));
        return "webapp/product/single_product_modal :: modal_fragment";
    }

    @GetMapping(value = "/show_wishlist")
    public String showWishListProducts(Model model, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        var products_db = new ArrayList<Wishlist>();
        var products = new HashSet<Product>();
        if(principal != null) {
            products_db.addAll(wishListService.findWishListByCustomerId(principal.getUserId()));
            for(Wishlist wishlist : products_db) {
                products.add(productService.findProductById(wishlist.getProductId()));
            }
            session.setAttribute("wishlist_products", products);
        } else {
            products = (HashSet<Product>) session.getAttribute("wishlist_products");
            if(products == null) {
                products = new HashSet<>();
                session.setAttribute("wishlist_products", products);
            }
        }
        findMinMaxPriceAndRating(products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", products);
        return "webapp/product/wishlist";
    }

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    SubcategoryService subcategoryService;
    @Autowired
    ColorService colorService;
    @Autowired
    WishListService wishListService;
    @Autowired
    ReviewService reviewService;
}
