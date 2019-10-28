package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Inventory item cannot be deleted since it was involved in a purchase.")
public class InventoryItemCannotBeDeleted extends RuntimeException {
}
