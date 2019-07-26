package rs.dev.plasticstore.repository.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import rs.dev.plasticstore.model.Category;


public interface CategoryRepository extends JpaRepository<Category, Integer> {


}
