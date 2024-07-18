package es.ucm.tp1.control.excepciones;

public class NotEnoughCoinsException extends CommandExecuteException {
	
	private static final long serialVersionUID = 1L;
	public NotEnoughCoinsException(String message) {
		super(message);
	}


}
