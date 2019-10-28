package petEmporium.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import petEmporium.entities.Address;
import petEmporium.entities.PetEmail;
import petEmporium.entities.PetUsers;
import petEmporium.interfaces.IAddressRepo;
import petEmporium.interfaces.IPetEmailRepo;
import petEmporium.interfaces.IPetUsersEmailAddressDAO;
import petEmporium.interfaces.IPetUsersRepo;

import java.util.List;
import java.util.Optional;

@Component
public class PetUsersEmailAddressDAO implements IPetUsersEmailAddressDAO {
    @Autowired
    private
    IAddressRepo addressRepo;
    @Autowired
    private IPetEmailRepo emailRepo;
    @Autowired
    private IPetUsersRepo petUsersRepo;

    @Override
    public List<PetUsers> getAllUsers() {
        return petUsersRepo.findAll();
    }

    @Override
    public PetUsers createUsers(PetUsers petUsers) {
        return petUsersRepo.save(petUsers);
    }

    @Override
    public PetUsers updateUsers(Integer userId, PetUsers petUsers) {
        PetUsers s = petUsersRepo.findById(userId).orElse(null);//Makes sure the id is in the system
        if (s == null) {
            return null;
        }
        petUsers.setPetUsersId(userId);
        return petUsersRepo.save(petUsers);
    }

    @Override
    public void deleteUsers(Integer userId) {
        petUsersRepo.deleteById(userId);
    }

    @Override
    public PetUsers getUsersById(Integer userId) {
        return petUsersRepo.findById(userId).orElseThrow();
    }

    @Override
    public PetUsers getUsersByPhone(String phoneNumber) {
        return petUsersRepo.findFirstByPhoneNumber(phoneNumber);
    }

    @Override
    public List<PetUsers> getUserByLastName(String lastName) {
        return petUsersRepo.findByLastName(lastName);
    }

    @Override
    public PetUsers getUsersByEmail(String userEmail) {
        PetEmail s = emailRepo.findByUserEmail(userEmail);
        return petUsersRepo.findByEmailEmailId(s.getEmailId());
    }

    @Override
    public List<PetEmail> getAllEmails() {
        return emailRepo.findAll();
    }

    @Override
    public PetEmail createEmail(PetEmail petEmail) {
        return emailRepo.save(petEmail);
    }

    @Override
    public PetEmail updateEmail(Integer emailId, PetEmail petEmail) {
        PetEmail s = emailRepo.findById(emailId).orElse(null);//Makes sure the id is in the system
        if (s == null) {
            return null;
        }
        petEmail.setEmailId(emailId);
        return emailRepo.save(petEmail);
    }

    @Override
    public void deleteEmail(Integer emailId) {
        emailRepo.deleteById(emailId);
    }

    @Override
    public PetEmail getEmailByID(Integer emailId) {
        return emailRepo.findById(emailId).orElseThrow();
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepo.findAll();
    }

    @Override
    public Address createAddress(Address address) {
        return addressRepo.save(address);
    }

    @Override
    public Address updateAddress(Integer addressId, Address address) {
        Address s = addressRepo.findById(addressId).orElse(null);//Makes sure the id is in the system
        if (s == null) {
            return null;
        }
        address.setAddressId(addressId);
        return addressRepo.save(address);
    }

    @Override
    public void deleteAddress(Integer addressId) {
        addressRepo.deleteById(addressId);
    }

    @Override
    public Address getAddressById(Integer addressId) {
        return addressRepo.findById(addressId).orElseThrow();
    }
}