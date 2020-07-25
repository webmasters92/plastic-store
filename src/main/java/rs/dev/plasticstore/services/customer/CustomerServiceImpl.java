package rs.dev.plasticstore.services.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Customer;
import rs.dev.plasticstore.model.UserPrincipal;
import rs.dev.plasticstore.repository.customer.CustomerRepository;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    @Transactional
    @CacheEvict(value = "customer_by_id", key = "#id")
    public void deleteCustomerById(int id) {
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Cacheable(value = "all_customers")
    public ArrayList<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer findCustomerByEmail(String email) {
        var customer = customerRepository.findByEmail(email);
        return customer;
    }

    @Override
    @Transactional
    public Customer findCustomerById(int id) {
        var customer = customerRepository.findCustomerById(id);
        return customer;
    }

    @Override
    public Optional findCustomerByResetToken(String resetToken) {
        return customerRepository.findByResetToken(resetToken);
    }

    @Override
    @Transactional
    public Customer findCustomerByUsername(String username) {
        Optional customerDb = customerRepository.findByUsername(username);
        Customer customer;
        if(customerDb.isPresent()) customer = (Customer) customerDb.get();
        else customer = null;
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
    @CachePut(value = "customer_by_id", key = "#customer")
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Autowired
    CustomerRepository customerRepository;
}
