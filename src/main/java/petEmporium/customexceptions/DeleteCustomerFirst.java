package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Must delete customer before you can delete this entity.")
public class DeleteCustomerFirst extends RuntimeException {
}
