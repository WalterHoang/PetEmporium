package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "An inventory item's price must be non-negative and precise to two decimal places!")
public class InvalidInventoryPriceException extends RuntimeException {
}
