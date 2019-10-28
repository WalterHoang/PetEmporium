package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "The number of this item available must be a non-negative number!")
public class InvalidInventoryAmountException extends RuntimeException {
}
