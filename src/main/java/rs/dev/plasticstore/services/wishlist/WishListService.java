package rs.dev.plasticstore.services.wishlist;

import rs.dev.plasticstore.model.Wishlist;

import java.util.List;

public interface WishListService {

    void saveWishList(Wishlist wishlist);

    void deleteWishListByCustomerId(int userId, int productId);

    void deleteWishListByCustomerId(int userId);

    List<Wishlist> findWishListByUserId(int id);

    List<Wishlist> findAll();

}
