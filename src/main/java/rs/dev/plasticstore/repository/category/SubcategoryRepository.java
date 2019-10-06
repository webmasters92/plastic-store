package rs.dev.plasticstore.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.Category;
import rs.dev.plasticstore.model.Subcategory;

import java.util.List;


public interface SubcategoryRepository extends JpaRepository<Subcategory, Integer> {

    List<Subcategory> findSubcategoryByCategory_Id(int id);
}
