package es.ucm.tp1.control.excepciones;

public class CommandExecuteException extends GameException{
	private static final long serialVersionUID = 1L;
	
	public CommandExecuteException(String message) {
		super(message);
	}
}
