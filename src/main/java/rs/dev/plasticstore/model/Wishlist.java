package rs.dev.plasticstore.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "wishlist")
public class Wishlist implements Serializable {

    private static final long serialVersionUID = 2681531852204068105L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int userId;
    private int productId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
