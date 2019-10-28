package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "You cannot delete a pet that has been assigned to an order")
public class CannotDeletePet extends RuntimeException {
}
