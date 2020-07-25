package rs.dev.plasticstore.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Subcategory;
import rs.dev.plasticstore.repository.category.SubcategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubcategoryServiceImpl implements SubcategoryService {

    @Override
    @Transactional
    @Cacheable(value = "all_subcategories")
    public List<Subcategory> findAll() {
        return subcategoryRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Subcategory> findSubCategoryById(int id) {
        return subcategoryRepository.findById(id);
    }

    @Override
    @Transactional
    public List<Subcategory> findSubcategoriesByCategoryId(int id) {
        return subcategoryRepository.findSubcategoryByCategory_Id(id);
    }

    @Autowired
    SubcategoryRepository subcategoryRepository;
}
