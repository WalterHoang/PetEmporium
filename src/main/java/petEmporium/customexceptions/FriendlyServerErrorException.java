package petEmporium.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unexpected Server error, please try again later or contact the administrator of this api.")
public class FriendlyServerErrorException extends RuntimeException {
}
