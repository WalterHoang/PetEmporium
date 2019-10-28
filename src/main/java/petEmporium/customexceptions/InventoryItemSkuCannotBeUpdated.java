package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The SKU of an inventory item cannot be updated.")
public class InventoryItemSkuCannotBeUpdated extends RuntimeException {
}
