package petEmporium.interfaces;

import petEmporium.entities.Address;
import petEmporium.entities.PetEmail;
import petEmporium.entities.PetUsers;

import java.util.List;
import java.util.Optional;

public interface IPetUsersEmailAddressDAO {

    List<PetUsers> getAllUsers();

    PetUsers createUsers(PetUsers petUsers);

    PetUsers updateUsers(Integer userId, PetUsers petUsers);

    void deleteUsers(Integer userId);

    PetUsers getUsersById(Integer userId);

    PetUsers getUsersByPhone(String phoneNumber);

    List<PetUsers> getUserByLastName(String lastName);

    PetUsers getUsersByEmail(String userEmail);

//EMAIL DAO

    List<PetEmail> getAllEmails();

    PetEmail createEmail(PetEmail petEmail);

    PetEmail updateEmail(Integer emailId, PetEmail petEmail);

    void deleteEmail(Integer emailId);

    PetEmail getEmailByID(Integer emailId);

    //ADDRESS DAO;

    List<Address> getAllAddresses();

    Address createAddress(Address address);

    Address updateAddress(Integer addressId, Address address);

    void deleteAddress(Integer addressId);

    Address getAddressById(Integer addressId);

}
