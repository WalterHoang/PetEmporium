package petEmporium.validators;

import org.junit.Test;

import static org.junit.Assert.*;

public class ValidateUserInfoTest {

    private ValidateUserInfo vui = new ValidateUserInfo();


    @Test
    public void PnumValidate() {
        assertTrue(vui.PNumValidate("303-555-4444"));
        assertFalse(vui.PNumValidate(""));
        assertFalse(vui.PNumValidate("1-222-3333"));
        assertFalse(vui.PNumValidate("111-22-2222"));
        assertFalse(vui.PNumValidate("111-222-111"));
    }

    @Test
    public void usCodeValidate() {
        assertTrue(vui.usCodeValidate("CO"));
        assertFalse(vui.usCodeValidate("AA"));
    }

    @Test
    public void zipCodeValidate() {
        assertTrue(vui.zipCodeValidate("80521"));
        assertTrue(vui.zipCodeValidate("80521-1000"));
        assertFalse(vui.zipCodeValidate("805211000"));
        assertFalse(vui.zipCodeValidate("8052"));
    }

    @Test
    public void emailFormValidation() {
        assertTrue(vui.emailFormValidation("darklord@horizon.net"));
        assertFalse(vui.emailFormValidation("darklord@horizon"));
        assertFalse(vui.emailFormValidation(" "));
    }
}