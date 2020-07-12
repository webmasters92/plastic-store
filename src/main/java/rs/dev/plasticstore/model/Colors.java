package rs.dev.plasticstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "colors")
public class Colors implements Serializable {

    private static final long serialVersionUID = -5046696605277912958L;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(32)")
    private String name;

    @Column(name = "code", nullable = false)
    private String code;
}
