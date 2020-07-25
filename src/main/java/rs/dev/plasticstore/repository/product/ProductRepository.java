package rs.dev.plasticstore.repository.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select max(pa.product_price) from products p join product_attributes pa on p.id=pa.product_id", nativeQuery = true)
    int findMaxProductPrice();

    @Query(value = "select max(pa.product_price) from products p join product_attributes pa on p.id=pa.product_id where p.category_id=?1", nativeQuery = true)
    int findMaxProductPriceByCategory(int categoryId);

    @Query(value = "select max(pa.product_price) from products p join product_attributes pa on p.id=pa.product_id where p.sub_category_id=?1", nativeQuery = true)
    int findMaxProductPriceBySubCategory(int categoryId);

    @Query(value = "select min(pa.product_price) from products p join product_attributes pa on p.id=pa.product_id", nativeQuery = true)
    int findMinProductPrice();

    @Query(value = "select min(pa.product_price) from products p join product_attributes pa on p.id=pa.product_id where p.category_id=?1", nativeQuery = true)
    int findMinProductPriceByCategory(int categoryId);

    @Query(value = "select min(pa.product_price) from products p join product_attributes pa on p.id=pa.product_id where p.sub_category_id=?1", nativeQuery = true)
    int findMinProductPriceBySubCategory(int categoryId);

    Optional<Product> findProductByCode(int code);

    Page<Product> findProductsByCategoryId(int code, Pageable pageRequest);

    @Query(value = "select * from products p left join product_attributes pa on p.id=pa.product_id left join product_color pc on p.id = pc.product_id where p.category_id=?1 and pa.product_price between ?2 and ?3 and pc.name in ?4 group by p.id", countQuery = "select count(*) from products p inner join product_attributes pa on p.id=pa.product_id left join product_color pc on p.id = pc.product_id where p.category_id=?1 and pa.product_price between ?2 and ?3 and pc.name in ?4 group by p.id", nativeQuery = true)
    Page<Product> findProductsByPriceAndCategory(int categoryId, int minPrice, int maxPrice, ArrayList<String> colors, Pageable pageable);

    @Query(value = "select * from products p left join product_attributes pa on p.id=pa.product_id left join product_color pc on p.id = pc.product_id where p.sub_category_id=?1 and pa.product_price between ?2 and ?3 and pc.name in ?4 group by p.id", countQuery = "select count(*) from products p inner join product_attributes pa on p.id=pa.product_id left join product_color pc on p.id = pc.product_id where p.sub_category_id=?1 and pa.product_price between ?2 and ?3 and pc.name in ?4 group by p.id", nativeQuery = true)
    Page<Product> findProductsByPriceAndSubcategory(int subcategoryId, int minPrice, int maxPrice, ArrayList<String> colors, Pageable pageable);

    @Query(value = "select * from products p left join product_attributes pa on p.id=pa.product_id where p.name like ?1 and pa.product_price between ?2 and ?3 group by p.id", countQuery = "select count(*) from products p inner join product_attributes pa on p.id=pa.product_id  where p.name like ?1 and pa.product_price between ?2 and ?3 group by p.id", nativeQuery = true)
    Page<Product> findProductsBySearch(String name, int minPrice, int maxPrice, Pageable pageRequest);

    Page<Product> findProductsBySubcategoryId(int code, Pageable pageRequest);

    List<Product> findProductsBySubcategoryId(int code);

    @Query(value = "select * from products p order by date_created desc limit 15", nativeQuery = true)
    List<Product> findTop15ByAvailable(boolean available);

    List<Product> findTop15ByCategoryId(int code);

    List<Product> findTop15BySale(boolean sale);

    List<Product> findTop15ByStatus(boolean status);
}
