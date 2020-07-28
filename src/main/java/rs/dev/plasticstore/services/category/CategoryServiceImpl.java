package rs.dev.plasticstore.services.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Category;
import rs.dev.plasticstore.repository.category.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    @Transactional
    @Cacheable(value = "all_categories")
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Category> findCategoryById(int id) {
        return categoryRepository.findById(id);
    }

    @Autowired
    CategoryRepository categoryRepository;
}
