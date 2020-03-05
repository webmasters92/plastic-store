package rs.dev.plasticstore.model;

public class Mail {

    public Customer getCustomer() {
        return customer;
    }

    public String getFrom() {
        return from;
    }

    public Order getOrder() {
        return order;
    }

    public String getSubject() {
        return subject;
    }

    public String getTo() {
        return to;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(String to) {
        this.to = to;
    }

    private String from;
    private String to;
    private String subject;
    private Order order;
    private Customer customer;
}
