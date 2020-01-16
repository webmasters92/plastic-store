package rs.dev.plasticstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity(name = "cart_items")
public class CartItem implements Serializable {

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

    private String size;

    private String color;

    private int price;

    private int quantity;

    @Transient
    private int product_id;

    private int totalPrice;

    public int getCartItemId() {
        return id;
    }

    public void setCartItemId(int cartItemId) {
        this.id = cartItemId;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Colors getProduct_color() {
        return product_color;
    }

    public void setProduct_color(Colors product_color) {
        this.product_color = product_color;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSize(), getColor(), getPrice());
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof CartItem)) return false;
        CartItem cartItem = (CartItem) o;
        return getPrice() == cartItem.getPrice() && getSize().equals(cartItem.getSize()) && getColor().equals(cartItem.getColor());
    }

    @Override
    public String toString() {
        return "CartItem{" + "id=" + id + ", cart=" + cart + ", product=" + product + ", size='" + size + '\'' + ", color='" + color + '\'' + ", product_color=" + product_color + ", price=" + price + ", quantity=" + quantity + ", totalPrice=" + totalPrice + '}';
    }
}
