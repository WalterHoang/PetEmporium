package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "The inventory item or items you are looking for cannot be found.")
public class InventoryItemNotFound extends RuntimeException {
}

