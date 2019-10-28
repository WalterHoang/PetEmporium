package petEmporium.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.persistence.Entity;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "", name = "Address Id", required = false, value = "Auto-generated ID for address record")
    private int addressId;
    @ApiModelProperty(notes = "", name = "Street Address 1", required = true, example = "6512 W Breaker Ave.", value = "Street portion of a valid US address")
    private String streetAddress;
    @ApiModelProperty(notes = "", name = "Street Address 2", required = false, example = "#202", value = "Extended Street Address for apartment or suite buildings")
    private String streetAddress2;
    @ApiModelProperty(notes = "", name = "City Address", required = true, example = "Carson City", value = "City portion of a valid US address")
    private String cityAddress;
    @ApiModelProperty(notes = "", name = "State Code", required = true, example = "NY", value = "State portion of a valid US address")
    private String stateCode;
    @ApiModelProperty(notes = "", name = "Zip Code", required = true, example = "554663", value = "Zip portion of a valid US address. Can be in format 55555 or 55555-4444")
    private String zipCode;

    @ManyToMany(mappedBy = "address")
    private Set<PetUsers> petUsers = new HashSet<>();

    public Address() {
    }

    public Address(String streetAddress, String streetAddress2, String cityAddress, String stateCode, String zipCode) {
        this.streetAddress = streetAddress;
        this.streetAddress2 = streetAddress2;
        this.cityAddress = cityAddress;
        this.stateCode = stateCode;
        this.zipCode = zipCode;
    }


    public Address(int addressId, //For the Many to Many relationship
                   String streetAddress,
                   String streetAddress2,
                   String cityAddress,
                   String stateCode,
                   String zipCode, Set<PetUsers> petUsers) {
        this.addressId = addressId;
        this.streetAddress = streetAddress;
        this.streetAddress2 = streetAddress2;
        this.cityAddress = cityAddress;
        this.stateCode = stateCode;
        this.zipCode = zipCode;
        this.petUsers = petUsers;
    }


    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress2() {
        return streetAddress2;
    }

    public void setStreetAddress2(String streetAddress2) {
        this.streetAddress2 = streetAddress2;
    }

    public String getCityAddress() {
        return cityAddress;
    }

    public void setCityAddress(String cityAddress) {
        this.cityAddress = cityAddress;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}

