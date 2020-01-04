package rs.dev.plasticstore.services.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.dev.plasticstore.model.Product;

import java.util.*;

public interface ProductService {

    void saveProduct(Product product);

    void deleteProduct(int id);

    Product findProductById(int id);

    Optional<Product> findProductByCode(int code);

    List<Product> findAll();

    Page<Product> findProductsByCategoryId(int code, Pageable pageRequest);

    Page<Product> findProductsBySubCategoryId(int code, Pageable pageRequest);

    List<Product> findProductsBySubCategoryId(int code);

    List<Product> findPopularProducts();

    List<Product> findNewProducts();

    List<Product> findProductsOnSale();

    Page<Product> findProductsBySearch(String name, int minPrice, int maxPrice, Pageable pageRequest);

    Page<Product> findProductsByPrice(int category_id, int min, int max, ArrayList<String> colors, Pageable pageRequest);

    Page<Product> findProductsByPriceAndSubCategory(int subcategory_id, int min, int max, ArrayList<String> colors, Pageable pageRequest);

    int findMinProductPrice();

    int findMaxProductPrice();

    int findMinProductPriceByCategory(int id);

    int findMaxProductPriceByCategory(int id);

    int findMinProductPriceBySubCategory(int id);

    int findMaxProductPriceBySubCategory(int id);

    List<Product> findSimilarProductsByProductId(int id);
}
