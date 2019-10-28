package petEmporium.logic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import petEmporium.customexceptions.*;
import petEmporium.interfaces.IPetUsersEmailAddressDAO;
import petEmporium.entities.Address;
import petEmporium.entities.PetEmail;
import petEmporium.entities.PetUsers;
import petEmporium.interfaces.IUsersEmailAddressService;
import petEmporium.validators.ValidateUserInfo;

import java.util.List;

@Service
public class UsersEmailAddressServices implements IUsersEmailAddressService {

    @Autowired
    private IPetUsersEmailAddressDAO pUEDAO;
    private ResponseEntity<String> strRespEntity;

    private ValidateUserInfo vUInfo = new ValidateUserInfo();
    private static Logger logger = LogManager.getLogger();

    @Override
    public ResponseEntity<List<PetUsers>> getAllUsers() {
        return new ResponseEntity<>(pUEDAO.getAllUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<PetEmail>> getAllEmails() {
        return new ResponseEntity<>(pUEDAO.getAllEmails(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Address>> getAllAddress() {
        return new ResponseEntity<>(pUEDAO.getAllAddresses(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createUsers(PetUsers petUsers) {
        try {
            if (vUInfo.validateUser(petUsers)) {
                pUEDAO.createUsers(petUsers);
                strRespEntity = new ResponseEntity<>(HttpStatus.CREATED);
                return strRespEntity;
            } else {
                throw new UserInvalidInput();
            }
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new UserInvalidInput();
        }

    }

    @Override
    public ResponseEntity<String> createAddress(Address address) {
        try {
            if (vUInfo.validateAddress(address)) {
                pUEDAO.createAddress(address);
                strRespEntity = new ResponseEntity<>(HttpStatus.CREATED);
                return strRespEntity;
            } else {
                throw new IncorrectAddress();
            }
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new IncorrectAddress();
        }
    }

    @Override
    public ResponseEntity<String> createEmail(PetEmail petEmail) {
        try {
            if (vUInfo.emailFormValidation(petEmail.getUserEmail())) {

              for(PetEmail userEmail : pUEDAO.getAllEmails().stream().toArray(PetEmail[]::new)){
                  if(userEmail.getUserEmail().equals(petEmail.getUserEmail()) ){
                      throw new EmailNotUnique();
                  }
              }
                pUEDAO.createEmail(petEmail);
                strRespEntity = new ResponseEntity<>(HttpStatus.CREATED);
                return strRespEntity;
            }

            throw new IncorrectEmail();

        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new IncorrectEmail();
        }
    }

    @Override
    public ResponseEntity<String> updateUsers(Integer petUsersId, PetUsers petUsers) {
        try {
            if (pUEDAO.getUsersById(petUsersId) == null) {
                throw new NotFound();
            }
            if (vUInfo.validateUser(petUsers)) {
                pUEDAO.updateUsers(petUsersId, petUsers);
                strRespEntity = new ResponseEntity<>(HttpStatus.OK);
                return strRespEntity;
            } else {
                throw new UserInvalidInput();
            }
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new UserInvalidInput();
        }

    }

    @Override
    public ResponseEntity<String> updateAddress(Integer addressId, Address address) {
        try {
            if (pUEDAO.getAddressById(addressId) == null) {
                throw new NotFound();
            }
            if (vUInfo.validateAddress(address)) {
                pUEDAO.updateAddress(addressId, address);
                strRespEntity = new ResponseEntity<>(HttpStatus.OK);
                return strRespEntity;
            } else {
                throw new IncorrectAddress();
            }
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new IncorrectAddress();
        }
    }

    @Override
    public ResponseEntity<String> updateEmail(Integer emailId, PetEmail email) {
        try {
            if (pUEDAO.getEmailByID(emailId) == null) {
                throw new NotFound();
            }
            pUEDAO.updateEmail(emailId, email);
            strRespEntity = new ResponseEntity<>(HttpStatus.OK);
            return strRespEntity;
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new IncorrectEmail();
        }
    }

    @Override
    public ResponseEntity<String> deleteUsers(Integer petUsersId) {
        try {
            if (pUEDAO.getUsersById(petUsersId) == null) {
                throw new NotFound();
            }
            pUEDAO.deleteUsers(petUsersId);
            strRespEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return strRespEntity;
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }

    @Override
    public ResponseEntity<String> deleteAddress(Integer addressId) {
        try {
            if (pUEDAO.getAddressById(addressId) == null) {
                throw new NotFound();
            }
            pUEDAO.deleteAddress(addressId);
            strRespEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return strRespEntity;
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new DeleteCustomerFirst();
        }
    }

    @Override
    public ResponseEntity<String> deleteEmail(Integer emailId) {
        try {
            if (pUEDAO.getEmailByID(emailId) == null) {
                throw new NotFound();
            }
            pUEDAO.deleteEmail(emailId);
            strRespEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return strRespEntity;
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new DeleteCustomerFirst();
        }
    }

    @Override
    public ResponseEntity<PetUsers> getByUsersId(Integer petUsersId) {
        try {
            if (pUEDAO.getUsersById(petUsersId) == null) {
                throw new NotFound();
            }
            return new ResponseEntity<>(pUEDAO.getUsersById(petUsersId), HttpStatus.OK);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }

    @Override
    public ResponseEntity<Address> getByAddressId(Integer addressId) {
        try {
            if (pUEDAO.getAddressById(addressId) == null) {
                throw new NotFound();
            }
            return new ResponseEntity<>(pUEDAO.getAddressById(addressId), HttpStatus.OK);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }

    @Override
    public ResponseEntity<PetEmail> getByEmailId(Integer emailId) {
        try {
            if (pUEDAO.getEmailByID(emailId) == null) {
                throw new NotFound();
            }
            return new ResponseEntity<>(pUEDAO.getEmailByID(emailId), HttpStatus.OK);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }

    //Custom Gets

    @Override
    public ResponseEntity<PetUsers> getByUsersPhoneNumber(String phoneNumber) {
        try {
            if (pUEDAO.getUsersByPhone(phoneNumber) == null) {
                throw new NotFound();
            }
            return new ResponseEntity<>(pUEDAO.getUsersByPhone(phoneNumber), HttpStatus.OK);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }

    @Override
    public ResponseEntity<List<PetUsers>> getByUsersLastName(String lastName) {
        try {
            if (pUEDAO.getUserByLastName(lastName).isEmpty()) {
                throw new NotFound();
            }
            return new ResponseEntity<>(pUEDAO.getUserByLastName(lastName), HttpStatus.OK);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }

    @Override
    public ResponseEntity<PetUsers> getByUsersEmail(String userEmail) {
        try {
            if (pUEDAO.getUsersByEmail(userEmail) == null) {
                throw new NotFound();
            }
            return new ResponseEntity<>(pUEDAO.getUsersByEmail(userEmail), HttpStatus.OK);
        } catch (Exception ex) {
            logger.debug(ex.getMessage());
            throw new NotFound();
        }
    }
}
