package petEmporium.presentation;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerErrorException;
import petEmporium.customexceptions.ExceptionResponse;
import petEmporium.customexceptions.FriendlyServerErrorException;
import petEmporium.entities.Address;
import petEmporium.entities.PetEmail;
import petEmporium.entities.PetUsers;
import petEmporium.interfaces.IUsersEmailAddressController;
import petEmporium.logic.UsersEmailAddressServices;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
@ApiResponses(value = {@ApiResponse(code = 500, message = "An unexpected error occurred.")})
public class UsersEmailAddressController implements IUsersEmailAddressController {

    @Autowired
    private UsersEmailAddressServices uEAServe;

    private static Logger logger = LogManager.getLogger();

    @Override
    @GetMapping(value = "/allUsers")
    @ApiOperation("Gets all Users in the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = PetUsers.class)
    })
    public ResponseEntity<List<PetUsers>> getAllUsers() {
        try {
            return uEAServe.getAllUsers();
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/allEmails")
    @ApiOperation("Gets all Emails in the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = PetEmail.class)
    })
    public ResponseEntity<List<PetEmail>> getAllEmails() {
        try {
            return uEAServe.getAllEmails();
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/allAddresses")
    @ApiOperation("Gets all Addresses in the system.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "List", response = Address.class)
    })
    public ResponseEntity<List<Address>> getAllAddress() {
        try {
            return uEAServe.getAllAddress();
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PostMapping(value = "/addUser")
    @ApiOperation("Posts a new User to the system.") // For swagger
    @ApiResponses(value = { // response codes for swagger
            @ApiResponse(code = 201, message = "Created", response = PetUsers.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> createUsers(@RequestBody PetUsers petUsers) {
        try {
            return uEAServe.createUsers(petUsers);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PostMapping(value = "/addAddress")
    @ApiOperation("Posts a new Address to the system.") // For swagger
    @ApiResponses(value = { // response codes for swagger
            @ApiResponse(code = 201, message = "Created", response = Address.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> createAddress(@RequestBody Address address) {
        try {
            return uEAServe.createAddress(address);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PostMapping(value = "/addEmail")
    @ApiOperation("Posts a new Email to the system.") // For swagger
    @ApiResponses(value = { // response codes for swagger
            @ApiResponse(code = 201, message = "Created", response = PetEmail.class),
            @ApiResponse(code = 400, message = "Bad Request", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> createEmail(@RequestBody PetEmail petEmail) {
        try {
            return uEAServe.createEmail(petEmail);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PutMapping(value = "/updateUser{petUsersId}")
    @ApiOperation("Updates a User in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PetUsers.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> updateUsers(@PathVariable Integer petUsersId, @RequestBody PetUsers petUsers) {
        try {
            return uEAServe.updateUsers(petUsersId, petUsers);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PutMapping(value = "/updateAddress{addressId}")
    @ApiOperation("Updates a Address in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Address.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> updateAddress(@PathVariable Integer addressId, @RequestBody Address address) {
        try {
            return uEAServe.updateAddress(addressId, address);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @PutMapping(value = "/updateEmail{emailId}")
    @ApiOperation("Updates a Email in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PetEmail.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> updateEmail(@PathVariable Integer emailId, @RequestBody PetEmail email) {
        try {
            return uEAServe.updateEmail(emailId, email);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @DeleteMapping(value = "/deleteUser{petUsersId}")
    @ApiOperation("Deletes a User in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> deleteUsers(@PathVariable Integer petUsersId) {
        try {
            return uEAServe.deleteUsers(petUsersId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @DeleteMapping(value = "/deleteAddress{addressId}")
    @ApiOperation("Deletes a Address in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> deleteAddress(@PathVariable Integer addressId) {
        try {
            return uEAServe.deleteAddress(addressId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @DeleteMapping(value = "/deleteEmail{emailId}")
    @ApiOperation("Deletes a Email in the system by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class),
            @ApiResponse(code = 409, message = "Conflict Occurred", response = ExceptionResponse.class)
    })
    public ResponseEntity<String> deleteEmail(@PathVariable Integer emailId) {
        try {
            return uEAServe.deleteEmail(emailId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/user{petUsersId}")
    @ApiOperation("Gets a user by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Optional", response = PetUsers.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<PetUsers> getByUsersId(@PathVariable Integer petUsersId) {
        try {
            return uEAServe.getByUsersId(petUsersId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/address{addressId}")
    @ApiOperation("Gets a Address by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Optional", response = Address.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<Address> getByAddressId(@PathVariable Integer addressId) {
        try {
            return uEAServe.getByAddressId(addressId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/email{emailId}")
    @ApiOperation("Gets a email by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Optional", response = PetEmail.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<PetEmail> getByEmailId(@PathVariable Integer emailId) {
        try {
            return uEAServe.getByEmailId(emailId);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/userbyphonenumber/")
    @ApiOperation("Gets a user by Phone number")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Optional", response = PetUsers.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<PetUsers> getByUsersPhoneNumber(@RequestParam String phoneNumber) {
        try {
            return uEAServe.getByUsersPhoneNumber(phoneNumber);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/userbylastname/")
    @ApiOperation("Gets a user by Last Name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Optional", response = PetUsers.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<List<PetUsers>> getByUsersLastName(@RequestParam String lastName) {
        try {
            return uEAServe.getByUsersLastName(lastName);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }

    @Override
    @GetMapping(value = "/userbyemail/")
    @ApiOperation("Gets a user by email")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", responseContainer = "Optional", response = PetUsers.class),
            @ApiResponse(code = 404, message = "Not Found", response = ExceptionResponse.class)
    })
    public ResponseEntity<PetUsers> getByUsersEmail(@RequestParam String userEmail) {
        try {
            return uEAServe.getByUsersEmail(userEmail);
        } catch (ServerErrorException e) {
            logger.debug(e.getMessage());
            throw new FriendlyServerErrorException();
        }
    }
}
