package petEmporium.logic;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import petEmporium.data.PetUsersEmailAddressDAO;
import petEmporium.entities.Address;
import petEmporium.entities.PetEmail;
import petEmporium.entities.PetUsers;
import petEmporium.interfaces.IAddressRepo;
import petEmporium.interfaces.IPetEmailRepo;
import petEmporium.interfaces.IPetUsersRepo;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UsersEmailAddressServicesTest {
    @Mock
    private IPetUsersRepo mockUserRepo;
    @Mock
    private IAddressRepo mockAddressRepo;
    @Mock
    private IPetEmailRepo mockEmailRepo;
    @Mock
    private PetUsersEmailAddressDAO mockDao;
    @InjectMocks
    private UsersEmailAddressServices mockService;

    private Address testAddress;
    private PetEmail testEmail;
    private PetEmail testEmail2;
    private PetUsers testUser;
    private Set<Address> testaddresses = new HashSet<>();

    private List<PetUsers> petUsers = new ArrayList<>();
    private List<PetEmail> petEmails = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();

    @Before
    public void init() {
        testAddress = new Address(
                "1234 test Street",
                null,
                "Test Lake City",
                "CO",
                "12345-1234"
        );
        testAddress.setAddressId(1);
        testaddresses.add(testAddress);
        testEmail = new PetEmail(
                "Example@example.int",
                "Customer"
        );
        testEmail.setEmailId(1);
        testEmail2 = new PetEmail(
                "Example@example.gov",
                "Customer"
        );
        testEmail.setEmailId(1);
        petEmails.add(testEmail);
        testUser = new PetUsers(
                "Smtih",
                "Jonason",
                "666-777-8899",
                testEmail,
                testaddresses
        );
        petUsers.add(testUser);
    }

    @Test
    public void getAllUsers() {
        Mockito.when(mockDao.getAllUsers()).thenReturn(petUsers);
        ResponseEntity<List<PetUsers>> expected = new ResponseEntity<>(petUsers, HttpStatus.OK);
        assertEquals(expected, mockService.getAllUsers());
    }

    @Test
    public void getAllEmails() {
        Mockito.when(mockDao.getAllEmails()).thenReturn(petEmails);
        ResponseEntity<List<PetEmail>> expected = new ResponseEntity<>(petEmails, HttpStatus.OK);
        assertEquals(expected, mockService.getAllEmails());
    }

    @Test
    public void getAllAddress() {
        Mockito.when(mockDao.getAllAddresses()).thenReturn(addresses);
        ResponseEntity<List<Address>> expected = new ResponseEntity<>(addresses, HttpStatus.OK);
        assertEquals(expected, mockService.getAllAddress());
    }

    @Test
    public void createUsers() {
        Mockito.when(mockDao.createUsers(testUser)).thenReturn(testUser);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.CREATED);
        assertEquals(expected, mockService.createUsers(testUser));
    }

    @Test
    public void createAddress() {
        Mockito.when(mockDao.createAddress(testAddress)).thenReturn(testAddress);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.CREATED);
        assertEquals(expected, mockService.createAddress(testAddress));
    }

    @Test
    public void createEmail() {
        Mockito.when(mockDao.createEmail(testEmail)).thenReturn(testEmail);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.CREATED);
        assertEquals(expected, mockService.createEmail(testEmail));
    }

    @Test
    public void updateUsers() {
        PetUsers newUser = new PetUsers(
                "Jonah",
                "Jonason",
                "666-777-8899",
                testEmail2,
                null
        );
        Mockito.when(mockDao.getUsersById(1)).thenReturn(testUser);
        Mockito.when(mockDao.updateUsers(1, newUser)).thenReturn(newUser);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(expected, mockService.updateUsers(1, newUser));
    }

    @Test
    public void updateAddress() {
        Address newAddress = new Address(
                "4432 quiz street",
                "#222",
                "Quiz Kingdome",
                "NY",
                "55332"
        );

        Mockito.when(mockDao.getAddressById(1)).thenReturn(testAddress);
        Mockito.when(mockDao.updateAddress(1, newAddress)).thenReturn(newAddress);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(expected, mockService.updateAddress(1, newAddress));
    }

    @Test
    public void updateEmail() {
        PetEmail newEmail = new PetEmail(
                "Shredder2234@example.TnT",
                "Customer"
        );
        Mockito.when(mockDao.getEmailByID(1)).thenReturn(testEmail);
        Mockito.when(mockDao.updateEmail(1, newEmail)).thenReturn(newEmail);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(expected, mockService.updateEmail(1, newEmail));
    }

    @Test
    public void zdeleteUsers() {
        Mockito.when(mockDao.getUsersById(1)).thenReturn(testUser);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        assertEquals(expected, mockService.deleteUsers(1));
    }

    @Test
    public void zzdeleteAddress() {
        Mockito.when(mockDao.getAddressById(1)).thenReturn(testAddress);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        assertEquals(expected, mockService.deleteAddress(1));
    }

    @Test
    public void zzdeleteEmail() {
        Mockito.when(mockDao.getEmailByID(1)).thenReturn(testEmail);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        assertEquals(expected, mockService.deleteEmail(1));
    }

    @Test
    public void getByUsersId() {
        Mockito.when(mockDao.getUsersById(1)).thenReturn(testUser);
        ResponseEntity<PetUsers> expected = new ResponseEntity<>(testUser, HttpStatus.OK);
        assertEquals(expected, mockService.getByUsersId(1));
    }

    @Test
    public void getByAddressId() {
        Mockito.when(mockDao.getAddressById(1)).thenReturn(testAddress);
        ResponseEntity<Address> expected = new ResponseEntity<>(testAddress, HttpStatus.OK);
        assertEquals(expected, mockService.getByAddressId(1));
    }

    @Test
    public void getByEmailId() {
        Mockito.when(mockDao.getEmailByID(1)).thenReturn(testEmail);
        ResponseEntity<PetEmail> expected = new ResponseEntity<>(testEmail, HttpStatus.OK);
        assertEquals(expected, mockService.getByEmailId(1));
    }

    @Test
    public void getByUsersPhoneNumber() {
        Mockito.when(mockDao.getUsersByPhone("666-777-8899")).thenReturn(testUser);
        ResponseEntity<PetUsers> expected = new ResponseEntity<>(testUser, HttpStatus.OK);
        assertEquals(expected, mockService.getByUsersPhoneNumber("666-777-8899"));
    }

    @Test
    public void getByUsersLastName() {
        Mockito.when(mockDao.getUserByLastName("Jonason")).thenReturn(petUsers);
        ResponseEntity<List<PetUsers>> expected = new ResponseEntity<>(petUsers, HttpStatus.OK);
        assertEquals(expected, mockService.getByUsersLastName("Jonason"));
    }

    @Test
    public void getByUsersEmail() {
        Mockito.when(mockDao.getUsersByEmail("Example@example.int")).thenReturn(testUser);
        ResponseEntity<PetUsers> expected = new ResponseEntity<>(testUser, HttpStatus.OK);
        assertEquals(expected, mockService.getByUsersEmail("Example@example.int"));
    }
}
