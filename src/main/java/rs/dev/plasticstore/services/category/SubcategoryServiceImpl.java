package rs.dev.plasticstore.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Category;
import rs.dev.plasticstore.model.Subcategory;
import rs.dev.plasticstore.repository.category.CategoryRepository;
import rs.dev.plasticstore.repository.category.SubcategoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Subcategory> findSubcategoriesByCategoryId(int id) {
        return subcategoryRepository.findSubcategoryByCategory_Id(id);
    }
}
