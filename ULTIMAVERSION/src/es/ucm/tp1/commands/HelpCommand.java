package es.ucm.tp1.commands;

import es.ucm.tp1.logic.Game;


public class HelpCommand extends Command {

	private static final String NAME = "help";

	private static final String DETAILS = "[h]elp";

	private static final String SHORTCUT = "h";

	private static final String HELP = "show this help";

	public HelpCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) {
		StringBuilder buffer = new StringBuilder("Available commands:\n");
		for(Command c : Command.getAvailableCommands()) {
			buffer.append(c.printHelp());
			buffer.append("\n");
		}
		
		System.out.print(buffer.toString());
		return false;

	}

	

}
