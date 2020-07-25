package rs.dev.plasticstore.services.wishlist;

import rs.dev.plasticstore.model.Wishlist;

import java.util.List;

public interface WishListService {

    void deleteWishListByCustomerId(int userId, int productId);

    void deleteWishListByCustomerId(int userId);

    List<Wishlist> findAll();

    List<Wishlist> findWishListByCustomerId(int id);

    void saveWishList(Wishlist wishlist);

}
