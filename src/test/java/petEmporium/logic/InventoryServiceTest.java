package petEmporium.logic;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import petEmporium.customexceptions.*;
import petEmporium.data.InventoryDao;
import petEmporium.data.OrdersShoppingCartDAO;
import petEmporium.entities.Inventory;
import petEmporium.entities.ItemType;
import petEmporium.entities.ShoppingCart;
import petEmporium.interfaces.IInventoryRepo;
import petEmporium.interfaces.IItemTypeRepo;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class InventoryServiceTest {

    // mock the layers below the one we're testing
    @Mock
    InventoryDao mockInventoryDao;
    @Mock
    IInventoryRepo mockInventoryRepo;
    @Mock
    OrdersShoppingCartDAO mockOrderDao;
    @Mock
    IItemTypeRepo mockItemTypeRepo;

    //inject the mock into our service class
    @InjectMocks
    InventoryService classUnderTest;


    static Inventory testItem;
    static Inventory testItem2;
    static ItemType type;
    static ItemType type2;

    // create a new inventory item to test
    @Before
    public void before() {
        type = new ItemType("Food");
        mockItemTypeRepo.save(type);

        type2 = new ItemType("Toys");
        mockItemTypeRepo.save(type2);

        testItem = new Inventory("Test", type, "12345678", 12, 12.99);
        testItem.setInventoryId(1);

        // add another item with the same sku to the repo to use when testing uniqueness
        testItem2 = new Inventory("Test2", type, "12345678", 0, 12.99);
        mockInventoryRepo.save(testItem2);

    }

    @Test
    public void addInventoryItemHappy() {
        when(mockInventoryDao.addInventoryItem(testItem)).thenReturn(testItem);
        Inventory result = classUnderTest.addInventoryItem(testItem);
        assertEquals(result, testItem);
    }

    ;

    @Test(expected = InvalidInventoryNameException.class)
    public void addInventoryItemNameTooLong() {
        // set name to anything over 100 characters
        testItem.setName("svy4WoyXZRomtJyZsoBkzTlv18Qff11asVVqJLATcyo2DtqcyNSvXEnP4uceLnYi9aIprfiqRTA8x5EB34GLO3fLTvyvJX0z7I6i32");
        classUnderTest.addInventoryItem(testItem);

    }

    ;

    @Test(expected = InvalidInventoryNameException.class)
    public void addInventoryItemNameTooShort() {
        //set name to anything with less than 3 characters
        testItem.setName("A");
        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test(expected = InvalidInventoryItemTypeException.class)
    public void addInventoryItemInvalidType() {
        //set item type to anything EXCEPT Food, Toys, or Accessories
        type = testItem.getItemType();
        type.setItemType("wrong");
        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test(expected = InvalidInventorySkuException.class)
    public void addInventoryItemInvalidSkuTooLong() {
        // set sku to more than 8 alphanumeric characters
        testItem.setSku("123456789");
        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test(expected = InvalidInventorySkuException.class)
    public void addInventoryItemInvalidSkuTooShort() {
        // set sku to less than 8 alphanumeric characters
        testItem.setSku("1234567");
        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test(expected = InvalidInventorySkuException.class)
    public void addInventoryItemInvalidSkuNotAlphanumeric() {
        // set sku to contain a special symbol
        testItem.setSku("1234567*");
        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test(expected = InvalidInventoryAmountException.class)
    public void addInventoryItemInvalidAmount() {
        // set number available to a negative number
        testItem.setNumberAvailable(-1);
        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test(expected = InvalidInventoryPriceException.class)
    public void addInventoryItemInvalidPriceNegative() {
        // set price to a negative number
        testItem.setPrice(-0.01);
        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test(expected = InvalidInventoryPriceException.class)
    public void addInventoryItemInvalidPriceTooManyDecimals() {
        // set price to a double with more than 2 (significant) decimal places
        testItem.setPrice(0.011);
        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test(expected = InventoryItemSkuNotUnique.class)
    public void addInventoryItemSkuNotUnique() {
        List<String> skus = new ArrayList<>();
        skus.add("12345678");

        when(mockInventoryDao.getAllSkus()).thenReturn(skus);

        classUnderTest.addInventoryItem(testItem);
    }

    ;

    @Test
    public void getAllInventoryItems() {
        List<Inventory> expected = new ArrayList<>();
        expected.add(testItem);
        when(mockInventoryDao.getAllInventoryItems()).thenReturn(expected);
        List<Inventory> result = classUnderTest.getAllInventoryItems();
        assertEquals(result, expected);
    }

    ;

    @Test
    public void getInventoryItemByIdHappy() {
        when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        Inventory result = classUnderTest.getInventoryItemById(testItem.getInventoryId());
        assertEquals(result, testItem);
    }

    ;

    @Test(expected = InventoryItemNotFound.class)
    public void getInventoryItemByIdSad() {
        // set an id that we don't have in the system
        int id = 2;
        classUnderTest.getInventoryItemById(id);
    }

    ;

    @Test
    public void getInventoryItemByNameHappy() {
        when(mockInventoryDao.getInventoryItemByName(testItem.getName())).thenReturn(testItem);
        Inventory result = classUnderTest.getInventoryItemByName(testItem.getName());
        assertEquals(result, testItem);
    }

    ;

    @Test(expected = InventoryItemNotFound.class)
    public void getInventoryItemByNameSad() {
        // change the name "Joe"
        String name = "Joe";
        classUnderTest.getInventoryItemByName(name);
    }

    ;

    @Test
    public void getInventoryItemsByTypeHappy() {
        List<Inventory> expected = new ArrayList<>();
        expected.add(testItem);
        when(mockInventoryDao.getInventoryItemsByType("Food")).thenReturn(expected);
        List<Inventory> result = classUnderTest.getInventoryItemsByType("Food");
        assertEquals(result, expected);
    }

    ;

    @Test(expected = InventoryItemNotFound.class)
    public void getInventoryItemsByTypeSad() {
        // change type to another type
        testItem.setItemType(type2);
        List<Inventory> emptyList = new ArrayList<>();
        when(mockInventoryDao.getInventoryItemsByType("Accessories")).thenReturn(emptyList);
        classUnderTest.getInventoryItemsByType("Accessories");
    }

    ;

    @Test
    public void getInventoryItemBySkuHappy() {
        when(mockInventoryDao.getInventoryItemBySku(testItem.getSku())).thenReturn(testItem);
        Inventory result = classUnderTest.getInventoryItemBySku(testItem.getSku());
        assertEquals(result, testItem);
    }

    ;

    @Test(expected = InventoryItemNotFound.class)
    public void getInventoryItemBySkuSad() {
        // change sku
        String sku = "abc45678";
        classUnderTest.getInventoryItemBySku(sku);
    }

    ;

    @Test
    public void updateInventoryItemHappy() {
        when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        when(mockInventoryDao.updateInventoryItem(1, testItem2)).thenReturn(testItem2);
        Inventory result = classUnderTest.updateInventoryItem(1, testItem2);
        assertNotEquals(result, testItem);
    }

    ;

    @Test(expected = InventoryItemSkuCannotBeUpdated.class)
    public void updateInventoryItemCannotUpdateSku() {
        // change the sku to something other than original "12345678"
        testItem2.setSku("abc45678");
        when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        when(mockInventoryDao.updateInventoryItem(1, testItem2)).thenReturn(testItem2);
        classUnderTest.updateInventoryItem(1, testItem2);
    }

    ;

    @Test
    public void zdeleteInventoryItemHappy() {
        when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        classUnderTest.deleteInventoryItem(1);
    }

    ;

    @Test(expected = InventoryItemNotFound.class)
    public void zdeleteInventoryItemNotFound() {
        //set id to something not in the system
        int id = 2;

        classUnderTest.deleteInventoryItem(1);
    }

    ;


    @Test(expected = InventoryItemCannotBeDeleted.class)
    public void zdeleteInventoryItemCannotDelete() {
        //add the test item to a shopping cart and that cart to an order
        ShoppingCart cart = new ShoppingCart();
        cart.setItemOrder(testItem);
        List<ShoppingCart> carts = new ArrayList<>();
        carts.add(cart);

        when(mockInventoryDao.getInventoryItemById(1)).thenReturn(testItem);
        when(mockOrderDao.getAllShoppingCarts()).thenReturn(carts);


        classUnderTest.deleteInventoryItem(1);
    }


}
