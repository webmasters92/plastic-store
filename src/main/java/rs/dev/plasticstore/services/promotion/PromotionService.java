package rs.dev.plasticstore.services.promotion;

import rs.dev.plasticstore.model.Category;
import rs.dev.plasticstore.model.Promotion;

import java.util.List;

public interface PromotionService {

    void deletePromotion(int parseInt);

    List<Promotion> findAll();

    Promotion findPromotionByCategory(Category category);

    Promotion findPromotionById(int parseInt);

    void savePromotion(Promotion promotion);
}
