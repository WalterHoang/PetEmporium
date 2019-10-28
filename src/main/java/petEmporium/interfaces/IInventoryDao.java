package petEmporium.interfaces;

import petEmporium.entities.Inventory;

import java.util.List;

public interface IInventoryDao {

    Inventory addInventoryItem(Inventory inventory);

    List<Inventory> getAllInventoryItems();

    Inventory getInventoryItemById(int inventoryItemId);

    Inventory getInventoryItemByName(String name);

    List<Inventory> getInventoryItemsByType(String type);

    Inventory getInventoryItemBySku(String sku);

    Inventory updateInventoryItem(int inventoryItemId, Inventory inventory);

    void deleteInventoryItem(int inventoryItemId);

    List<String> getAllSkus();

}
