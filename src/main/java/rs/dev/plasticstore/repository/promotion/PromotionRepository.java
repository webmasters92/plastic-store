package rs.dev.plasticstore.repository.promotion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Category;
import rs.dev.plasticstore.model.Promotion;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {

    Promotion findByCategory(Category category);
}
