package rs.dev.plasticstore.services.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rs.dev.plasticstore.model.Message;
import rs.dev.plasticstore.repository.message.MessageRepository;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {

    @Override
    @Transactional
    @CacheEvict(value = "all_messages", key = "#id")
    public void deleteMessageBy_Id(int id) {
        messageRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Caching(cacheable = @Cacheable(value = "all_messages", key = "'ALL'"), put = @CachePut(value = "all_messages", key = "'ALL'"))
    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    @Override
    @Transactional
    @Cacheable(value = "all_messages", key = "#id")
    public Message findMessageById(int id) {
        return messageRepository.findById(id).get();
    }

    @Override
    @Transactional
    @CachePut(value = "all_messages", key = "#message")
    public void saveMessage(Message message) {
        messageRepository.save(message);
    }

    @Autowired
    MessageRepository messageRepository;
}
