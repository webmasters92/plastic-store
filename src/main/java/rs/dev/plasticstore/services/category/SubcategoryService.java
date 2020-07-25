package rs.dev.plasticstore.services.category;

import rs.dev.plasticstore.model.Subcategory;

import java.util.List;
import java.util.Optional;

public interface SubcategoryService {

    List<Subcategory> findAll();

    Optional<Subcategory> findSubCategoryById(int id);

    List<Subcategory> findSubcategoriesByCategoryId(int id);

}
