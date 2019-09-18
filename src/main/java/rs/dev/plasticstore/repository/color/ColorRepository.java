package rs.dev.plasticstore.repository.color;

import org.springframework.data.jpa.repository.JpaRepository;
import rs.dev.plasticstore.model.Colors;

import java.util.Optional;

public interface ColorRepository extends JpaRepository<Colors, Integer> {

    Optional<Colors> findColorsByName(String name);
}
