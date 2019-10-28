package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Item type must be one of: Food, Toys, or Accessories")
public class InvalidInventoryItemTypeException extends RuntimeException {
}
