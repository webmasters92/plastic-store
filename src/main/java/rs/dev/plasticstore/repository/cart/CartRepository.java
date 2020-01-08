package rs.dev.plasticstore.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.Cart;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Cart findByCustomerId(int id);

    List<Cart> findAllByCustomerId(int id);

    void deleteByCustomerId(int userId);


}
