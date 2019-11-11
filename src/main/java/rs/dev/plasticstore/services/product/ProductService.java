package rs.dev.plasticstore.services.product;

import rs.dev.plasticstore.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void saveProduct(Product product);
    void deleteProduct(int id);
    Optional<Product> findProductById(int id);
    Optional<Product> findProductByCode(int code);
    List<Product> findAll();
    List<Product> findProductsByCategoryId(int code);
    List<Product> findProductsBySubCategoryId(int code);
    List<Product> findPopularProducts();
    List<Product> findNewProducts();
    List<Product> findProductsOnSale();

}
