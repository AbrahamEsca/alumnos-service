package mx.com.alumnos.exception;

public class RecordAlreadyExistsException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public RecordAlreadyExistsException(String message) {
        super(message);
    }
    
}
