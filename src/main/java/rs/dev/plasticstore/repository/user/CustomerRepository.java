package rs.dev.plasticstore.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.Customer;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Optional<Customer> findByUsername(String username);
}
