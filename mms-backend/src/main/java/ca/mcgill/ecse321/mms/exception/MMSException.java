package ca.mcgill.ecse321.mms.exception;

import org.springframework.http.HttpStatus;

@SuppressWarnings("serial")
public class MMSException extends RuntimeException  {
    
    private HttpStatus status;
	
	public MMSException(HttpStatus status, String message) {
		super(message);
		this.status = status;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
    
    }


