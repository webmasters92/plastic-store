package rs.dev.plasticstore.services.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Order;
import rs.dev.plasticstore.repository.checkout.CheckoutRepository;

import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Override
    @Transactional
    public List<Order> findAll() {
        return checkoutRepository.findAll();
    }

    @Override
    @Transactional
    public List<Order> findAllOrdersByCustomerId(int id) {
        return checkoutRepository.findOrdersByCustomer_id(id);
    }

    @Override
    public Order findOrderById(int id) {
        return checkoutRepository.findOrderById(id);
    }

    @Override
    @Transactional
    public void saveOrder(Order order) {
        checkoutRepository.save(order);
    }

    @Autowired
    CheckoutRepository checkoutRepository;
}