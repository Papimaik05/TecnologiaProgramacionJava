package es.ucm.tp1.commands;

import es.ucm.tp1.control.excepciones.CommandExecuteException;
import es.ucm.tp1.control.excepciones.CommandParseException;
import es.ucm.tp1.logic.Game;

public abstract class Command {

	private static final String UNKNOWN_COMMAND_MSG = "Unknown command";
	protected static final String INCORRECT_NUMBER_OF_ARGS_MSG = "Incorrect number of arguments";
	protected static final String PARAMETER_FAIL = " Incorrect type of parameter";
	protected static final String TYPE_FAIL = "Must be one of: TEST, EASY, HARD, ADVANCED";
	protected static final String FAILED_MSG = "Failed to ";
	protected static final String POSITION_MSG = "Invalid position ";
	protected static final String COIN_MSG = "Not enough coins ";
	protected static final String ERROR = "Failing to save";
	protected static final String SAVE_MSG = "Game successfully saved in file";
	protected static final String VERSION = "3.0";
	protected static final String WELCOME_MSG = String.format("Super cars %s\n\n", VERSION);
	
	
	
	/* @formatter:off */
	protected static final Command[] AVAILABLE_COMMANDS = {
		new HelpCommand(),
		new InfoCommand(),
		new GrenadeCommand(),
		new WaveCommand(),
		new UpdateCommand(),
		new ExitCommand(),
		new GodownCommand(),
		new GoupCommand(),
		new TestCommand(),
		new ResetCommand(),
		new ShootCommand(),
		new ClearCommand(),
		new CheatCommand(),
		new SerializeCommand(),
		new SaveCommand(),
		new DumpCommand(),
		new ShowRecordCommand(),
	};
	/* @formatter:on */
	public static Command getCommand(String[] commandWords) throws CommandParseException {
		Command command = null;
		for(int i=0;i<AVAILABLE_COMMANDS.length && command==null;i++) {
			command=AVAILABLE_COMMANDS[i].parse(commandWords);
		}
		if(command == null) {
			throw new CommandParseException(String.format("[ERROR]: %s", UNKNOWN_COMMAND_MSG));
		}
		return command;
	}

	private final String name;

	private final String shortcut;

	private final String details;

	private final String help;

	public Command(String name, String shortcut, String details, String help) {
		this.name = name;
		this.shortcut = shortcut;
		this.details = details;
		this.help = help;
	}

	public abstract boolean execute(Game game) throws CommandExecuteException;

	protected boolean matchCommandName(String name) {
		return this.shortcut.equalsIgnoreCase(name) || this.name.equalsIgnoreCase(name);
	}

	protected Command parse(String[] words) throws CommandParseException {
		
		if (matchCommandName(words[0])) {
			if (words.length != 1) {
				throw new CommandParseException(String.format("[ERROR]:Command %s: %s", name,INCORRECT_NUMBER_OF_ARGS_MSG));
			} else {
				return this;
			}
			}
		return null;
		}


	protected  String printHelp() {
		return details + ": "+ help;
	}

	protected static  Command[] getAvailableCommands() {
		return AVAILABLE_COMMANDS;
	}
    
	

}
