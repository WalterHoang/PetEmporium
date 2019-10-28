package petEmporium.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petEmporium.entities.Inventory;

import java.util.List;

@Repository
public interface IInventoryRepo extends JpaRepository<Inventory, Integer> {

    Inventory findFirstByName(String name);

    List<Inventory> findAllByItemTypeItemType(String itemType);

    Inventory findBySku(String sku);
}
