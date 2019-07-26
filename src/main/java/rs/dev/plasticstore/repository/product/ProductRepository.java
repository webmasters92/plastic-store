package rs.dev.plasticstore.repository.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> , CrudRepository<Product,Integer> {

}
