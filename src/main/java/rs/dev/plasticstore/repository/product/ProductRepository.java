package rs.dev.plasticstore.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findProductByCode(int code);

    List<Product> findProductsByCategoryId(int code);

    List<Product> findProductsBySubcategoryId(int code);
}
