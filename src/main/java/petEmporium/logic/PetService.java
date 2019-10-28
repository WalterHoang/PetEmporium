package petEmporium.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import petEmporium.customexceptions.*;
import petEmporium.data.OrdersShoppingCartDAO;
import petEmporium.data.PetDao;
import petEmporium.entities.Pet;
import petEmporium.entities.ShoppingCart;
import petEmporium.interfaces.IPetService;
import petEmporium.validators.CommonValidation;
import petEmporium.validators.PetValidator;

import java.util.List;

/**
 * This service handles the validators and exception handling for pet requests.
 */
@Service
public class PetService implements IPetService {
    @Autowired
    private PetDao petDao;
    @Autowired
    private OrdersShoppingCartDAO ordersShoppingCartDAO;
    private CommonValidation commonValidation = new CommonValidation();
    private PetValidator petValidator = new PetValidator();
    private static Logger logger = LogManager.getLogger();

    /**
     * This method validates the required pet fields before passing the object to the dao
     *
     * @param pet A object of type Pet
     * @return A http status code
     */
    @Override
    public ResponseEntity<Pet> addpet(Pet pet) {

        if (commonValidation.checkEmptyString(pet.getPetName())) {
            throw new PetNameException();
        }
        if (!petValidator.checkType(pet.getPetType())) {
            throw new PetTypeException();
        }
        if (!petValidator.checkSex(pet.getPetSex())) {
            throw new PetSexException();
        }
        if (!commonValidation.checkValidPrice(pet.getPetAdoptionFee())) {
            throw new PetAdoptionFeeException();
        }
        try {
            Pet requestBody = petDao.addPet(pet);
            return new ResponseEntity<Pet>(pet, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    /**
     * This method calls the dao's findallpets method
     *
     * @return A http status code paired with a list of pets
     */
    @Override
    public ResponseEntity<List<Pet>> getAllPets() {
        try {
            return new ResponseEntity<>(petDao.getAllPets(), HttpStatus.OK);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    /**
     * This method calls the dao's getAllPetsByname method
     *
     * @param petName String type
     * @return a list of pets matching the entered name paired with a http code
     */
    @Override
    public ResponseEntity<List<Pet>> getAllPetsByName(String petName) {
        List<Pet> findPets;
        try {
            findPets = petDao.getAllPetsbyname(petName);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
        if (findPets.isEmpty()) {
            throw new PetNotFoundException();
        } else {
            return new ResponseEntity<>(findPets, HttpStatus.OK);
        }
    }

    /**
     * This method calls the dao's getAllPetsByType method
     *
     * @param petType
     * @return a list of pets that matches the type with a http code
     */
    @Override
    public ResponseEntity<List<Pet>> getAllPetsByType(String petType) {
        List<Pet> pets;
        try {
            pets = petDao.getAllPetsByType(petType);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
        if (pets == null) {
            throw new PetNotFoundException();
        } else {
            return new ResponseEntity<>(pets, HttpStatus.OK);
        }
    }

    /**
     * This method calls the dao's getPetById method
     *
     * @param petId
     * @return a pet with the matching id with a http code
     */
    @Override
    public ResponseEntity<Pet> getPetById(int petId) {
        Pet pet = petDao.getPetById(petId);
        if (pet == null) {
            throw new PetNotFoundException();
        } else {
            return new ResponseEntity<>(pet, HttpStatus.OK);
        }
    }

    /**
     * This method calls the dao's updatePet method after validating all required fields
     *
     * @param petId
     * @param pet
     * @return the new pet with the petId of the old pet
     */
    @Override
    public ResponseEntity<Pet> updatePet(int petId, Pet pet) {

        if (commonValidation.checkEmptyString(pet.getPetName())) {
            throw new PetNameException();
        }
        if (!petValidator.checkType(pet.getPetType())) {
            throw new PetTypeException();
        }
        if (!petValidator.checkSex(pet.getPetSex())) {
            throw new PetSexException();
        }
        if (!commonValidation.checkValidPrice(pet.getPetAdoptionFee())) {
            throw new PetAdoptionFeeException();
        }
        try {
            return new ResponseEntity<>(petDao.updatePet(petId, pet), HttpStatus.OK);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw e;
        }
    }

    /**
     * This method calls the dao's deletePet method
     *
     * @param petId
     * @return A status code
     */
    @Override
    public ResponseEntity<Pet> deletePet(int petId) {
        try {
            petDao.getPetById(petId);
        } catch (DataAccessException ex) {
            logger.debug(ex.getMessage());
            throw new FriendlyServerErrorException();
        }

        List<ShoppingCart> carts = ordersShoppingCartDAO.getAllShoppingCarts();

        for (ShoppingCart cart : carts) {
            if (cart.getPet() != null && cart.getPet().getPetId() == petId) {
                throw new CannotDeletePet();
            }
        }
        try {
            petDao.deletePet(petId);
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new NotFound();
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<String> getAdoptionCountByType() {
        try {
            String[] petTypes = {"Dog", "Cat", "Bird"};
            String output = "";
            for (int i = 0; i < petTypes.length; i++) {
                Long count = petDao.getAllPetsByType(petTypes[i]).stream().filter(Pet::isBeenAdopted).count();
                output = petTypes[i] + " : " + count.toString() + ", \n" + output;
            }
            return new ResponseEntity<>("Pets Adopted: \n" + output, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>("Cannot find an animal of that type", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
