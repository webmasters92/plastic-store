package rs.dev.plasticstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.web.multipart.MultipartFile;
import rs.dev.plasticstore.utils.StringListConverter;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
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

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "product_attributes", joinColumns = @JoinColumn(name = "product_id"))
    @AttributeOverrides({@AttributeOverride(name = "size", column = @Column(name = "product_size")), @AttributeOverride(name = "price", column = @Column(name = "product_price")), @AttributeOverride(name = "discounted_price", column = @Column(name = "discounted_price"))})
    private List<ProductAttributes> productAttributes = new ArrayList<>();

    @Transient
    @Convert(converter = StringListConverter.class)
    private List<String> sizes = new ArrayList<>();

    @Transient
    @Convert(converter = StringListConverter.class)
    private List<Integer> prices = new ArrayList<>();

    @Transient
    @Convert(converter = StringListConverter.class)
    private List<Integer> discounted_prices = new ArrayList<>();

    @Transient
    private List<MultipartFile> imgData = new ArrayList<>();

    @Transient
    private int averageRating;

    @Transient
    private int minPrice;

    @Transient
    private int maxPrice;

    @Transient
    private int minDiscountedPrice;

    @Transient
    private int maxDiscountedPrice;

    @Column(name = "date_created", updatable = false)
    @CreationTimestamp
    private LocalDateTime date_created;

    @Column(name = "date_updated")
    @UpdateTimestamp
    private LocalDateTime date_updated;

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

    public int getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(int averageRating) {
        this.averageRating = averageRating;
    }

    public LocalDateTime getDate_created() {
        return date_created;
    }

    public void setDate_created(LocalDateTime date_created) {
        this.date_created = date_created;
    }

    public LocalDateTime getDate_updated() {
        return date_updated;
    }

    public void setDate_updated(LocalDateTime date_updated) {
        this.date_updated = date_updated;
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

    public int getMinDiscountedPrice() {
        return minDiscountedPrice;
    }

    public void setMinDiscountedPrice(int minDiscountedPrice) {
        this.minDiscountedPrice = minDiscountedPrice;
    }

    public int getMaxDiscountedPrice() {
        return maxDiscountedPrice;
    }

    public void setMaxDiscountedPrice(int maxDiscountedPrice) {
        this.maxDiscountedPrice = maxDiscountedPrice;
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

    public List<Integer> getDiscounted_prices() {
        return discounted_prices;
    }

    public void setDiscounted_prices(List<Integer> discounted_prices) {
        this.discounted_prices = discounted_prices;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getCode() == product.getCode();
    }
}
