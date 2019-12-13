package rs.dev.plasticstore.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.repository.product.ProductRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

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
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Product findProductById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    @Transactional
    public Optional<Product> findProductByCode(int code) {
        return productRepository.findProductByCode(code);
    }

    @Override
    @Transactional
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public Page<Product> findProductsByCategoryId(int code, Pageable pageRequest) {
        return productRepository.findProductsByCategoryId(code, pageRequest);
    }

    @Override
    @Transactional
    public Page<Product> findProductsBySubCategoryId(int code, Pageable pageRequest) {
        return productRepository.findProductsBySubcategoryId(code, pageRequest);
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
    public Page<Product> findProductsBySearch(String name, int minPrice, int maxPrice, Pageable pageRequest) {
        return productRepository.findProductsBySearch("%" + name + "%", minPrice, maxPrice, pageRequest);
    }

    @Override
    @Transactional
    public Page<Product> findProductsByPrice(int categoryId, int min, int max, ArrayList<String> colors, Pageable pageRequest) {
        return productRepository.findProductsByPrice(categoryId, min, max, colors, pageRequest);
    }

    @Override
    @Transactional
    public int findMinProductPrice() {
        return productRepository.findMinProductPrice();
    }

    @Override
    @Transactional
    public int findMaxProductPrice() {
        return productRepository.findMaxProductPrice();
    }

    @Override
    @Transactional
    public int findMinProductPrice(int parseInt) {
        return productRepository.findMinProductPriceByCategory(parseInt);
    }

    @Override
    @Transactional
    public int findMaxProductPrice(int parseInt) {
        return productRepository.findMaxProductPriceByCategory(parseInt);
    }
}
