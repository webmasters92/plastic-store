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
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Autowired
    SubcategoryRepository subcategoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Subcategory> findAll() {
        return subcategoryRepository.findAll();
    }

    @Override
    @Transactional
    public List<Subcategory> findSubcategoriesByCategoryId(int id) {
        return subcategoryRepository.findSubcategoryByCategory_Id(id);
    }

    @Override
    @Transactional
    public Optional<Subcategory> findSubCategoryById(int id) {
        return subcategoryRepository.findById(id);
    }
}
