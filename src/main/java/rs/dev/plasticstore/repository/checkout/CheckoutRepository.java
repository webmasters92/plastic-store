package rs.dev.plasticstore.repository.checkout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.dev.plasticstore.model.Order;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Order, Integer> {

    Order findOrderById(int id);

    @Query(value = "select * from orders o where customer_id=?1", nativeQuery = true)
    List<Order> findOrdersByCustomer_id(int id);

}
