package rs.dev.plasticstore.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Product;

import java.util.*;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Optional<Product> findProductByCode(int code);

    Page<Product> findProductsByCategoryId(int code, Pageable pageRequest);

    Page<Product> findProductsBySubcategoryId(int code, Pageable pageRequest);

    List<Product> findProductsBySubcategoryId(int code);

    List<Product> findTop15BySale(boolean sale);

    List<Product> findTop15ByStatus(boolean status);

    List<Product> findTop15ByCategoryId(int code);

    List<Product> findTop15ByAvailable(boolean available);

    @Query(value = "select * from products p left join productAttributes pa on p.id=pa.product_id where p.name like ?1 and pa.product_price between ?2 and ?3 group by p.id", countQuery = "select count(*) from products p inner join productAttributes pa on p.id=pa.product_id  where p.name like ?1 and pa.product_price between ?2 and ?3 group by p.id", nativeQuery = true)
    Page<Product> findProductsBySearch(String name, int minPrice, int maxPrice, Pageable pageRequest);

    @Query(value = "select * from products p left join productAttributes pa on p.id=pa.product_id left join product_color pc on p.id = pc.product_id where p.category_id=?1 and pa.product_price between ?2 and ?3 and pc.name in ?4 group by p.id", countQuery = "select count(*) from products p inner join productAttributes pa on p.id=pa.product_id left join product_color pc on p.id = pc.product_id where p.category_id=?1 and pa.product_price between ?2 and ?3 and pc.name in ?4 group by p.id", nativeQuery = true)
    Page<Product> findProductsByPrice(int categoryId, int minPrice, int maxPrice, ArrayList<String> colors, Pageable pageable);

    @Query(value = "select min(pa.product_price) from products p join productAttributes pa on p.id=pa.product_id", nativeQuery = true)
    int findMinProductPrice();

    @Query(value = "select max(pa.product_price) from products p join productAttributes pa on p.id=pa.product_id", nativeQuery = true)
    int findMaxProductPrice();

    @Query(value = "select min(pa.product_price) from products p join productAttributes pa on p.id=pa.product_id where p.category_id=?1", nativeQuery = true)
    int findMinProductPriceByCategory(int categoryId);

    @Query(value = "select max(pa.product_price) from products p join productAttributes pa on p.id=pa.product_id where p.category_id=?1", nativeQuery = true)
    int findMaxProductPriceByCategory(int categoryId);
}
