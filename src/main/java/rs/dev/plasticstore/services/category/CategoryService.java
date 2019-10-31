package rs.dev.plasticstore.services.category;

import rs.dev.plasticstore.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();

    Optional<Category> findCategoryById(int id);

}
