package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.dev.plasticstore.Utils.MinMax;
import rs.dev.plasticstore.Utils.Sorting;
import rs.dev.plasticstore.model.*;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.category.SubcategoryService;
import rs.dev.plasticstore.services.color.ColorService;
import rs.dev.plasticstore.services.product.ProductService;
import rs.dev.plasticstore.services.wishlist.WishListService;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ProductController {

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

    @ResponseBody
    @GetMapping(value = "/checkCode", produces = "application/json")
    public String checkProductCode(@RequestParam(value = "code") int code) {
        var product = productService.findProductByCode(code);
        if(product.isEmpty()) return "{\"valid\":true}";
        return "{\"valid\":false}";
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
        Page<Product> pageableProduct = productService.findProductsByPrice(Integer.parseInt(id), min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
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
        Page<Product> pageableProduct = productService.findProductsByPrice(Integer.parseInt(id), min, max, selected_colors, PageRequest.of(pageNumber, page_size));
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

    @GetMapping(value = "/product_list_category/{id}")
    public String showProductListByCategory(@PathVariable String id, Model model, HttpSession session) {
        int page = 0;
        int size = 16;
        Page<Product> pageableProduct = productService.findProductsByCategoryId(Integer.parseInt(id), PageRequest.of(page, size, Sort.by("name").ascending()));
        var map = new HashMap<ProductColor, Integer>();

        findMinMaxPrice(pageableProduct.getContent());

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
        System.out.println(session.getAttribute("cart"));
        return "webapp/product/product_list";
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

        Page<Product> pageableProduct = productService.findProductsByPrice(category_id, min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        var list = new ArrayList<>(pageableProduct.getContent());

        while(pageableProduct.hasNext()) {
            Page<Product> nextPageOfProducts = productService.findProductsByPrice(category_id, min, max, selected_colors, pageableProduct.nextPageable());
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
            Page<Product> nextPageOfProducts = productService.findProductsByPrice(category_id, min, max, selected_colors, pageableProduct.nextPageable());
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

    @GetMapping(value = "/product_list_sub_category/{id}")
    public String showProductListBySubCategory(@PathVariable String id, Model model) {
        var subCategory = subcategoryService.findSubCategoryById(Integer.parseInt(id)).get();
        Page<Product> pageableProduct = productService.findProductsBySubCategoryId(subCategory.getId(), PageRequest.of(0, 16));
        var map = new HashMap<ProductColor, Integer>();
        for(Product product : pageableProduct.getContent()) {
            for(ProductColor productColor : product.getProductColors()) {
                map.merge(productColor, 1, Integer::sum);
            }
        }
        findMinMaxPrice(pageableProduct.getContent());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selected_category", subCategory.getCategory());
        model.addAttribute("selected_subcategory", subCategory);
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("colorMap", map);
        return "webapp/product/product_list";
    }

    @GetMapping(value = "/product_list_searched_fragment/{search}/{page}/{size}/{sort}")
    public String showSearchedProductsByName(@PathVariable String search, @PathVariable String page, @PathVariable String size, @PathVariable String sort, Model model) {
        int pageNumber = Integer.parseInt(page);
        int page_size = Integer.parseInt(size);
        int minPrice = productService.findMinProductPrice();
        int maxPrice = productService.findMaxProductPrice();
        Page<Product> pageableProduct = productService.findProductsBySearch(search, minPrice, maxPrice, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        findMinMaxPrice(pageableProduct.getContent());
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/product_list_searched_fragment :: searched_products";
    }

    @GetMapping(value = "/searchProductsByName")
    public String searchProductsByName(@RequestParam(value = "search", required = false) String name, Model model) {
        int page = 0;
        int size = 16;
        int minPrice = productService.findMinProductPrice();
        int maxPrice = productService.findMaxProductPrice();
        Page<Product> pageableProduct = productService.findProductsBySearch(name, minPrice, maxPrice, PageRequest.of(page, size, Sorting.returnSortedOrder("name-asc")));
        findMinMaxPrice(pageableProduct.getContent());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("search", name);
        return "webapp/product/product_list_searched";
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

        Page<Product> pageableProduct = productService.findProductsByPrice(category_id, min, max, selected_colors, PageRequest.of(pageNumber, page_size, Sorting.returnSortedOrder(sort)));
        findMinMaxPrice(pageableProduct.getContent());
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
        findMinMaxPrice(pageableProduct.getContent());
        model.addAttribute("selected_category", categoryService.findCategoryById(Integer.parseInt(id)).get());
        model.addAttribute("products", pageableProduct.getContent());
        return "webapp/product/product_list_fragment :: product_list_fragment";
    }

    @GetMapping(value = "/single_product/{id}")
    public String showSingleProduct(@PathVariable String id, Model model) {
        System.out.println("prikaz single product");
        var product = productService.findProductById(Integer.parseInt(id));
        var similar_products = productService.findSimilarProductsByProductId(product.getCategory().getId());
        findMinMaxPrice(similar_products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("single_product", product);
        model.addAttribute("selected_product", new Product());
        model.addAttribute("similar_products", similar_products);
        return "webapp/product/single_product";
    }

    @ResponseBody
    @GetMapping(value = "/add_product_to_wishlist/{id}")
    public String addProductToWishList(@PathVariable String id, HttpSession session, @AuthenticationPrincipal UserPrincipal principal) {
        if(principal != null) {
            var wishlistDB = wishListService.findWishListByUserId(principal.getUserId());
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

        findMinMaxPrice(products);
        session.setAttribute("wishlist_products", products);
        return product.getName();
    }

    @GetMapping(value = "/show_wishlist")
    public String showWishListProducts(Model model, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        var products_db = new ArrayList<Wishlist>();
        var products = new HashSet<Product>();
        if(principal != null) {
            products_db.addAll(wishListService.findWishListByUserId(principal.getUserId()));
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
        findMinMaxPrice(products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", products);
        return "webapp/product/wishlist";
    }

    @GetMapping(value = "/delete_wishlist/{id}")
    public String deleteWishListProduct(@PathVariable String id, @AuthenticationPrincipal UserPrincipal principal, HttpSession session) {
        var products = new HashSet<Product>();
        if(principal != null) {
            wishListService.deleteWishList(principal.getUserId(), Integer.parseInt(id));
        } else {
            products = (HashSet<Product>) session.getAttribute("wishlist_products");
            products.removeIf(product -> product.getId() == Integer.parseInt(id));
            session.setAttribute("wishlist_products", products);
        }
        return "redirect:/product/show_wishlist";
    }

    @GetMapping(value = "/product_modal/{id}")
    public String showSingleProductModal(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.findProductById(Integer.parseInt(id)));
        return "webapp/product/single_product_modal :: modal_fragment";
    }

    @GetMapping(value = "/products_by_sub_category/{id}")
    public String showProductsBySubCategory(@PathVariable String id, Model model) {
        var products = productService.findProductsBySubCategoryId(Integer.parseInt(id));
        findMinMaxPrice(products);
        model.addAttribute("slider_products", products);
        return "webapp/product/product_slider_fragment :: slider_fragment";
    }

    @ResponseBody
    @GetMapping(value = "/products_min_price_by_category/{id}")
    public int returnMinPriceByCategory(@PathVariable String id) {
        return productService.findMinProductPriceByCategory(Integer.parseInt(id));
    }

    @ResponseBody
    @GetMapping(value = "/products_max_price_by_category/{id}")
    public int returnMaxPriceByCategory(@PathVariable String id) {
        return productService.findMaxProductPriceByCategory(Integer.parseInt(id));
    }

    @ResponseBody
    @GetMapping(value = "/products_min_price_by_subcategory/{id}")
    public int returnMinPriceBySubcategory(@PathVariable String id) {
        return productService.findMinProductPriceBySubCategory(Integer.parseInt(id));
    }

    @ResponseBody
    @GetMapping(value = "/products_max_price_by_subcategory/{id}")
    public int returnMaxPriceBySubcategory(@PathVariable String id) {
        return productService.findMaxProductPriceBySubCategory(Integer.parseInt(id));
    }

    private void findMinMaxPrice(List<Product> list) {
        list.forEach(product -> {
            product.getProductAttributes().forEach(productAttributes -> product.getPrices().add(productAttributes.getPrice()));
            product.getProductAttributes().forEach(productAttributes -> product.getDiscounted_prices().add(productAttributes.getDiscounted_price()));
            product.setMinPrice(MinMax.findMin(product.getPrices()));
            product.setMaxPrice(MinMax.findMax(product.getPrices()));
            product.setMinDiscountedPrice(MinMax.findMin(product.getDiscounted_prices()));
            product.setMaxDiscountedPrice(MinMax.findMax(product.getDiscounted_prices()));
        });
    }

    private void findMinMaxPrice(Set<Product> set) {
        set.forEach(product -> {
            product.getProductAttributes().forEach(productAttributes -> product.getPrices().add(productAttributes.getPrice()));
            product.getProductAttributes().forEach(productAttributes -> product.getDiscounted_prices().add(productAttributes.getDiscounted_price()));
            product.setMinPrice(MinMax.findMin(product.getPrices()));
            product.setMaxPrice(MinMax.findMax(product.getPrices()));
            product.setMinDiscountedPrice(MinMax.findMin(product.getDiscounted_prices()));
            product.setMaxDiscountedPrice(MinMax.findMax(product.getDiscounted_prices()));
        });
    }
}
