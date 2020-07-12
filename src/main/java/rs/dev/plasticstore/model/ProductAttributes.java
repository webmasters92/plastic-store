package rs.dev.plasticstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
@Embeddable
public class ProductAttributes implements Serializable {

    private static final long serialVersionUID = 2681531852204068105L;

    @NotNull
    private int price;

    @NotNull
    private String size;

    private int discounted_price;
}
