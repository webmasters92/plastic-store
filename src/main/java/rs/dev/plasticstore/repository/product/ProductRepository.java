package rs.dev.plasticstore.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findProductByCode(int code);

    Page<Product> findProductsByCategoryId(int code, Pageable pageRequest);

    Page<Product> findProductsBySubcategoryId(int code, Pageable pageRequest);

    List<Product> findProductsBySubcategoryId(int code);

    List<Product> findTop15BySale(boolean sale);

    List<Product> findTop15ByStatus(boolean status);

    List<Product> findTop15ByAvailable(boolean available);

    Page<Product> findProductsByNameLike(String name,Pageable pageRequest);
}
