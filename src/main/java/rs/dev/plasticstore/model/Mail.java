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

    public String getHome_link() {
        return home_link;
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

    public String getReset_link() {
        return reset_link;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setHome_link(String home_link) {
        this.home_link = home_link;
    }

    public void setReset_link(String reset_link) {
        this.reset_link = reset_link;
    }

    private String from;
    private String to;
    private String subject;
    private String reset_link;
    private String home_link;
    private Order order;
    private Customer customer;
}
