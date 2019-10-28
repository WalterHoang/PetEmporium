package petEmporium.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petEmporium.entities.Address;

@Repository
public interface IAddressRepo extends JpaRepository<Address, Integer> {
}
