package es.ucm.tp1.control.excepciones;

public abstract class GameException extends Exception {
	
    private static final long serialVersionUID = 1L;

    public GameException() { 
        super(); 
    }

    public GameException(String message){ 
        super(message); 
    }
    public GameException(String message, Throwable cause){
        super(message, cause);
    }
    public GameException(Throwable cause){ 
        super(cause); 
    }
    


}