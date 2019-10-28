package petEmporium.interfaces;

import org.springframework.http.ResponseEntity;
import petEmporium.entities.Address;
import petEmporium.entities.PetEmail;
import petEmporium.entities.PetUsers;

import java.util.List;

public interface IUsersEmailAddressController {
    ResponseEntity<List<PetUsers>> getAllUsers();

    ResponseEntity<List<PetEmail>> getAllEmails();

    ResponseEntity<List<Address>> getAllAddress();

    ResponseEntity<String> createUsers(PetUsers petUsers);

    ResponseEntity<String> createAddress(Address address);

    ResponseEntity<String> createEmail(PetEmail petEmail);

    ResponseEntity<String> updateUsers(Integer petUsersId, PetUsers petUsers);

    ResponseEntity<String> updateAddress(Integer addressId, Address address);

    ResponseEntity<String> updateEmail(Integer emailId, PetEmail email);

    ResponseEntity<String> deleteUsers(Integer petUsersId);

    ResponseEntity<String> deleteAddress(Integer addressId);

    ResponseEntity<String> deleteEmail(Integer emailId);

    ResponseEntity<PetUsers> getByUsersId(Integer petUsersId);

    ResponseEntity<Address> getByAddressId(Integer addressId);

    ResponseEntity<PetEmail> getByEmailId(Integer emailId);

    //Custom gets for the User class!
    ResponseEntity<PetUsers> getByUsersPhoneNumber(String phoneNumber);

    ResponseEntity<List<PetUsers>> getByUsersLastName(String lastName);

    ResponseEntity<PetUsers> getByUsersEmail(String userEmail);

}
