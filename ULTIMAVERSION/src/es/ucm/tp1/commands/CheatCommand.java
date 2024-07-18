package es.ucm.tp1.commands;

import es.ucm.tp1.control.excepciones.CommandParseException;
import es.ucm.tp1.logic.Game;


public class CheatCommand extends Command {

	private static final String NAME = "[1-5]";

	private static final String DETAILS = "Cheat [1..5]";

	private static final String SHORTCUT = "add";

	private static final String HELP = "Removes all elements of last visible column, and adds an Advanced Object";
	
    private int number;

	public CheatCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) {
		game.changeavailable(false);
		game.addAvancedObject(number);
		
		return true;
	}
	protected Command parse(String[] commandWords) throws CommandParseException {
		if(commandWords[0].matches(NAME)) {
			if(commandWords.length!=1) {
				throw new  CommandParseException(INCORRECT_NUMBER_OF_ARGS_MSG);
			}
			else {
				number=Integer.parseInt(commandWords[0]);
				return this;
			}
			
			}
			return null;
			}
	}


