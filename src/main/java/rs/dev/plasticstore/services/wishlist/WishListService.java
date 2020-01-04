package rs.dev.plasticstore.services.wishlist;

import rs.dev.plasticstore.model.Wishlist;

import java.util.List;

public interface WishListService {

    void saveWishList(Wishlist wishlist);

    void deleteWishList(int userId, int productId);

    List<Wishlist> findWishListByUserId(int id);

    List<Wishlist> findAll();

}
