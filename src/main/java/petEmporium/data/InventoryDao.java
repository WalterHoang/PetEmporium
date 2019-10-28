package petEmporium.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petEmporium.entities.Inventory;
import petEmporium.entities.ItemType;
import petEmporium.interfaces.IInventoryDao;
import petEmporium.interfaces.IInventoryRepo;
import petEmporium.interfaces.IItemTypeRepo;

import java.util.ArrayList;
import java.util.List;


/**
 * this is the DAO layer for the inventory class, where custom methods are defined using the JPA repo methods
 */
@Component
public class InventoryDao implements IInventoryDao {

    /**
     * instantiate Inventory and ItemType repo so we can use their methods
     */
    @Autowired
    private IInventoryRepo inventoryRepo;
    @Autowired
    private IItemTypeRepo iItemTypeRepo;

    /**
     * adds a new inventory item to the database
     *
     * @param inventory the inventory item to add
     * @return the new inventory item
     */
    @Override
    public Inventory addInventoryItem(Inventory inventory) {

        // the inventory's item type
        ItemType newItemType = inventory.getItemType();

        // find if the item type is already in the system
        ItemType existingItemType = iItemTypeRepo.findByItemType(newItemType.getItemType());

        // if the type is not yet in the database, add it to get an itemTypeId
        // otherwise, update the newItemType to the one that already exists (i.e. give it the proper id)
        if (existingItemType == null) {
            newItemType = iItemTypeRepo.save(newItemType);
        } else {
            newItemType = existingItemType;
        }

        // put the new itemType (with id) back into the inventory item ad save it to the database
        inventory.setItemType(newItemType);
        return inventoryRepo.save(inventory);

    }

    ;


    /**
     * returns a list of all inventory items
     *
     * @return
     */
    @Override
    public List<Inventory> getAllInventoryItems() {
        return inventoryRepo.findAll();
    }

    ;

    /**
     * returns the inventory item with the corresponding id
     *
     * @param inventoryItemId
     * @return
     */
    @Override
    public Inventory getInventoryItemById(int inventoryItemId) {
        return inventoryRepo.findById(inventoryItemId).orElse(null);
    }

    ;


    /**
     * returns the FIRST inventory item with the given name
     *
     * @param name
     * @return
     */
    @Override
    public Inventory getInventoryItemByName(String name) {
        return inventoryRepo.findFirstByName(name);
    }

    ;


    /**
     * returns a list of all inventory items of the given type (NOT typeId)
     *
     * @param type
     * @return
     */
    @Override
    public List<Inventory> getInventoryItemsByType(String type) {
        return inventoryRepo.findAllByItemTypeItemType(type);
    }

    ;


    /**
     * returns the inventory item with the given sku
     *
     * @param sku
     * @return
     */
    @Override
    public Inventory getInventoryItemBySku(String sku) {
        return inventoryRepo.findBySku(sku);
    }

    ;


    /**
     * returns an updated inventory item, given the id of the item you want to update and the information you wish to be associated with that id
     *
     * @param inventoryItemId
     * @param inventory
     * @return
     */
    @Override
    public Inventory updateInventoryItem(int inventoryItemId, Inventory inventory) {
        //check to see if this item id already exists in the database
        Inventory existingItem = inventoryRepo.findById(inventoryItemId).orElse(null);

        // if the item id exists, set the id of the new inventory item and save it to the database
        // if the item id doesn't exist, return null
        if (existingItem != null) {
            inventory.setInventoryId(inventoryItemId);
            return addInventoryItem(inventory);
        } else {
            return null;
        }
    }

    ;


    /**
     * deletes the inventory item with the given id from the database
     *
     * @param inventoryItemId
     */
    @Override
    public void deleteInventoryItem(int inventoryItemId) {
        inventoryRepo.deleteById(inventoryItemId);
    }

    ;


    /**
     * returns a list of all the sku number currently in the database
     *
     * @return
     */
    @Override
    public List<String> getAllSkus() {
        //get all inventory items in the database
        List<Inventory> items = inventoryRepo.findAll();
        List<String> skus = new ArrayList<>();

        //for each item in the database, get its sku and add it to the list of skus
        for (Inventory item : items) {
            String sku = item.getSku();
            skus.add(sku);
        }

        return skus;

    }
}
