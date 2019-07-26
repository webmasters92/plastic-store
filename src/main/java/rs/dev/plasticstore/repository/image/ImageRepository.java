package rs.dev.plasticstore.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>, CrudRepository<Image, Integer> {

}
