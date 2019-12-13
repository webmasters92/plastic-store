package rs.dev.plasticstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;
import rs.dev.plasticstore.Utils.StringListConverter;

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

    @Column(name = "code")
    private int code;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "manufacturer")
    private String manufacturer;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductColor> productColors = new ArrayList<>();

    @Column(name = "sale")
    private boolean sale;

    @Column(name = "status")
    private boolean status;

    @Column(name = "available")
    private boolean available;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "sub_category_id")
    private Subcategory subcategory;

    @ElementCollection
    @CollectionTable(name = "productAttributes", joinColumns = @JoinColumn(name = "product_id"))
    @AttributeOverrides({@AttributeOverride(name = "size", column = @Column(name = "product_size")), @AttributeOverride(name = "price", column = @Column(name = "product_price"))})
    private List<ProductAttributes> productAttributes = new ArrayList<>();

    @Transient
    @Convert(converter = StringListConverter.class)
    private List<String> sizes = new ArrayList<>();

    @Transient
    @Convert(converter = StringListConverter.class)
    private List<Integer> prices = new ArrayList<>();

    @Transient
    private List<MultipartFile> imgData = new ArrayList<>();

    @Transient
    private int minPrice;

    @Transient
    private int maxPrice;

    @Transient
    private List<String> selectedColors = new ArrayList<>();

    @Transient
    public int getMinPrice() {
        return minPrice;
    }

    @Transient
    public void setMinPrice(int minPrice) {
        this.minPrice = minPrice;
    }

    public int getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(int maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<String> getSelectedColors() {
        return selectedColors;
    }

    public void setSelectedColors(List<String> selectedColors) {
        this.selectedColors = selectedColors;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Integer> getPrices() {
        return prices;
    }

    public void setPrices(List<Integer> prices) {
        this.prices = prices;
    }

    public List<ProductAttributes> getProductAttributes() {
        return productAttributes;
    }

    public void setProductAttributes(List<ProductAttributes> productAttributes) {
        this.productAttributes = productAttributes;
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

    public Subcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(Subcategory subcategory) {
        this.subcategory = subcategory;
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

    public List<ProductColor> getProductColors() {
        return productColors;
    }

    public void setProductColors(List<ProductColor> productColors) {
        this.productColors = productColors;
    }
}
