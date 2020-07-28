package rs.dev.plasticstore.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Mail {

    private String from, to, subject, reset_link, home_link,message;
    private Order order;
    private Customer customer;
}
