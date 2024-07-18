package es.ucm.tp1.control.excepciones;

public class NumberFormatException extends CommandParseException{
	private static final long serialVersionUID = 1L;
	public NumberFormatException(String message) {
		super(message);
	}


}
