package petEmporium.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petEmporium.entities.ShoppingCart;

@Repository
public interface ICartRepo extends JpaRepository<ShoppingCart, Integer> {
}
