package petEmporium.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petEmporium.customexceptions.PetNotFoundException;
import petEmporium.entities.Pet;
import petEmporium.interfaces.IPetDao;
import petEmporium.interfaces.IPetRepo;

import java.util.List;

/**
 * This Dao accesses the database methods for pet requests
 */
@Component
public class PetDao implements IPetDao {
    // Handles dependency of repo
    @Autowired
    private IPetRepo repo;

    /**
     * This method accesses the repository's save method to add the pet to the database
     *
     * @param pet An object of type Pet
     * @return The newly added pet on success
     */
    @Override
    public Pet addPet(Pet pet) {
        return repo.save(pet);
    }

    /**
     * This method accesses the repository's findall method to find all pets in the database
     *
     * @return a list of pets
     */
    @Override
    public List<Pet> getAllPets() {
        return repo.findAll();
    }

    /**
     * This method uses Jpa's custom method implementation to find all pets by a name
     *
     * @param petName
     * @return a list of pets matching the name entered
     */
    @Override
    public List<Pet> getAllPetsbyname(String petName) {
        return repo.findAllByPetName(petName);
    }

    /**
     * This method uses Jpa's custom method implementation to find all pets by a type
     *
     * @param petType
     * @return a list of pets matching the type entered
     */
    @Override
    public List<Pet> getAllPetsByType(String petType) {
        return repo.findAllByPetType(petType);
    }

    /**
     * This method uses Jpa's findById method to find a requested pet
     *
     * @param petId
     * @return a pet object if found or null if not
     */
    @Override
    public Pet getPetById(int petId) {
        return repo.findById(petId).orElse(null);
    }

    /**
     * This method assigns a pet id existing in the database then overwrites the pet information
     * with the new pet information
     *
     * @param petId id of the pet in the database
     * @param pet   new pet information
     * @return the new pet object with the id assigned
     */
    @Override
    public Pet updatePet(int petId, Pet pet) {
        Pet petdb = repo.findById(petId).orElse(null);
        if (petdb == null) {
            throw new PetNotFoundException();
        } else {
            pet.setPetId(petId);
            return repo.save(pet);
        }
    }

    /**
     * This method calls the repo's deleteById method
     *
     * @param petId
     */
    @Override
    public void deletePet(int petId) {
        repo.deleteById(petId);
    }
}
