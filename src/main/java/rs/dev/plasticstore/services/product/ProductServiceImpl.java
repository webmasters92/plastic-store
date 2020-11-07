package rs.dev.plasticstore.services.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Product;
import rs.dev.plasticstore.repository.product.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Override
    @Transactional
    @CacheEvict(value = "all_products", key = "#id",allEntries = true)
    public void deleteProduct(int id) {
        productRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Caching(cacheable = @Cacheable(value = "all_products", key = "'ALL'"), put = @CachePut(value = "all_products", key = "'ALL'"))
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    @Transactional
    public int findMaxProductPrice() {
        return productRepository.findMaxProductPrice();
    }

    @Override
    @Transactional
    public int findMaxProductPriceByCategory(int id) {
        return productRepository.findMaxProductPriceByCategory(id);
    }

    @Override
    @Transactional
    public int findMaxProductPriceBySubCategory(int id) {
        return productRepository.findMaxProductPriceBySubCategory(id);
    }

    @Override
    @Transactional
    public int findMinProductPrice() {
        return productRepository.findMinProductPrice();
    }

    @Override
    @Transactional
    public int findMinProductPriceByCategory(int id) {
        //TODO BUG vraca null kad nema proizvoda
        return productRepository.findMinProductPriceByCategory(id);
    }

    @Override
    @Transactional
    public int findMinProductPriceBySubCategory(int id) {
        return productRepository.findMinProductPriceBySubCategory(id);
    }

    @Override
    @Transactional
    public List<Product> findNewProducts() {
        return productRepository.findTop15ByAvailable(true);
    }

    @Override
    @Transactional
    public List<Product> findProductsByCategoryId(int categoryID) {
        return productRepository.findAllProductsByCategoryId(categoryID);
    }

    @Override
    @Transactional
    public List<Product> findPopularProducts() {
        return productRepository.findTop15ByPopularity();
    }

    @Override
    @Transactional
    @Cacheable(value = "product_by_code", key = "#code")
    public Optional<Product> findProductByCode(int code) {
        return productRepository.findProductByCode(code);
    }

    @Override
    @Transactional
    @Cacheable(value = "all_products", key = "#id")
    public Product findProductById(int id) {
        return productRepository.findById(id).get();
    }

    @Override
    @Transactional
    @Cacheable(value = "products_by_category")
    public Page<Product> findProductsByCategoryId(int code, Pageable pageRequest) {
        return productRepository.findProductsByCategoryId(code, pageRequest);
    }

    @Override
    @Transactional
    @Cacheable(value = "products_by_price_and_category")
    public Page<Product> findProductsByPriceAndCategory(int categoryId, int min, int max, ArrayList<String> colors, Pageable pageRequest) {
        return productRepository.findProductsByPriceAndCategory(categoryId, min, max, colors, pageRequest);
    }

    @Override
    @Transactional
    @Cacheable(value = "products_by_price_and_subcategory")
    public Page<Product> findProductsByPriceAndSubCategory(int subcategoryId, int min, int max, ArrayList<String> colors, Pageable pageRequest) {
        return productRepository.findProductsByPriceAndSubcategory(subcategoryId, min, max, colors, pageRequest);
    }

    @Override
    @Transactional
    public Page<Product> findProductsBySearch(String name, int minPrice, int maxPrice, Pageable pageRequest) {
        return productRepository.findProductsBySearch("%" + name + "%", minPrice, maxPrice, pageRequest);
    }

    @Override
    @Transactional
    @Cacheable(value = "products_by_subcategory")
    public Page<Product> findProductsBySubCategoryId(int code, Pageable pageRequest) {
        return productRepository.findProductsBySubcategoryId(code, pageRequest);
    }

    @Override
    @Transactional
    @Cacheable(value = "products_by_subcategory", key = "#code")
    public List<Product> findProductsBySubCategoryId(int code) {
        return productRepository.findProductsBySubcategoryId(code);
    }

    @Override
    @Transactional
    public List<Product> findProductsOnSale() {
        return productRepository.findTop15BySale(true);
    }

    @Override
    public List<Product> findSimilarProductsByProductId(int id) {
        return productRepository.findTop15ByCategoryId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "all_products", key = "#product")
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    @Autowired
    ProductRepository productRepository;
}
