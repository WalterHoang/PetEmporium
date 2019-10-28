package petEmporium.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Set;

/**
 * This entity represents an item in the store's inventory
 * It consists of a unique id, the name of the item, the type of item, a unique sku, the amount of the item available in stock, and the price of the item
 */
@Entity
public class Inventory {

    /**
     * an auto-generated ID for the item
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "", name = "Inventory Id", required = false, value = "Auto-generated ID for inventory record")
    private Integer inventoryId;


    /**
     * the components of an item include name, itemType, sky, numberAvailable, and price
     * all are required
     * name is between 3-100 characters
     * itemType is a FK to itemType lookup table
     * sku is a string of exactly 8 alphanumeric characters which is unique within the database
     * numberAvailable cannot drop below 0
     * price is in USD to 2 decimal places
     */
    @ApiModelProperty(notes = "", name = "Item Name", required = true, example = "Chew Toy", value = "Name of the product")
    private String name;

    @ManyToOne
    @JoinColumn(name = "item_type_id")
    @ApiModelProperty(notes = "", name = "Item Type", required = true, value = "The category the item belongs under")
    private ItemType itemType;
    @ApiModelProperty(notes = "", name = "SKU", required = true, example = "12345678", value = "Unique identifier for the item. Contains various information that a id cannot store on its own")
    private String sku;
    @ApiModelProperty(notes = "", name = "Number Available", required = true, example = "15", allowableValues = "0..2,147,483,647", value = "Remaining amount of an item")
    private Integer numberAvailable;
    @ApiModelProperty(notes = "", name = "Price", required = true, example = "15.45", allowableValues = "0.01..2,147,483,647", value = "The price of an item")
    private Double price;

    @ApiModelProperty(name="CartId", example = "1", value = "This is the connection point between the Cart class and the Inventory class.")
    @OneToMany(mappedBy = "itemOrder")
    private Set<ShoppingCart> cart;

    public Inventory() {
    };

    /**
     * a constructor WITHOUT id
     */
    public Inventory(
            String name,
            ItemType itemType,
            String sku,
            Integer numberAvailable,
            Double price) {
        this.name = name;
        this.itemType = itemType;
        this.sku = sku;
        this.numberAvailable = numberAvailable;
        this.price = price;
    }

    public Inventory(Integer inventoryId,
                     String name,
                     ItemType itemType,
                     String sku, Integer numberAvailable, Double price, Set<ShoppingCart> cart) {
        this.inventoryId = inventoryId;
        this.name = name;
        this.itemType = itemType;
        this.sku = sku;
        this.numberAvailable = numberAvailable;
        this.price = price;
        this.cart = cart;
    }

    /**
     * getters and setters
     */
    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemType getItemType() {
        return itemType;
    }

    public void setItemType(ItemType itemType) {
        this.itemType = itemType;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public Integer getNumberAvailable() {
        return numberAvailable;
    }

    public void setNumberAvailable(Integer numberAvailable) {
        this.numberAvailable = numberAvailable;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    @JsonIgnore
    public Set<ShoppingCart> getCart() {
        return cart;
    }

    public void setCart(Set<ShoppingCart> cart) {
        this.cart = cart;
    }

    /**
     * override toString method to print an inventory item and its details
     *
     * @return
     */
    @Override
    public String toString() {
        return "Inventory{" +
                "inventoryId=" + inventoryId +
                ", name='" + name + '\'' +
                ", itemType=" + itemType +
                ", sku='" + sku + '\'' +
                ", numberAvailable=" + numberAvailable +
                ", price=" + price +
                '}';
    }

}
