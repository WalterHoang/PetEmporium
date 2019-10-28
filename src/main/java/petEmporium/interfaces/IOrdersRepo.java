package petEmporium.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petEmporium.entities.PetOrders;

import java.util.List;

@Repository
public interface IOrdersRepo extends JpaRepository<PetOrders, Integer> {

}
