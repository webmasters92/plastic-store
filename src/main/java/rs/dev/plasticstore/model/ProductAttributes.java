package rs.dev.plasticstore.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Embeddable
public class ProductAttributes implements Serializable {

    private static final long serialVersionUID = 2681531852204068105L;

    @NotNull
    private int price;

    @NotNull
    private String size;

    private int discounted_price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDiscounted_price() {
        return discounted_price;
    }

    public void setDiscounted_price(int discounted_price) {
        this.discounted_price = discounted_price;
    }
}
