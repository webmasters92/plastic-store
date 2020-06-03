package rs.dev.plasticstore.services.customer;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.dev.plasticstore.model.Customer;

import java.util.ArrayList;
import java.util.Optional;

public interface CustomerService extends UserDetailsService {

    ArrayList<Customer> findAll();

    void save(Customer customer);

    Customer findCustomerById(int id);

    Customer findCustomerByUsername(String username);

    Customer findCustomerByEmail(String email);

    Optional<Customer> findCustomerByResetToken(String resetToken);

    void deleteCustomerById(int id);
}
