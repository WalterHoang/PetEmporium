package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The Pet Adoption fee must be greater than zero!")
public class PetAdoptionFeeException extends RuntimeException {
}
