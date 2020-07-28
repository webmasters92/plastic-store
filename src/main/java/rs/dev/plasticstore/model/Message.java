package rs.dev.plasticstore.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message implements Serializable {

    private static final long serialVersionUID = 2681531852204068105L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name, email, subject;
    @Column(name = "message", length = 500)
    private String text;
    private boolean answered = false;
    @Column(name = "date_created", updatable = false)
    @CreationTimestamp
    private LocalDateTime date_created;
}
