package rs.dev.plasticstore.services.message;

import rs.dev.plasticstore.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> findAll();

    void saveMessage(Message message);

}
