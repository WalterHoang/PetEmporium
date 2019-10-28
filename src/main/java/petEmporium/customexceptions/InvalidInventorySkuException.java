package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Inventory item SKU must be 8 alphanumeric characters!")
public class InvalidInventorySkuException extends RuntimeException {
}
