package petEmporium.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petEmporium.data.InventoryDao;
import petEmporium.data.OrdersShoppingCartDAO;
import petEmporium.entities.ShoppingCart;
import petEmporium.validators.InventoryValidator;
import petEmporium.customexceptions.*;
import petEmporium.entities.Inventory;
import petEmporium.interfaces.IInventoryService;

import java.util.ArrayList;
import java.util.List;

/**
 * this is the service layer for the inventory class, where validation is added
 */
@Service
public class InventoryService implements IInventoryService {

    /**
     * instantiate an inventory DAO so we can use the custom method defined there
     */
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private OrdersShoppingCartDAO orderDao;


    private static Logger logger = LogManager.getLogger();

    private InventoryValidator inventoryValidator = new InventoryValidator();


    /**
     * adds a new inventory item to the database
     *
     * @param inventory the inventory item to add
     * @return the new inventory item
     */
    @Override
    public Inventory addInventoryItem(Inventory inventory) {
        List<String> skus = inventoryDao.getAllSkus();
        try {
            if (!inventoryValidator.checkValidName(inventory.getName())) {
                throw new InvalidInventoryNameException();
            }
            if (!inventoryValidator.checkValidType(inventory.getItemType())) {
                throw new InvalidInventoryItemTypeException();
            }
            if (!inventoryValidator.checkValidSku(inventory.getSku())) {
                throw new InvalidInventorySkuException();
            }
            if (!inventoryValidator.checkValidNumberAvailable(inventory.getNumberAvailable())) {
                throw new InvalidInventoryAmountException();
            }
            if (!inventoryValidator.checkValidPrice(inventory.getPrice())) {
                throw new InvalidInventoryPriceException();
            }
            if (skus.contains(inventory.getSku())) {
                throw new InventoryItemSkuNotUnique();
            }

        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }

        return inventoryDao.addInventoryItem(inventory);
    }


    /**
     * returns a list of all inventory items
     *
     * @returnlist of all inventory items
     */
    @Override
    public List<Inventory> getAllInventoryItems() {
        return inventoryDao.getAllInventoryItems();
    }


    /**
     * returns the inventory item with the corresponding id
     *
     * @param inventoryItemId the id of the item
     * @return the inventory item with the corresponding id
     */
    @Override
    public Inventory getInventoryItemById(int inventoryItemId) {
        try {
            Inventory item = inventoryDao.getInventoryItemById(inventoryItemId);
            if (item == null) {
                throw new InventoryItemNotFound();
            }
            return item;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }


    /**
     * returns the FIRST inventory item with the given name
     *
     * @param name the name of the item
     * @return the FIRST inventory item with the given name
     */
    @Override
    public Inventory getInventoryItemByName(String name) {
        try {
            Inventory item = inventoryDao.getInventoryItemByName(name);
            if (item == null) {
                throw new InventoryItemNotFound();
            }
            return item;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }


    /**
     * returns a list of all inventory items of the given type (NOT typeId)
     *
     * @param type the type of the item (as a string)
     * @return all inventory items of the given type (NOT typeId)
     */
    @Override
    public List<Inventory> getInventoryItemsByType(String type) {
        try {
            List<Inventory> items = inventoryDao.getInventoryItemsByType(type);
            if (items.isEmpty()) {
                throw new InventoryItemNotFound();
            }
            return items;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }


    /**
     * returns the inventory item with the given sku
     *
     * @param sku the sku of the item
     * @return the inventory item with the given sku
     */
    @Override
    public Inventory getInventoryItemBySku(String sku) {
        try {
            Inventory item = inventoryDao.getInventoryItemBySku(sku);
            if (item == null) {
                throw new InventoryItemNotFound();
            }
            return item;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    /**
     * returns an updated inventory item, given the id of the item you want to update and the information you wish to be associated with that id
     *
     * @param inventoryItemId the id of the item we want to update
     * @param inventory       the new information we wish to populate the item with
     * @return the updated item
     */
    @Override
    public Inventory updateInventoryItem(int inventoryItemId, Inventory inventory) {
        try {
            Inventory oldItem = inventoryDao.getInventoryItemById(inventoryItemId);
            if (oldItem == null) {
                throw new InventoryItemNotFound();
            }
            String oldSku = oldItem.getSku();
            Inventory newItem = inventoryDao.updateInventoryItem(inventoryItemId, inventory);
            String newSku = newItem.getSku();
            if (!oldSku.equals(newSku)) {
                throw new InventoryItemSkuCannotBeUpdated();
            }
            return newItem;
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }


    /**
     * deletes the inventory item with the given id from the database
     *
     * @param inventoryItemId the id of the item we want to delete
     */
    @Override
    public void deleteInventoryItem(int inventoryItemId) {
        Inventory item = inventoryDao.getInventoryItemById(inventoryItemId);

        try {
            //gets list of all suborders
            List<ShoppingCart> carts = orderDao.getAllShoppingCarts();
            //for each suborder, add the item ordered in that suborder to the list of items ordered
            List<Inventory> itemsOrdered = new ArrayList<>();
            for (ShoppingCart cart : carts) {
                Inventory newItem = cart.getItemOrder();
                itemsOrdered.add(newItem);
            }
            if (item == null) {
                throw new InventoryItemNotFound();
            }
            if (itemsOrdered.contains(item)) {
                throw new InventoryItemCannotBeDeleted();
            }
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
        try {
            inventoryDao.deleteInventoryItem(inventoryItemId);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }
}


