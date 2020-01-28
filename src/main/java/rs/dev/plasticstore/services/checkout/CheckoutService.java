package rs.dev.plasticstore.services.checkout;

import rs.dev.plasticstore.model.Order;

import java.util.List;

public interface CheckoutService {

    List<Order> findAll();

    List<Order> findAllOrdersByCustomerId(int id);

    Order findOrderById(int id);

    void saveOrder(Order cart);

}
