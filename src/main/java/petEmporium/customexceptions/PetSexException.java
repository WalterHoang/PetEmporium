package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "A pet's sex must be entered as 'M' or 'F'.")
public class PetSexException extends RuntimeException {
}
