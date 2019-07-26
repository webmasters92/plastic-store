package rs.dev.plasticstore.model;

import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product implements Serializable {

    private static final long serialVersionUID = 2681531852204068105L;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private double price;

    @OneToMany(mappedBy = "product")
    private List<Image> images = new ArrayList<>();

    @Column(name = "sale")
    private boolean sale;

    @Column(name = "status")
    private boolean status;

    @Column(name = "available")
    private boolean available;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Transient
    private List<MultipartFile> imgData = new ArrayList<>();

    public int getId() {

        return id;
    }

    public void setId(int id) {

        this.id = id;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public String getDescription() {

        return description;
    }

    public void setDescription(String description) {

        this.description = description;
    }

    public double getPrice() {

        return price;
    }

    public void setPrice(double price) {

        this.price = price;
    }

    public boolean isSale() {

        return sale;
    }

    public void setSale(boolean sale) {

        this.sale = sale;
    }

    public boolean isStatus() {

        return status;
    }

    public void setStatus(boolean status) {

        this.status = status;
    }

    public boolean isAvailable() {

        return available;
    }

    public void setAvailable(boolean available) {

        this.available = available;
    }

    public Category getCategory() {

        return category;
    }

    public void setCategory(Category category) {

        this.category = category;
    }

    public List<Image> getImages() {

        return images;
    }

    public void setImages(List<Image> images) {

        this.images = images;
    }

    public List<MultipartFile> getImgData() {

        return imgData;
    }

    public void setImgData(List<MultipartFile> imgData) {

        this.imgData = imgData;
    }
}
