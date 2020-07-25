package rs.dev.plasticstore.services.customer;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.dev.plasticstore.model.Customer;

import java.util.ArrayList;
import java.util.Optional;

public interface CustomerService extends UserDetailsService {

    void deleteCustomerById(int id);

    ArrayList<Customer> findAll();

    Customer findCustomerByEmail(String email);

    Customer findCustomerById(int id);

    Optional<Customer> findCustomerByResetToken(String resetToken);

    Customer findCustomerByUsername(String username);

    void save(Customer customer);
}
