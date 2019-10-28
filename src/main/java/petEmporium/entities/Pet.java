package petEmporium.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "", name = "Pet Id", required = false, value = "Auto-generated ID for pet record")
    private Integer petId;
    @ApiModelProperty(notes = "", name = "Pet Name", required = true, example = "Spot", value = "The name of the pet")
    private String petName;
    // Current pet types are dogs, cats, and birds
    // cannot abstract pet type or else an error is thrown with spring
//    @ManyToOne
//    @JoinColumn(name = "pet_type_id")
    @ApiModelProperty(notes = "", name = "Pet Type", required = true, example = "Dog", value = "The type of animal the pet is")
    private String petType;
    // Male or female pets
    @ApiModelProperty(notes = "", name = "Pet Sex", required = true, example = "F", value = "The biological sex the pet is")
    private String petSex;
    @ApiModelProperty(notes = "", name = "Pet Adoption Fee", required = true, example = "540.50", value = "The amount of dollars needed to adopt the pet")
    private double petAdoptionFee;
    // These Fields are optional to fill in
    @ApiModelProperty(notes = "", name = "Pet Color", required = false, example = "Brown", value = "The color of the animal's body (fur, feathers, etc.)")
    private String petColor;

    @ApiModelProperty(notes = "", name = "Pet Type", required = false, example = "2 years and 6 months", value = "How old the Pet is (allows for years and months")
    private String petAge;

    @ApiModelProperty(name = "beenAdopted", example = "true", value = "If the pet has been adopted or not. Is set on time of order")
    private boolean beenAdopted;

    @ApiModelProperty(name="CartId", example = "1", value = "This is the connection point between the Cart class and the Pet class.")
    @OneToMany(mappedBy = "pet")
    private Set<ShoppingCart> cart;

    // Constructors
    public Pet() {
    }

    public Pet(String petName, String petType, String petSex, double petAdoptionFee, String petColor, String petAge,
               boolean beenAdopted) {
        this.petName = petName;
        this.petType = petType;
        this.petSex = petSex;
        this.petAdoptionFee = petAdoptionFee;
        this.petColor = petColor;
        this.petAge = petAge;
        this.beenAdopted = beenAdopted;
    }

    public Pet(Integer petId, String petName,
               String petType, String petSex,
               double petAdoptionFee, String petColor,
               String petAge, Set<ShoppingCart> cart, boolean beenAdopted) {
        this.petId = petId;
        this.petName = petName;
        this.petType = petType;
        this.petSex = petSex;
        this.petAdoptionFee = petAdoptionFee;
        this.petColor = petColor;
        this.petAge = petAge;
        this.beenAdopted = beenAdopted;
        this.cart = cart;
    }

    // Getters and setters

    public Integer getPetId() {
        return petId;
    }

    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

//    public PetType getPetType() {
//        return petType;
//    }
//
//    public void setPetType(PetType petType) {
//        this.petType = petType;
//    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetSex() {
        return petSex;
    }

    public void setPetSex(String petSex) {
        this.petSex = petSex;
    }

    public double getPetAdoptionFee() {
        return petAdoptionFee;
    }

    public void setPetAdoptionFee(double petAdoptionFee) {
        this.petAdoptionFee = petAdoptionFee;
    }

    public String getPetColor() {
        return petColor;
    }

    public void setPetColor(String petColor) {
        this.petColor = petColor;
    }

    public String getPetAge() {
        return petAge;
    }

    public void setPetAge(String petAge) {
        this.petAge = petAge;
    }

    public boolean isBeenAdopted() {
        return beenAdopted;
    }

    public void setBeenAdopted(boolean beenAdopted) {
        this.beenAdopted = beenAdopted;
    }

    @JsonIgnore
    public Set<ShoppingCart> getCart() {
        return cart;
    }

    public void setCart(Set<ShoppingCart> cart) {
        this.cart = cart;
    }
// Other methods go below here
}
