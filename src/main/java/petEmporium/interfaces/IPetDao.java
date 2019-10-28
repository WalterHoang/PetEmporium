package petEmporium.interfaces;

import org.springframework.stereotype.Repository;
import petEmporium.entities.Pet;

import java.util.List;

public interface IPetDao {
    Pet addPet(Pet pet);

    List<Pet> getAllPets();

    List<Pet> getAllPetsbyname(String petName);

    List<Pet> getAllPetsByType(String petType);

    Pet getPetById(int petId);

    Pet updatePet(int petId, Pet pet);

    void deletePet(int petId);
}
