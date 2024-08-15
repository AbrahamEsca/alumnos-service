package mx.com.alumnos.exception;

import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
		LOGGER.info("Los valores de entrada no cumple con las validaciones establecidas: ", ex.getMessage());
		
        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(violation -> {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            errors.put(fieldName, errorMessage);
        });
        
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
		LOGGER.info("Los valores de entrada no cumple con las validaciones establecidas: ", ex.getMessage());
		
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		LOGGER.info("Los valores de entrada no cumple con las validaciones establecidas: ", ex.getMessage());
		
        Map<String, String> response = new HashMap<>();
        response.put("error", "El valor del parametro es invalido");
        response.put("message", "El parametro '" + ex.getName() + "' deberia ser numerico");
        
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleEntityNotFoundException(EntityNotFoundException ex) {
		LOGGER.info("No se encontro registros con los valores de busqueda: ", ex.getMessage());
		
		Map<String, String> errors = new HashMap<>();
		errors.put("error", ex.getMessage());
		
		return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(RecordAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleRecordAlreadyExistsException(RecordAlreadyExistsException ex) {
		LOGGER.info("Ya existen registros con las datos de entrada: ", ex.getMessage());
		
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getMessage());
        
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleAllExceptions(Exception ex) {
		LOGGER.info("Ocurrio un error interno en el servidor: ", ex.getMessage(), ex);
		
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("error", "Ha ocurrido un error interno en el servidor");
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.INTERNAL_SERVER_ERROR);
    }
	
}
