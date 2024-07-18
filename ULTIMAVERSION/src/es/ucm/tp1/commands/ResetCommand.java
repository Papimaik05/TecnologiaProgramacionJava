package es.ucm.tp1.commands;

import es.ucm.tp1.control.Level;
import es.ucm.tp1.control.excepciones.CommandParseException;
import es.ucm.tp1.control.excepciones.InputOutputRecordException;
import es.ucm.tp1.logic.Game;

public class ResetCommand extends Command {

	private static final String NAME = "reset";

	private static final String DETAILS = "[r]eset  [<level> <seed>]";

	private static final String SHORTCUT = "r";

	private static final String HELP = "reset game";

    private long seed;
    private Level level;
	public ResetCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) throws InputOutputRecordException {
		if(level!=null) {
			game.newreset(level,seed);
		}
		if(level==null) {
			game.reset();
		}
		return true;
	}
	
	protected Command parse(String[] commandWords) throws CommandParseException {
		try {
			if(matchCommandName(commandWords[0]) && ((commandWords.length==2)||(commandWords.length>3))) {
				throw new CommandParseException(String.format("[ERROR]:Command %s: %s", NAME,INCORRECT_NUMBER_OF_ARGS_MSG));
			}
			else if(matchCommandName(commandWords[0]) && commandWords.length>1) {
					if(commandWords[1].equalsIgnoreCase("easy") || commandWords[1].equalsIgnoreCase("hard") || commandWords[1].equalsIgnoreCase("advanced")|| commandWords[1].equalsIgnoreCase("test")) {
						level=Level.valueOfIgnoreCase(commandWords[1]);
					}	
					else {
						throw new NumberFormatException(String.format("[ERROR]:Command %s: %s", NAME,TYPE_FAIL));
					}
				try {
					seed =Long.parseLong(commandWords[2]);
					return this;
				}
				catch(NumberFormatException n) {
					System.out.println(String.format(PARAMETER_FAIL));
				    throw new CommandParseException(String.format("[ERROR]: %s", FAILED_MSG + HELP));
				}
			}
			else if(matchCommandName(commandWords[0]) && commandWords.length<2) {
				return this;
			}
		}
		
		catch(NumberFormatException n){
			throw new CommandParseException(n.getMessage());
		}
			
		return null;
	}
}
