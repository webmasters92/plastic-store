package rs.dev.plasticstore.services.user;

import org.springframework.security.core.userdetails.UserDetailsService;
import rs.dev.plasticstore.model.Customer;

public interface CustomerService extends UserDetailsService {

    void save(Customer customer);

    Customer findCustomerById(int id);

    Customer findCustomerByUsername(String username);
}
