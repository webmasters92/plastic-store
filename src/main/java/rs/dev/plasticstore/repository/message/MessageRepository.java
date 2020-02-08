package rs.dev.plasticstore.repository.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rs.dev.plasticstore.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {


}
