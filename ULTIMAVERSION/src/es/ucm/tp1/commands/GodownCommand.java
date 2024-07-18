package es.ucm.tp1.commands;

import es.ucm.tp1.logic.Game;

public class GodownCommand extends Command {

	private static final String NAME = "go down";

	private static final String DETAILS = "[a]";

	private static final String SHORTCUT = "a";

	private static final String HELP = "go down player";

	public GodownCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) {
		game.godowng();
		return true;
	}

}
