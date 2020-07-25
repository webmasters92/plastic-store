package rs.dev.plasticstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity(name = "cart_items")
public class CartItem implements Serializable {

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return getProduct().getCode() == cartItem.getProduct().getCode() && getPrice() == cartItem.getPrice() && getSize().equals(cartItem.getSize()) && getColor().equals(cartItem.getColor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), getColor(), getPrice());
    }
    private static final long serialVersionUID = 5113284808160766997L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @OneToOne
    @JsonIgnore
    @JoinColumn(name = "color_id")
    private Colors product_color;
    private String size, color;
    private int price, quantity, totalPrice;
    @Transient
    private int product_id;
}
