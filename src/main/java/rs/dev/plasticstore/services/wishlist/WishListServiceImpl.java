package rs.dev.plasticstore.services.wishlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Wishlist;
import rs.dev.plasticstore.repository.wishlist.WishListRepository;

import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {

    @Override
    @Transactional
    @CacheEvict(value = "all_wishes", key = "#userId + #productId")
    public void deleteWishListByCustomerId(int userId, int productId) {
        wishListRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    @Transactional
    @CacheEvict(value = "all_wishes", key = "#userId ")
    public void deleteWishListByCustomerId(int userId) {
        wishListRepository.deleteByUserId(userId);
    }

    @Override
    @Transactional
    @Cacheable(value = "all_wishes")
    public List<Wishlist> findAll() {
        return wishListRepository.findAll();
    }

    @Override
    @Transactional
    @Cacheable(value = "all_wishes", key = "#id")
    public List<Wishlist> findWishListByCustomerId(int id) {
        return wishListRepository.findAllByUserId(id);
    }

    @Override
    @Transactional
    @CachePut(value = "all_wishes", key = "#wishlist")
    public void saveWishList(Wishlist wishlist) {
        wishListRepository.save(wishlist);
    }

    @Autowired
    WishListRepository wishListRepository;
}
