package rs.dev.plasticstore.services.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Wishlist;
import rs.dev.plasticstore.repository.wishlist.WishListRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {

    @Autowired
    WishListRepository wishListRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void saveWishList(Wishlist wishlist) {
        wishListRepository.save(wishlist);
    }

    @Override
    @Transactional
    public void deleteWishListByCustomerId(int userId, int productId) {
        wishListRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    @Transactional
    public void deleteWishListByCustomerId(int userId) {
        wishListRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    public List<Wishlist> findWishListByUserId(int id) {
        return wishListRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    public List<Wishlist> findAll() {
        return wishListRepository.findAll();
    }
}
