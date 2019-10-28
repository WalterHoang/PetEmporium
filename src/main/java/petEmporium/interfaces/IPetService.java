package petEmporium.interfaces;

import org.springframework.http.ResponseEntity;

import petEmporium.entities.Pet;

import java.util.List;

public interface IPetService {

    ResponseEntity<Pet> addpet(Pet pet);

    ResponseEntity<List<Pet>> getAllPets();

    ResponseEntity<List<Pet>> getAllPetsByName(String petName);

    ResponseEntity<List<Pet>> getAllPetsByType(String petType);

    ResponseEntity<Pet> getPetById(int petId);

    ResponseEntity<Pet> updatePet(int petId, Pet pet);

    ResponseEntity<Pet> deletePet(int petId);

    ResponseEntity<String> getAdoptionCountByType();
}
