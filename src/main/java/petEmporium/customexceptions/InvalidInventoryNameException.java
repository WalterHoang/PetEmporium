package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Inventory Item Name must be between 3 and 100 characters!")
public class InvalidInventoryNameException extends RuntimeException {
}
