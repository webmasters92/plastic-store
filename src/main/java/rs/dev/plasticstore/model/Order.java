package rs.dev.plasticstore.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 2681531852204068105L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "createdDate")
    private Date createdDate;

    @Column(name = "delivered")
    private boolean delivered;

    @Column(name = "orderTotal")
    private double orderTotal;

    @OneToOne
    @JoinColumn(name = "customerId")
    private Customer customer;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    private List<OrderItem> items;

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Date getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {

        this.createdDate = createdDate;
    }

    public boolean isDelivered() {

        return delivered;
    }

    public void setDelivered(boolean delivered) {

        this.delivered = delivered;
    }

    public double getOrderTotal() {

        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {

        this.orderTotal = orderTotal;
    }

    public Customer getCustomer() {

        return customer;
    }

    public void setCustomer(Customer customer) {

        this.customer = customer;
    }

    public List<OrderItem> getItems() {

        return items;
    }

    public void setItems(List<OrderItem> items) {

        this.items = items;
    }
}
