package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The current accepted pet types are 'Dog', 'Cat', and 'Bird'.")
public class PetTypeException extends RuntimeException {
}
