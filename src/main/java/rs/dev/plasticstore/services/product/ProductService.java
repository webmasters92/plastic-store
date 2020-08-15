package rs.dev.plasticstore.services.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rs.dev.plasticstore.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ProductService {

    void deleteProduct(int id);

    List<Product> findAll();

    int findMaxProductPrice();

    int findMaxProductPriceByCategory(int id);

    int findMaxProductPriceBySubCategory(int id);

    int findMinProductPrice();

    int findMinProductPriceByCategory(int id);

    int findMinProductPriceBySubCategory(int id);

    List<Product> findNewProducts();

    List<Product> findProductsByCategoryId(int id);

    List<Product> findPopularProducts();

    Optional<Product> findProductByCode(int code);

    Product findProductById(int id);

    Page<Product> findProductsByCategoryId(int code, Pageable pageRequest);

    Page<Product> findProductsByPriceAndCategory(int category_id, int min, int max, ArrayList<String> colors, Pageable pageRequest);

    Page<Product> findProductsByPriceAndSubCategory(int subcategory_id, int min, int max, ArrayList<String> colors, Pageable pageRequest);

    Page<Product> findProductsBySearch(String name, int minPrice, int maxPrice, Pageable pageRequest);

    Page<Product> findProductsBySubCategoryId(int code, Pageable pageRequest);

    List<Product> findProductsBySubCategoryId(int code);

    List<Product> findProductsOnSale();

    List<Product> findSimilarProductsByProductId(int id);

    void saveProduct(Product product);
}
