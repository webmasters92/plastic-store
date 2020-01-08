package rs.dev.plasticstore.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import rs.dev.plasticstore.model.Customer;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.repository.user.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        customer.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return customer.map(UserPrincipal::new).get();
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
