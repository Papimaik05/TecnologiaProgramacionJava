package es.ucm.tp1.commands;

import es.ucm.tp1.control.excepciones.CommandExecuteException;
import es.ucm.tp1.logic.Game;
import es.ucm.tp1.view.GamePrinter;

public class ShowRecordCommand extends Command{

	private static final String NAME = "record";

	private static final String DETAILS = "[r]ecord";

	private static final String SHORTCUT = "o";

	private static final String HELP = "show record";

	public ShowRecordCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		GamePrinter.record(game);
		return false;
	}

}
