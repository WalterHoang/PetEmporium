package petEmporium.presentation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;
import petEmporium.customexceptions.CannotDeletePet;
import petEmporium.customexceptions.ExceptionResponse;
import petEmporium.customexceptions.FriendlyServerErrorException;
import petEmporium.entities.Pet;
import petEmporium.interfaces.IPetController;
import petEmporium.logic.PetService;

import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

/**
 * This Controller handles all pet requests and passes them to the service
 */
@RestController
@RequestMapping(value = "/pets")
@ApiResponses(value = {@ApiResponse(code = 500, message = "An unexpected error occurred.")})
public class PetController implements IPetController {
    @Autowired
    private PetService petService;
    private static Logger logger = LogManager.getLogger();

    /**
     * This method handles the addition of a pet to the database
     *
     * @param pet a json body converted to an object of type Pet
     * @return A Http status code reflecting the status of the method and the added pet
     */
    @Override
    @PostMapping
    @ApiOperation("Posts a new pet to the system.") // For swagger
    @ApiResponses(value = { // response codes for swagger
            @ApiResponse(code = 201, message = "Created", response = Pet.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class)
    })
    public ResponseEntity<Pet> addPet(@RequestBody Pet pet) {
        try {
            return petService.addpet(pet);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    /**
     * This method handles the getting of all pets.
     * This method is assigned an endpoint of '/pets/allPets'
     *
     * @return A http status code reflecting the status of the method and a list of pets
     */
    @Override
    @GetMapping(value = "/allPets")
    @ApiOperation("Gets all Pets in the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Pet.class)
    })
    public ResponseEntity<List<Pet>> getallPets() {
        try {
            return petService.getAllPets();
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    /**
     * This method handles the getting of all pets by name
     * This method is assigned an endpoint of '/pets/petName'
     *
     * @param petName A name of the pet
     * @return A http status code reflecting the status of the method and a list of pets
     */
    @Override
    @GetMapping(value = "/petName")
    @ApiOperation("Gets a list of pets in the system by name.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Pet.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<List<Pet>> getAllPetsByName(@RequestParam String petName) {
        try {
            return petService.getAllPetsByName(petName);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    /**
     * This method handles the getting of all pets by type
     * This method is assigned an endpoint of '/pets/petType'
     *
     * @param petType Currently takes string one of these three pet types: "Dog", "Cat", and "Bird"
     * @return A http status code reflecting the status of the method and a list of pets
     */
    @Override
    @GetMapping(value = "/petType")
    @ApiOperation("Gets a list of pets in the system by type.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Pet.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class)
    })
    public ResponseEntity<List<Pet>> getAllPetsByType(@RequestParam String petType) {
        try {
            return petService.getAllPetsByType(petType);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    /**
     * This method handles the getting of a pet by id
     *
     * @param petId
     * @return A http status code reflecting the status of the method and a pet object
     */
    @Override
    @GetMapping(value = "/{petId}")
    @ApiOperation("Gets a Pet in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Pet.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<Pet> getPetById(@PathVariable int petId) {
        try {
            return petService.getPetById(petId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    /**
     * This method handles updating pets
     *
     * @param petId id of the current pet in the database
     * @param pet   object containing new pet information
     * @return new pet object with pet id of old pet
     */
    @Override
    @PutMapping(value = "/{petId}")
    @ApiOperation("Updates a Pet in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Pet.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<Pet> updatePet(@PathVariable int petId, @RequestBody Pet pet) {
        try {
            return petService.updatePet(petId, pet);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    /**
     * This method handles deleting pets
     *
     * @param petId id of pet to delete
     * @return A http status code
     */
    @Override
    @DeleteMapping(value = "/{petId}")
    @ApiOperation("Deletes a Pet in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<Pet> deletePet(@PathVariable int petId) {
            return petService.deletePet(petId);
    }

    @Override
    @GetMapping("/getAdoptionCountByType")
    @ApiOperation("Returns the pet count by type")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 500, message = "Cannot find an animal of that type"),
    })
    public ResponseEntity<String> getAdoptionCountByType() {
        try {
            return petService.getAdoptionCountByType();
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }
}