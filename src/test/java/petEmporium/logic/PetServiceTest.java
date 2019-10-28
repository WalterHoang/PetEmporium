package petEmporium.logic;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import petEmporium.data.OrdersShoppingCartDAO;
import petEmporium.data.PetDao;
import petEmporium.entities.Pet;
import petEmporium.entities.ShoppingCart;
import petEmporium.interfaces.IPetRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class PetServiceTest {
    @Mock
    private IPetRepo mockPetRepo;
    @Mock
    private PetDao mockPetDao;
    @Mock
    private OrdersShoppingCartDAO mockcartdao;
    @InjectMocks
    private PetService mockService;
    private Pet testPet = new Pet(
            "Maurice",
            "Dog",
            "M",
            395.65,
            null,
            null,
            true);
    private List<Pet> pets = new ArrayList<>();

    @Before
    public void init() {
        testPet.setPetId(1);
        pets.add(testPet);
    }


    @Test
    public void addpet() {
        Mockito.when(mockPetDao.addPet(testPet)).thenReturn(testPet);
        ResponseEntity<Pet> expected = new ResponseEntity<>(testPet, HttpStatus.CREATED);
        assertEquals(expected, mockService.addpet(testPet));
    }

    @Test
    public void getAllPets() {
        Mockito.when(mockPetDao.getAllPets()).thenReturn(pets);
        ResponseEntity<List<Pet>> expected = new ResponseEntity<>(pets, HttpStatus.OK);
        assertEquals(expected, mockService.getAllPets());
    }

    @Test
    public void getAllPetsByName() {
        Mockito.when(mockPetDao.getAllPetsbyname("Maurice")).thenReturn(pets);
        ResponseEntity<List<Pet>> expected = new ResponseEntity<>(pets, HttpStatus.OK);
        assertEquals(expected, mockService.getAllPetsByName("Maurice"));
    }

    @Test
    public void getAllPetsByType() {
        Mockito.when(mockPetDao.getAllPetsByType("Dog")).thenReturn(pets);
        ResponseEntity<List<Pet>> expected = new ResponseEntity<>(pets, HttpStatus.OK);
        assertEquals(expected, mockService.getAllPetsByType("Dog"));
    }

    @Test
    public void getPetById() {
        Mockito.when(mockPetDao.getPetById(1)).thenReturn(testPet);
        ResponseEntity<Pet> expected = new ResponseEntity<>(testPet, HttpStatus.OK);
        assertEquals(expected, mockService.getPetById(1));
    }

    @Test
    public void updatePet() {
        Pet newPet = new Pet(
                "Horace",
                "Dog",
                "M",
                345.99,
                "Black",
                "2 years and 4 months",
                true
        );
        Mockito.when(mockPetDao.updatePet(1, newPet)).thenReturn(newPet);
        ResponseEntity<Pet> expected = new ResponseEntity<>(newPet, HttpStatus.OK);
        ResponseEntity<Pet> oldPet = new ResponseEntity<>(testPet, HttpStatus.OK);
        assertEquals(expected, mockService.updatePet(1, newPet));
        assertNotEquals(oldPet, mockService.updatePet(1, newPet));
    }

    @Test
    public void deletePet() {
        List<ShoppingCart> carts = new ArrayList<>();
        Mockito.when(mockPetDao.getPetById(1)).thenReturn(testPet);
        Mockito.when(mockcartdao.getAllShoppingCarts()).thenReturn(carts);
        mockService.deletePet(1);
        Mockito.verify(mockPetDao).deletePet(1);
    }
}