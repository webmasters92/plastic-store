package rs.dev.plasticstore.services.promotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Category;
import rs.dev.plasticstore.model.Promotion;
import rs.dev.plasticstore.repository.promotion.PromotionRepository;

import java.util.List;

@Service
public class PromotionServiceImpl implements PromotionService {

    @Override
    @Transactional
    public void deletePromotion(int id) {
        promotionRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Caching(cacheable = @Cacheable(value = "all_products", key = "'ALL'"), put = @CachePut(value = "all_products", key = "'ALL'"))
    public List<Promotion> findAll() {
        return promotionRepository.findAll();
    }

    @Override
    @Transactional
    public Promotion findPromotionByCategory(Category category) {
        return promotionRepository.findByCategory(category);
    }

    @Override
    @Transactional
    public Promotion findPromotionById(int id) {
        return promotionRepository.findById(id).get();
    }

    @Override
    @Transactional
    public void savePromotion(Promotion promotion) {
        promotionRepository.save(promotion);
    }

    @Autowired
    PromotionRepository promotionRepository;
}
