package petEmporium.validators;

import org.junit.Before;
import org.junit.Test;

import petEmporium.entities.ItemType;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class InventoryValidatorTest {

    InventoryValidator inventoryValidator = new InventoryValidator();

    @Before
    public void init() {
    }


    @Test
    public void checkValidNameHappy() {
        String name = "name";
        boolean actual = inventoryValidator.checkValidName(name);
        assertTrue(actual);
    }

    @Test
    public void checkValidNameSadTooShort() {
        String name = "1";
        boolean actual = inventoryValidator.checkValidName(name);
        assertFalse(actual);
    }

    @Test
    public void checkValidNameSadTooLong() {
        String name = "svy4WoyXZRomtJyZsoBkzTlv18Qff11asVVqJLATcyo2DtqcyNSvXEnP4uceLnYi9aIprfiqRTA8x5EB34GLO3fLTvyvJX0z7I6i32";
        boolean actual = inventoryValidator.checkValidName(name);
        assertFalse(actual);
    }

    @Test
    public void checkValidTypeHappy() {
        //set type to one of: Toys, Food, or Accessories
        String type = "Food";
        ItemType itemType = new ItemType(type);
        boolean actual = inventoryValidator.checkValidType(itemType);
        assertTrue(actual);
    }

    @Test
    public void checkValidTypeSad() {
        //set type to anything BUT :  Toys, Food, or Accessories
        String type = "treats";
        ItemType itemType = new ItemType(type);
        boolean actual = inventoryValidator.checkValidType(itemType);
        assertFalse(actual);
    }

    @Test
    public void checkValidSkuHappy() {
        // enter a sku string that is 8 alphanumeric characters
        String sku = "1abc5678";
        boolean actual = inventoryValidator.checkValidSku(sku);
        assertTrue(actual);
    }

    @Test
    public void checkValidSkuSad() {
        // set a sku string that is NOT 8 alphanumeric characters
        String sku = "1234567*";
        boolean actual = inventoryValidator.checkValidSku(sku);
        assertFalse(actual);
    }

    @Test
    public void checkValidNumberAvailableHappy() {
        // set any non-negative integer
        Integer num = 0;
        boolean actual = inventoryValidator.checkValidNumberAvailable(num);
        assertTrue(actual);
    }

    @Test
    public void checkValidNumberAvailableSad() {
        // set any negative integer
        Integer num = -1;
        boolean actual = inventoryValidator.checkValidNumberAvailable(num);
        assertFalse(actual);
    }

    @Test
    public void checkValidPriceHappy() {
        // set any non-negative price with at most 2 (significant) decimal places
        double price = 0.10;
        boolean actual = inventoryValidator.checkValidPrice(price);
        assertTrue(actual);
    }

    @Test
    public void checkValidPriceSadNegative() {
        // set any price which is negative
        double price = -0.01;
        boolean actual = inventoryValidator.checkValidPrice(price);
        assertFalse(actual);
    }

    @Test
    public void checkValidPriceSadDecimals() {
        // set any price which has more that 2 decimal places
        double price = 0.011;
        boolean actual = inventoryValidator.checkValidPrice(price);
        assertFalse(actual);
    }
}
