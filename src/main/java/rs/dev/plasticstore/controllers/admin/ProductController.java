package rs.dev.plasticstore.controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import rs.dev.plasticstore.Utils.Sorting;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.services.category.CategoryService;
import rs.dev.plasticstore.services.category.SubcategoryService;
import rs.dev.plasticstore.services.product.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    SubcategoryService subcategoryService;

    @ResponseBody
    @GetMapping(value = "/checkCode", produces = "application/json")
    public String checkProductCode(@RequestParam(value = "code") int code) {
        var product = productService.findProductByCode(code);
        if(product.isEmpty()) return "{\"valid\":true}";
        return "{\"valid\":false}";
    }

    @GetMapping(value = "/refresh_pagination/{id}/{page}/{minPrice}/{maxPrice}/{sort}")
    public String refreshPaginationByPrice(@PathVariable String id, @PathVariable String page, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String sort, Model model) {
        int pageNumber = Integer.parseInt(page);
        int size = 12;
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        Page<Product> pageableProduct = productService.findProductsByPrice(Integer.parseInt(id), min, max, PageRequest.of(pageNumber, size, Sorting.returnSortedOrder(sort)));
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/pagination_fragment :: pagination_fragment";
    }

    @GetMapping(value = "/refresh_search_pagination/{search}/{page}")
    public String refreshPaginationBySearch(@PathVariable String search, @PathVariable String page, Model model) {
        int pageNumber = Integer.parseInt(page);
        int size = 12;
        Page<Product> pageableProduct = productService.findProductsByNameLike(search, PageRequest.of(pageNumber, size));
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/pagination_fragment :: pagination_fragment";
    }

    @GetMapping(value = "/refresh_product_header/{id}/{page}/{minPrice}/{maxPrice}")
    public String refreshProductHeader(@PathVariable String id, @PathVariable String page, @PathVariable String minPrice, @PathVariable String maxPrice, Model model) {
        int pageNumber = Integer.parseInt(page);
        int size = 12;
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        Page<Product> pageableProduct = productService.findProductsByPrice(Integer.parseInt(id), min, max, PageRequest.of(pageNumber, size));
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/products_header_fragment :: products_header";
    }

    @GetMapping(value = "/product_list_category/{id}")
    public String showProductListByCategory(@PathVariable String id, Model model) {
        int page = 0;
        int size = 12;
        Page<Product> pageableProduct = productService.findProductsByCategoryId(Integer.parseInt(id), PageRequest.of(page, size,Sort.by("name").ascending()));

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selected_category", categoryService.findCategoryById(Integer.parseInt(id)).get());
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/product_list";
    }

    @GetMapping(value = "/product_list_sub_category/{id}")
    public String showProductListBySubCategory(@PathVariable String id, Model model) {
        var subCategory = subcategoryService.findSubCategoryById(Integer.parseInt(id)).get();
        Page<Product> pageableProduct = productService.findProductsBySubCategoryId(subCategory.getId(), PageRequest.of(0, 12));

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("selected_category", subCategory.getCategory());
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/product_list";
    }

    @GetMapping(value = "/product_list_searched_fragment/{search}/{page}")
    public String showSearchedProductsByName(@PathVariable String search, @PathVariable String page, Model model) {
        int pageNumber = Integer.parseInt(page);
        int size = 12;

        Page<Product> pageableProduct = productService.findProductsByNameLike(search, PageRequest.of(pageNumber, size));

        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        return "webapp/product/product_list_searched_fragment :: searched_products";
    }

    @GetMapping(value = "/searchProductsByName")
    public String searchProductsByName(@RequestParam(value = "search", required = false) String name, Model model) {
        int page = 0;
        int size = 12;

        Page<Product> pageableProduct = productService.findProductsByNameLike(name, PageRequest.of(page, size));

        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("products", pageableProduct.getContent());
        model.addAttribute("pagination", pageableProduct);
        model.addAttribute("search", name);
        return "webapp/product/product_list_searched";
    }

    @GetMapping(value = "/product_list_category_fragment/{id}/{page}/{minPrice}/{maxPrice}/{sort}")
    public String showProductListFragment(@PathVariable String id, @PathVariable String page, @PathVariable String minPrice, @PathVariable String maxPrice, @PathVariable String sort, Model model) {
        int category_id = Integer.parseInt(id);
        int pageNumber = Integer.parseInt(page);
        int min = Integer.parseInt(minPrice);
        int max = Integer.parseInt(maxPrice);
        Page<Product> pageableProduct = productService.findProductsByPrice(category_id, min, max, PageRequest.of(pageNumber, 12, Sorting.returnSortedOrder(sort)));
        model.addAttribute("selected_category", categoryService.findCategoryById(Integer.parseInt(id)).get());
        model.addAttribute("products", pageableProduct.getContent());
        return "webapp/product/product_list_fragment :: product_list_fragment";
    }

    @GetMapping(value = "/single_product/{id}")
    public String showSingleProduct(@PathVariable String id, Model model) {
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("product", productService.findProductById(Integer.parseInt(id)));
        return "webapp/product/single_product";
    }

    @GetMapping(value = "/product_modal/{id}")
    public String showSingleProductModal(@PathVariable String id, Model model) {
        model.addAttribute("product", productService.findProductById(Integer.parseInt(id)));
        return "webapp/product/single_product_modal :: modal_fragment";
    }

    @GetMapping(value = "/products_by_sub_category/{id}")
    public String showProductsBySubCategory(@PathVariable String id, Model model) {
        model.addAttribute("slider_products", productService.findProductsBySubCategoryId(Integer.parseInt(id)));
        return "webapp/product/product_slider_fragment :: slider_fragment";
    }
}
