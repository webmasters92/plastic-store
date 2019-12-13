package rs.dev.plasticstore.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "product_color")
public class ProductColor implements Serializable {

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, code);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ProductColor)) return false;
        ProductColor that = (ProductColor) o;
        return getName().equals(that.getName());
    }

    @Override
    public String toString() {
        return "ProductColor{" + "id=" + id + ", name='" + name + '\'' + ", code='" + code + '\'' + ", product=" + product + '}';
    }
}
