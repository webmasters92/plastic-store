package rs.dev.plasticstore.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order implements Serializable {

    public Customer getCustomer() {
        return customer;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public Guest getGuest() {
        return guest;
    }

    public int getId() {
        return id;
    }

    public Set<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public OrderPayment getOrder_payment() {
        return order_payment;
    }

    public double getShipping() {
        return shipping;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setGuest(Guest guest) {
        this.guest = guest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOrderItems(Set<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrder_payment(OrderPayment order_payment) {
        this.order_payment = order_payment;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + id + ", dateCreated=" + dateCreated + ", orderStatus=" + orderStatus + ", orderTotal=" + orderTotal + ", shipping=" + shipping + ", guest=" + guest + ", order_payment=" + order_payment + ", customer_id=" + customer_id + ", orderItems=" + orderItems + ", customer=" + customer + '}';
    }

    private static final long serialVersionUID = 2681531852204068105L;
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date_created")
    private Date dateCreated;
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    @Column(name = "order_total")
    private double orderTotal;
    @Column(name = "shipping")
    private double shipping;
    @OneToOne
    @JoinColumn(name = "guest_id")
    private Guest guest;
    @Column(name = "order_payment")
    @Enumerated(EnumType.STRING)
    private OrderPayment order_payment;
    @JoinColumn(name = "customer_id")
    private int customer_id;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems = new HashSet<>();
    @Transient
    Customer customer;
}
