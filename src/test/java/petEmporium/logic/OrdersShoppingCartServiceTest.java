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
import petEmporium.data.InventoryDao;
import petEmporium.data.OrdersShoppingCartDAO;

import petEmporium.entities.*;
import petEmporium.interfaces.*;

import java.util.ArrayList;
import java.util.HashSet;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OrdersShoppingCartServiceTest {
    @Mock
    private IPetUsersRepo mockUserRepo;
    @Mock
    private IAddressRepo mockAddressRepo;
    @Mock
    private IPetEmailRepo mockEmailRepo;
    @Mock
    InventoryDao mockInventoryDao;
    @Mock
    IInventoryRepo mockInventoryRepo;
    @Mock
    IItemTypeRepo mockItemTypeRepo;
    @Mock
    private IPetRepo mockPetRepo;
    @Mock
    private OrdersShoppingCartDAO mockDao;
    @InjectMocks
    private OrdersShoppingCartService mockService;

    private static Inventory testItem;
    private static ItemType type;
    private Address testAddress;
    private PetEmail testEmail;
    private PetUsers testUser;
    private Set<Address> testaddresses = new HashSet<>();
    private ShoppingCart testCart;
    private PetOrders testOrder;
    private Set<ShoppingCart> carts = new HashSet<>();
    private List<ShoppingCart> cartList = new ArrayList<>();
    private List<PetOrders> orders = new ArrayList<>();

    @Before
    public void before() {
        type = new ItemType("Food");
        type.setItemTypeId(1);
        //  mockItemTypeRepo.save(type);

        testItem = new Inventory("Test", type, "12345678", 12, 12.99);
        testItem.setInventoryId(1);
        //  mockInventoryRepo.save(testItem);
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
        testUser = new PetUsers(
                "Smtih",
                "Jonason",
                "666-777-8899",
                testEmail,
                testaddresses
        );
        testCart = new ShoppingCart();
        testCart.setCartId(1);
        testCart.setItemOrder(testItem);
        testCart.setItemQuantity(1);
        testCart.setLineTotal(12.99);
        carts.add(testCart);
        testOrder = new PetOrders(testUser, carts, 12.99 * 1.07);
        testCart.setPetOrders(testOrder);
        testOrder.setOrderId(1);

        cartList.add(testCart);
        orders.add(testOrder);
    }

    @Test
    public void getAllShoppingCarts() {
        Mockito.when(mockDao.getAllShoppingCarts()).thenReturn(cartList);
        ResponseEntity<List<ShoppingCart>> expected = new ResponseEntity<>(cartList, HttpStatus.OK);
        assertEquals(expected, mockService.getAllShoppingCarts());
    }

    @Test
    public void getAllOrders() {
        Mockito.when(mockDao.getAllOrders()).thenReturn(orders);
        ResponseEntity<List<PetOrders>> expected = new ResponseEntity<>(orders, HttpStatus.OK);
        assertEquals(expected, mockService.getAllOrders());
    }

    @Test
    public void getOrderById() {
        Mockito.when(mockDao.getOrderById(1)).thenReturn(testOrder);
        ResponseEntity<PetOrders> expected = new ResponseEntity<>(testOrder, HttpStatus.OK);
        assertEquals(expected, mockService.getOrderById(1));
    }

    @Test
    public void getShoppingCartById() {
        Mockito.when(mockDao.getShoppingCartById(1)).thenReturn(testCart);
        ResponseEntity<ShoppingCart> expected = new ResponseEntity<>(testCart, HttpStatus.OK);
        assertEquals(expected, mockService.getShoppingCartById(1));
    }

    @Test
    public void createaShoppingCart() {
        Mockito.when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        Mockito.when(mockDao.createShoppingCart(testCart)).thenReturn(testCart);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.CREATED);
        assertEquals(expected, mockService.createShoppingCart(testCart));
    }

    @Test
    public void createOrder() {
        Mockito.when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        Mockito.when(mockDao.getShoppingCartById(1)).thenReturn(testCart);
        Mockito.when(mockDao.createOrder(testOrder)).thenReturn(testOrder);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.CREATED);
        assertEquals(expected.getStatusCode(), mockService.createOrder(testOrder).getStatusCode());

    }

    @Test
    public void updateOrder() {
        testUser.setFirstName("Joey");
        testUser.setLastName("Wheeler");
        PetOrders newOrder = new PetOrders(testUser, carts, 12.99 * 1.07);

        Mockito.when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        Mockito.when(mockDao.getShoppingCartById(1)).thenReturn(testCart);
        Mockito.when(mockDao.updateOrder(1, newOrder)).thenReturn(newOrder);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(expected, mockService.updateOrder(1, newOrder));
    }

    @Test
    public void updateShoppingCart() {
        testItem.setName("testFood");
        ShoppingCart newCart = new ShoppingCart();
        newCart.setCartId(1);
        newCart.setItemOrder(testItem);
        newCart.setItemQuantity(1);
        newCart.setLineTotal(12.99);
        Mockito.when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        Mockito.when(mockDao.updateShoppingCart(1, newCart)).thenReturn(newCart);
        ResponseEntity<String> expected = new ResponseEntity<>(HttpStatus.OK);
        assertEquals(expected, mockService.updateShoppingCart(1, newCart));
    }
}
