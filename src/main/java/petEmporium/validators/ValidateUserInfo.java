package petEmporium.validators;

import java.util.regex.Pattern;

import petEmporium.entities.Address;
import petEmporium.entities.PetUsers;

public class ValidateUserInfo {

    private CommonValidation comVal = new CommonValidation();


    private String[] usStateCodes = {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC",
            "FL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO",
            "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN",
            "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};


    public boolean PNumValidate(String PNum) {
        return !PNum.isBlank() && Pattern.matches("[0-9]{3}-[0-9]{3}-[0-9]{4}", PNum);
    }

    public boolean usCodeValidate(String usCode) {
        for (String usStateCodes : usStateCodes) {
            if (usCode.equals(usStateCodes)) {
                return true;
            }
        }
        return false;
    }

    public boolean zipCodeValidate(String zipCo) {
        return !zipCo.isBlank() && Pattern.matches("^[0-9]{5}(?:-[0-9]{4})?$", zipCo);
    }

    public boolean emailFormValidation(String emailInput) {
        return !emailInput.isBlank() && Pattern.matches("^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$", emailInput);
    }


    public boolean validateUser(PetUsers petUser) {
        return !comVal.checkEmptyString(petUser.getFirstName())
                && !comVal.checkEmptyString(petUser.getLastName())
                && emailFormValidation(petUser.getEmail().getUserEmail())
                && PNumValidate(petUser.getPhoneNumber());
    }

    public boolean validateAddress(Address address) {
        return !comVal.checkEmptyString(address.getStreetAddress())
                && !comVal.checkEmptyString(address.getCityAddress())
                && usCodeValidate(address.getStateCode())
                && zipCodeValidate(address.getZipCode());
    }


}
