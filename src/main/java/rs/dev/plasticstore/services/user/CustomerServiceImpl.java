package rs.dev.plasticstore.services.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Customer;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.repository.user.CustomerRepository;

import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    @Transactional
    public Customer findCustomerById(int id) {
        Customer customer = customerRepository.findCustomerById(id);
        return customer;
    }

    @Override
    @Transactional
    public Customer findCustomerByUsername(String username) {
        Customer customer = customerRepository.findCustomerByUsername(username);
        return customer;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        customer.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return customer.map(UserPrincipal::new).get();
    }

    @Override
    @Transactional
    public void save(Customer customer) {
        customerRepository.save(customer);
    }
}
