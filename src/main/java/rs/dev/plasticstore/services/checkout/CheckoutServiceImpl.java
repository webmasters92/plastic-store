package rs.dev.plasticstore.services.checkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Order;
import rs.dev.plasticstore.repository.checkout.CheckoutRepository;

import java.util.List;

@Service
public class CheckoutServiceImpl implements CheckoutService {

    @Override
    @Transactional
    @CacheEvict(value = "all_orders", key = "#id")
    public void deleteOrder(int id) {
        checkoutRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Caching(cacheable = @Cacheable(value = "all_orders", key = "'ALL'"), put = @CachePut(value = "all_orders", key = "'ALL'"))
    public List<Order> findAll() {
        return checkoutRepository.findAll();
    }

    @Override
    @Transactional
    @Cacheable(value = "all_orders", key = "#id")
    public List<Order> findAllOrdersByCustomerId(int id) {
        return checkoutRepository.findOrdersByCustomer_id(id);
    }

    @Override
    @Transactional
    @Cacheable(value = "all_orders", key = "#id")
    public List<Order> findAllOrdersByGuestId(int id) {
        return checkoutRepository.findOrdersByGuest_id(id);
    }

    @Override
    @Transactional
    @Cacheable(value = "all_orders", key = "#id")
    public Order findOrderById(int id) {
        return checkoutRepository.findOrderById(id);
    }

    @Override
    @Transactional
    @CachePut(value = "all_orders", key = "#order")
    public void saveOrder(Order order) {
        checkoutRepository.save(order);
    }

    @Autowired
    CheckoutRepository checkoutRepository;
}
