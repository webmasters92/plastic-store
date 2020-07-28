package rs.dev.plasticstore.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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

@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
public class Order implements Serializable {

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
    @Column(name = "message",length = 100)
    private String message;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<OrderItem> orderItems = new HashSet<>();
    @Transient
    Customer customer;
}
