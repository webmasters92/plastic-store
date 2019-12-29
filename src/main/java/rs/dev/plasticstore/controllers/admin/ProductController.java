package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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
    public String showProductListByCategory(@PathVariable String id, Model model) {
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

    @GetMapping(value = "/single_product/{id}")
    public String showSingleProduct(@PathVariable String id, Model model) {
        var product = productService.findProductById(Integer.parseInt(id));
        var similar_products = productService.findSimilarProductsByProductId(product.getCategory().getId());
        findMinMaxPrice(similar_products);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("single_product", product);
        model.addAttribute("similar_products", similar_products);
        return "webapp/product/single_product";
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
    @GetMapping(value = "/products_min_price/{categoryId}")
    public int returnMinPriceByCategory(@PathVariable String categoryId) {
        return productService.findMinProductPrice(Integer.parseInt(categoryId));
    }

    @ResponseBody
    @GetMapping(value = "/products_max_price/{categoryId}")
    public int returnMaxPriceByCategory(@PathVariable String categoryId) {
        return productService.findMaxProductPrice(Integer.parseInt(categoryId));
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
}
