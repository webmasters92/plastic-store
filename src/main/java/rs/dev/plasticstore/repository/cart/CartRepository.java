package rs.dev.plasticstore.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.Cart;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    void deleteByCustomerId(int userId);

    List<Cart> findAllByCustomerId(int id);

    Cart findByCustomerId(int id);

}
