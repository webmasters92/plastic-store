package rs.dev.plasticstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
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

@Getter
@Setter
@Entity
@Table(name = "products")
public class Product implements Serializable {

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Product)) return false;
        Product product = (Product) o;
        return getCode() == product.getCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode());
    }
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
    @LazyCollection(LazyCollectionOption.FALSE)
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
    @LazyCollection(LazyCollectionOption.FALSE)
    @ElementCollection
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
    private int averageRating, minPrice, maxPrice, minDiscountedPrice, maxDiscountedPrice;
    @Column(name = "date_created", updatable = false)
    @CreationTimestamp
    private LocalDateTime date_created;
    @Column(name = "date_updated")
    @UpdateTimestamp
    private LocalDateTime date_updated;
    @Transient
    private List<String> selectedColors = new ArrayList<>();

}
