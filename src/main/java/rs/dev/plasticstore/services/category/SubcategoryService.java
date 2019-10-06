package rs.dev.plasticstore.services.category;

import rs.dev.plasticstore.model.Subcategory;

import java.util.List;

public interface SubcategoryService {

    List<Subcategory> findSubcategoriesByCategoryId(int id);
}
