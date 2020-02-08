package rs.dev.plasticstore.services.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Message;
import rs.dev.plasticstore.repository.message.MessageRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    @Transactional
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Autowired
    MessageRepository messageRepository;
    @PersistenceContext
    private EntityManager entityManager;

}
