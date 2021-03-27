package up.positivo.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class CustomErrors {
	
	/*
	 * Spring validations error
	 * 
	 * Doc: https://www.baeldung.com/spring-boot-bean-validation
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});

		return errors;
	}
	
	/*
	 * My errors
	 */
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> ResponseEntity<T> singleErrorException(String key, String value) {
		Map<String, String> errors = new HashMap<String, String>();
		errors.put(key, value);

		return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <T> ResponseEntity<T> singleErrorException(Map<String, String> errors) {
		return new ResponseEntity(errors, HttpStatus.BAD_REQUEST);
	}
}
