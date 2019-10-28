package petEmporium.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@Entity
public class PetUsers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "", name = "Pet User Id", required = false, value = "Auto-generated ID for user record")
    private int petUsersId;
    @ApiModelProperty(notes = "", name = "First Name", required = true, example = "John", value = "A user's first name")
    private String firstName;
    @ApiModelProperty(notes = "", name = "Last Name", required = true, example = "Smith", value = "User's Last name")
    private String lastName;
    @ApiModelProperty(notes = "", name = "Phone Number", required = true, example = "333-333-4444",value = "User's phone number")
    private String phoneNumber;

    //One to one with email;
    @OneToOne
    @JoinColumn(name = "emailId", referencedColumnName = "emailId")
    @ApiModelProperty(notes = "", name = "Email", required = true, value = "User's email address")
    private PetEmail email;


    @ManyToMany//Many to Many with Address
    @JoinTable
            (name = "Users_Address",
                    joinColumns = {@JoinColumn(
                            name = "petUserID", referencedColumnName = "petUsersId")},
                    inverseJoinColumns = {@JoinColumn(
                            name = "addressId", referencedColumnName = "addressId")})
    @Valid
    @ApiModelProperty(notes = "", name = "Address", required = true, value = "User's address")
    private Set<Address> address = new HashSet<>();

    @OneToMany(mappedBy = "petUsers")
    @ApiModelProperty(notes = "", name = "Orders", required = true, value = "User's orders")
    private Set<PetOrders> petOrders = new HashSet<>();

    public PetUsers() {
    } //For Hibernate

    public PetUsers(String firstName, String lastName, String phoneNumber, PetEmail email, @Valid Set<Address> address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
    }

    public PetUsers(int petUsersId, String firstName, String lastName, String phoneNumber, PetEmail email,
                    @Valid Set<Address> address,
                    Set<PetOrders> petOrders) {
        this.petUsersId = petUsersId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.petOrders = petOrders;
    }

    @JsonIgnore
    public Set<PetOrders> getPetOrders() {
        return petOrders;
    }

    public void setPetOrders(Set<PetOrders> petOrders) {
        this.petOrders = petOrders;
    }

    public int getPetUsersId() {
        return petUsersId;
    }

    public void setPetUsersId(int petUsersId) {
        this.petUsersId = petUsersId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PetEmail getEmail() {
        return email;
    }

    public void setEmail(PetEmail email) {
        this.email = email;
    }

    public Set<Address> getAddress() {
        return address;
    }

    public void setAddress(Set<Address> address) {
        this.address = address;
    }
}

