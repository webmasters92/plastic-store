package rs.dev.plasticstore.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.repository.product.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Product> findProductById(int id) {
        return productRepository.findById(id);
    }

    @Override
    @Transactional
    public Optional<Product> findProductByCode(int code) {
        return productRepository.findProductByCode(code);
    }

    @Override
    @Transactional
    public List<Product> findProductsByCategoryId(int code) {
       return productRepository.findProductsByCategoryId(code);
    }

    @Override
    @Transactional
    public List<Product> findProductsBySubCategoryId(int code) {
        return productRepository.findProductsBySubcategoryId(code);
    }

    @Override
    @Transactional
    public List<Product> findPopularProducts() {
        return productRepository.findTop15ByStatus(true);
    }

    @Override
    @Transactional
    public List<Product> findNewProducts() {
        return productRepository.findTop15ByAvailable(true);
    }

    @Override
    @Transactional
    public List<Product> findProductsOnSale() {
        return productRepository.findTop15BySale(true);
    }

    @Override
    @Transactional
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }
}
