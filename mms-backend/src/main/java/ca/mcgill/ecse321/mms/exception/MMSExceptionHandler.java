package ca.mcgill.ecse321.mms.exception;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MMSExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MMSException.class)
	public ResponseEntity<String> handleMMSException(MMSException exception) {
		return new ResponseEntity<String>(exception.getMessage(), exception.getStatus());
	}
    
}


