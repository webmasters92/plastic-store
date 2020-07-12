package rs.dev.plasticstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "product_color")
public class ProductColor implements Serializable {

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ProductColor)) return false;
        ProductColor that = (ProductColor) o;
        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code);
    }

    private static final long serialVersionUID = -5046696605277912958L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name", nullable = false, columnDefinition = "varchar(32)")
    private String name;
    @Column(name = "code", nullable = false)
    private String code;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
