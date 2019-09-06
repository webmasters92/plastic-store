package rs.dev.plasticstore.services.product;

import rs.dev.plasticstore.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    void saveProduct(Product product);

    List<Product> findAll();

    Optional<Product> findProductById(int id);

    void deleteProduct(int id);



}
