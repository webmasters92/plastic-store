package rs.dev.plasticstore.services.product;

import org.springframework.data.domain.*;
import rs.dev.plasticstore.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void saveProduct(Product product);
    void deleteProduct(int id);
    Product findProductById(int id);
    Optional<Product> findProductByCode(int code);
    List<Product> findAll();
    Page<Product> findProductsByCategoryId(int code, Pageable pageRequest);
    Page<Product> findProductsBySubCategoryId(int code,Pageable pageRequest);
    List<Product> findProductsBySubCategoryId(int code);
    List<Product> findPopularProducts();
    List<Product> findNewProducts();
    List<Product> findProductsOnSale();
    Page<Product> findProductsByNameLike(String name,Pageable pageRequest);

}
