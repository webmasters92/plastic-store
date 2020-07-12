package rs.dev.plasticstore.repository.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Wishlist;

import java.util.List;

@Repository
public interface WishListRepository extends JpaRepository<Wishlist, Integer> {

    List<Wishlist> findAllByUserId(int id);

    void deleteByUserIdAndProductId(int userId, int productId);

    void deleteByUserId(int userId);
}
