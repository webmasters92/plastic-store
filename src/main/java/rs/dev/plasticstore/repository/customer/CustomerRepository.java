package rs.dev.plasticstore.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.Customer;

import java.util.ArrayList;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    ArrayList<Customer> findAll();

    Customer findByEmail(String email);

    Optional<Customer> findByResetToken(String resetToken);

    Optional<Customer> findByUsername(String username);

    Customer findCustomerById(int id);

}
