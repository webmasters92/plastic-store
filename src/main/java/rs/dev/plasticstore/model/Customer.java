package rs.dev.plasticstore.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "customers")
public class Customer {

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public int getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public String getNew_password() {
        return new_password;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getResetToken() {
        return resetToken;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getUsername() {
        return username;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String password;
    @Column(name = "reset_token")
    private String resetToken;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "zip_code")
    private int zipCode;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "country")
    private String country;
    @Column(name = "city")
    private String city;
    @Column(name = "date_created")
    private Date dateCreated;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    @Transient
    private String new_password;
}
