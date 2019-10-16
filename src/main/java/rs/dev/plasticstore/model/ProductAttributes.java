package rs.dev.plasticstore.model;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class ProductAttributes {

    @NotNull
    private int price;

    @NotNull
    private String size;

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
}
