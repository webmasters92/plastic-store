package rs.dev.plasticstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Column(name = "username", length = 64, nullable = false)
    private String username;

    @Id
    @Column(name = "series", length = 64)
    private String series;

    @Column(name = "token", length = 64, nullable = false)
    private String token;

    @Column(name = "last_used", nullable = false)
    private Timestamp last_used;

}
