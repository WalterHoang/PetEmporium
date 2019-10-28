package petEmporium.validators;

import petEmporium.entities.ItemType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InventoryValidator {

    /**
     * checks to see if the given name is between 3 and 100 characters
     *
     * @param name
     * @return
     */
    public boolean checkValidName(String name) {
        int length = name.length();

        return length >= 3 && length <= 100;
    }


    /**
     * checks to see if the given type is in the allowed list
     *
     * @param itemType
     * @return
     */
    public boolean checkValidType(ItemType itemType) {
        // create a list of all valid item types
        List<String> validTypes = new ArrayList<>();
        validTypes.add("Food");
        validTypes.add("Toys");
        validTypes.add("Accessories");

        // get the item type of the provided itemType object
        String type = itemType.getItemType();

        return validTypes.contains(type);
    }


    /**
     * checks to see if the sku is precisely 8 alphanumeric characters
     *
     * @param sku
     * @return
     */
    public boolean checkValidSku(String sku) {
        Pattern validSku = Pattern.compile("^[a-zA-Z0-9]{8}$");
        Matcher check = validSku.matcher(sku);
        return check.matches();
    }


    /**
     * checks to see if the number available is not below 0
     *
     * @param numberAvailable
     * @return
     */
    public boolean checkValidNumberAvailable(Integer numberAvailable) {
        return numberAvailable >= 0;
    }

    /**
     * checks to see if the price is non-negative and to precisely 2 decimal places
     *
     * @param price
     * @return
     */
    public boolean checkValidPrice(Double price) {
        String toText = Double.toString(price);
        int decimalPlaces = toText.length() - toText.indexOf('.') - 1;
        return (decimalPlaces <= 2 && price >= 0);
    }

}
