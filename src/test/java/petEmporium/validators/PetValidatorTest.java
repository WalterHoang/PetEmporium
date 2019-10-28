package petEmporium.validators;

import org.junit.Before;
import org.junit.Test;
import petEmporium.entities.Pet;

import static org.junit.Assert.*;

public class PetValidatorTest {

    private PetValidator petValidator = new PetValidator();
    private CommonValidation commonValidation = new CommonValidation();
    private Pet happyPet1;
    private Pet happyPet2;
    private Pet sadPet1;
    private Pet sadPet2;
    private Pet sadPet3;
    boolean expected;
    boolean result;

    @Before
    public void init() {
        happyPet1 = new Pet(
                "Josephine",
                "Cat",
                "F",
                200.10,
                null,
                null,
                true
        );
        happyPet1.setPetId(1);
        happyPet2 = new Pet(
                "Manuel",
                "Bird",
                "M",
                150.55,
                "Green",
                "3 years 1 month",
                true
        );
        happyPet2.setPetId(2);
        sadPet1 = new Pet(
                "Joey",
                "Cat",
                "T",
                135.45,
                null,
                "1 year and 1 month",
                true
        );
        sadPet1.setPetId(3);
        sadPet2 = new Pet(
                "Joey",
                "Rhino",
                "M",
                135.45,
                null,
                "4 years and 1 month",
                true);
        sadPet2.setPetId(4);
        sadPet3 = new Pet(
                "Joey",
                "Dog",
                "M",
                -135.45,
                null,
                "4 years and 1 month",
                true
        );
        sadPet3.setPetId(5);
    }

    // These methods use the pet validator class
    @Test
    public void checkSex() {
        expected = true;
        result = petValidator.checkSex(happyPet1.getPetSex());
        assertEquals("Pet1 test: \n Expected: " + expected + " Got: " + result, expected, result);
        result = petValidator.checkSex(happyPet2.getPetSex());
        assertEquals("Pet2 test: \n Expected: " + expected + " Got: " + result, expected, result);
    }

    @Test
    public void checkInvalidSex() {
        expected = false;
        result = petValidator.checkSex(sadPet1.getPetSex());
        assertEquals("Sad test: \n Expected: " + expected + " Got: " + result, expected, result);
    }

    @Test
    public void checkType() {
        expected = true;
        result = petValidator.checkType(happyPet1.getPetType());
        assertEquals("Pet1 test: \n Expected: " + expected + " Got: " + result, expected, result);
        result = petValidator.checkType(happyPet2.getPetType());
        assertEquals("Pet2 test: \n Expected: " + expected + " Got: " + result, expected, result);
    }

    @Test
    public void checkInvalidType() {
        expected = false;
        result = petValidator.checkType(sadPet2.getPetType());
        assertEquals("Sad test: \n Expected: " + expected + " Got: " + result, expected, result);
    }

    // These methods use the common validator class
    @Test
    public void checkPetName() {
        expected = false;
        result = commonValidation.checkEmptyString(happyPet1.getPetName());
        assertEquals("Pet1 test: \n Expected: " + expected + " Got: " + result, expected, result);
    }

    @Test
    public void checkEmptyName() {
        expected = true;
        sadPet1.setPetName("");
        result = commonValidation.checkEmptyString(sadPet1.getPetName());
        assertEquals("Sad test: \n Expected: " + expected + " Got: " + result, expected, result);
    }

    @Test(expected = NullPointerException.class)
    public void checkVoidName() {
        sadPet1.setPetName(null);
        result = commonValidation.checkEmptyString(sadPet1.getPetName());
    }

    @Test
    public void checkPetAdoptionFee() {
        expected = true;
        result = commonValidation.checkValidPrice(happyPet1.getPetAdoptionFee());
        assertEquals("Pet1 test: \n Expected: " + expected + " Got: " + result, expected, result);
    }

    @Test
    public void checkNegativeFee() {
        expected = false;
        result = commonValidation.checkValidPrice(sadPet3.getPetAdoptionFee());
        assertEquals("Sad test: \n Expected: " + expected + " Got: " + result, expected, result);
    }
}