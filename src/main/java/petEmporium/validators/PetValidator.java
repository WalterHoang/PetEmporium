package petEmporium.validators;

import org.springframework.stereotype.Component;

@Component
public class PetValidator {
    /**
     * This method checks to see if the inputted pet sex is 'M' or 'F'
     *
     * @param petSex
     * @return
     */
    public boolean checkSex(String petSex) {
        return (petSex.equals("M") || petSex.equals("F"));
    }

    public boolean checkType(String petType) {
        return (petType.equals("Dog") || petType.equals("Cat") || petType.equals("Bird"));
    }
}
