package petEmporium.validators;

import org.junit.Test;

import static org.junit.Assert.*;

public class CommonValidationTest {
    private CommonValidation cv = new CommonValidation();
    private boolean expected;
    private boolean result;

    @Test
    public void checkEmptyString() {
        expected = true;
        result = cv.checkEmptyString("");
        assertSame(expected, result);
        expected = false;
        result = cv.checkEmptyString("Test string");
        assertSame(expected, result);
    }

    @Test
    public void checkValidPrice() {
        expected = true;
        result = cv.checkValidPrice(121.45);
        assertSame(expected, result);
        expected = false;
        result = cv.checkValidPrice(-12.43);
        assertSame(expected, result);
    }
}