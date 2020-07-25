package rs.dev.plasticstore.services.cart;

import rs.dev.plasticstore.model.Cart;

import java.util.List;

public interface CartService {

    void deleteCartByCustomerId(int customerId);

    List<Cart> findAll();

    Cart findCartByCustomerId(int id);

    void saveCart(Cart cart);

}
