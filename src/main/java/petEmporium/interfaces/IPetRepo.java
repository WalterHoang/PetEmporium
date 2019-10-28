package petEmporium.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petEmporium.entities.Pet;

import java.util.List;

@Repository
public interface IPetRepo extends JpaRepository<Pet, Integer> {

    List<Pet> findAllByPetName(String petName);

    List<Pet> findAllByPetType(String petType);
}
