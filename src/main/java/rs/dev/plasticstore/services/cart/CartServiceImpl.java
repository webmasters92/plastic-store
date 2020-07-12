package rs.dev.plasticstore.services.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Cart;
import rs.dev.plasticstore.repository.cart.CartRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<Cart> findAll() {
        return cartRepository.findAll();
    }

    @Override
    @Transactional
    public Cart findCartByCustomerId(int id) {
        return cartRepository.findByCustomerId(id);
    }

    @Override
    @Transactional
    public void saveCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    @Transactional
    public void deleteCartByCustomerId(int userId) {
        cartRepository.deleteByCustomerId(userId);
    }
}
