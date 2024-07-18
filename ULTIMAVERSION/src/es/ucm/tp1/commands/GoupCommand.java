package es.ucm.tp1.commands;

import es.ucm.tp1.logic.Game;

public class GoupCommand extends Command {

	private static final String NAME = "go up";

	private static final String DETAILS = "[q]";

	private static final String SHORTCUT = "q";

	private static final String HELP = "go up player";

	public GoupCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) {
		game.goupg();
		return true;
	}

}
