package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The SKU entered is already listed in the system.")
public class InventoryItemSkuNotUnique extends RuntimeException {
}
