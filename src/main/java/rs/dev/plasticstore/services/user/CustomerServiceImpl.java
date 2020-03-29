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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Customer> customer = customerRepository.findByUsername(username);
        customer.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return customer.map(UserPrincipal::new).get();
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
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Autowired
    CustomerRepository customerRepository;
}
