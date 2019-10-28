package petEmporium.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cartId;

    @ManyToOne
    @JoinColumn(name = "inventoryId", referencedColumnName = "inventoryId")//the nullability is by default true, but I want to make sure it is.
    @ApiModelProperty(notes = "the nullability is by default true, but I want to make sure it is.", name = "item", value = "Item id, not required")
    private Inventory itemOrder;

    private Integer itemQuantity;

    @ManyToOne
    @JoinColumn(name = "petId", nullable = true)
    @ApiModelProperty(notes = "", name = "Pet", value = "Pet id, not required")
    private Pet pet;

    @ManyToOne
    @ApiModelProperty(notes = "", name = "PetOrders", value = "PetOrder Id", required = true)
    private PetOrders petOrders;


    private Double lineTotal;

    public ShoppingCart() { } //for hibernate

    public ShoppingCart(Inventory itemOrder, Integer itemQuantity, PetOrders petOrders, Double lineTotal) {
        this.itemOrder = itemOrder;
        this.itemQuantity = itemQuantity;
        this.petOrders = petOrders;
        this.lineTotal = lineTotal;
    }

    public ShoppingCart(Pet pet, PetOrders petOrders, Double lineTotal) {
        this.pet = pet;
        this.petOrders = petOrders;
        this.lineTotal = lineTotal;
    }

    public ShoppingCart(Integer cartId, Inventory itemOrder, Integer itemQuantity, Pet pet, PetOrders petOrders, Double lineTotal) {
        this.cartId = cartId;
        this.itemOrder = itemOrder;
        this.itemQuantity = itemQuantity;
        this.pet = pet;
        this.petOrders = petOrders;
        this.lineTotal = lineTotal;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    //@JsonIgnore
    public Inventory getItemOrder() {
        return itemOrder;
    }

    public void setItemOrder(Inventory itemOrder) {
        this.itemOrder = itemOrder;
    }

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }


    public Double getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(Double lineTotal) {
        this.lineTotal = lineTotal;
    }

   // @JsonIgnore
    public PetOrders getPetOrders() {
        return petOrders;
    }

    public void setPetOrders(PetOrders petOrders) {
        this.petOrders = petOrders;
    }
}
