package rs.dev.plasticstore.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

    @Column(name = "name", length = 24, unique = true, nullable = false)
    private String name;

    @Column(name = "url", length = 64, unique = true, nullable = false)
    private String url;

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public Product getProduct() {

        return product;
    }

    public void setProduct(Product product) {

        this.product = product;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getUrl() {

        return url;
    }

    public void setUrl(String url) {

        this.url = url;
    }


}