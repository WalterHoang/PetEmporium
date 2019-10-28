package petEmporium.entities;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Set;


/**
 * this entity creates a lookup table for ItemType that links to the Inventory table
 */
@Entity
public class ItemType {


    /**
     * an auto-generated ID for the itemType
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "", name = "Item type Id", required = false, value = "Auto-generated ID for item type record")
    private Integer itemTypeId;

    /**
     * the name of the item type
     */
    @ApiModelProperty(notes = "", name = "Item Type", required = true, example = "Food", allowableValues = "food | toy | accessory", value = "The category the item belongs under")
    private String itemType;

    public ItemType() {
    }

    ;

    /**
     * constructor WITHOUT id
     */
    public ItemType(String itemType) {
        this.itemType = itemType;
    }


    /**
     * getters and setters
     */
    public Integer getItemTypeId() {
        return itemTypeId;
    }

    public void setItemTypeId(Integer itemTypeId) {
        this.itemTypeId = itemTypeId;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }


    /**
     * override toString method to print an item type and its details
     *
     * @return
     */
    @Override
    public String toString() {
        return "ItemType{" +
                "itemTypeId=" + itemTypeId +
                ", itemType='" + itemType + '\'' +
                '}';
    }
}

