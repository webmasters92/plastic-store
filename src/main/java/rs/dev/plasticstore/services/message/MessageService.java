package rs.dev.plasticstore.services.message;

import rs.dev.plasticstore.model.Message;

import java.util.List;

public interface MessageService {

    void deleteMessageBy_Id(int id);

    List<Message> findAll();

    Message findMessageById(int parseInt);

    void saveMessage(Message message);

}
