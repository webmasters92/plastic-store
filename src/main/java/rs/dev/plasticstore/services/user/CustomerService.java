package rs.dev.plasticstore.services.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.dev.plasticstore.model.Customer;

import java.util.Optional;

public interface CustomerService extends UserDetailsService {

    void save(Customer customer);

    Customer findCustomerById(int id);

    Customer findCustomerByUsername(String username);

    Customer findCustomerByEmail(String email);

    Optional<Customer> findCustomerByResetToken(String resetToken);
}
