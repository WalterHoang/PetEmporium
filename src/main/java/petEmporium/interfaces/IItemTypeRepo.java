package petEmporium.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petEmporium.entities.ItemType;

import java.util.List;

@Repository
public interface IItemTypeRepo extends JpaRepository<ItemType, Integer> {

    ItemType findByItemType(String itemType);
}
