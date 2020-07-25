package rs.dev.plasticstore.services.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Message;
import rs.dev.plasticstore.repository.message.MessageRepository;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    @Transactional
    @CachePut(value = "msg_by_id", key = "#message")
    public void deleteMessage(Message message) {
        messageRepository.delete(message);
    }

    @Override
    @Transactional
    @Cacheable(value = "all_messages")
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    @CachePut(value = "msg_by_id", key = "#message")
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }
    @Autowired
    MessageRepository messageRepository;
}
