package petEmporium.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidation {

    public boolean checkEmptyString(String field) {

        return field.isEmpty();
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
