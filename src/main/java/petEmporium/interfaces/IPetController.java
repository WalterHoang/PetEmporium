package petEmporium.interfaces;

import org.springframework.http.ResponseEntity;
import petEmporium.entities.Pet;

import java.util.List;

public interface IPetController {
    ResponseEntity<Pet> addPet(Pet pet);

    ResponseEntity<List<Pet>> getallPets();

    ResponseEntity<List<Pet>> getAllPetsByName(String petName);

    ResponseEntity<List<Pet>> getAllPetsByType(String petType);

    ResponseEntity<Pet> getPetById(int petId);

    ResponseEntity<Pet> updatePet(int petId, Pet pet);

    ResponseEntity<Pet> deletePet(int petId);

    ResponseEntity<String> getAdoptionCountByType();
}
