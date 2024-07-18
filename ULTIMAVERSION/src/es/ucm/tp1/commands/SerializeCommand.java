package es.ucm.tp1.commands;

import es.ucm.tp1.control.excepciones.CommandExecuteException;
import es.ucm.tp1.logic.Game;
import es.ucm.tp1.view.GameSerializer;

public class SerializeCommand extends Command {
	
	private static final String NAME = "serialize";

	private static final String DETAILS = "[s]erialize";

	private static final String SHORTCUT = "z";

	private static final String HELP = "show all the objects";

	public SerializeCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		GameSerializer.printSerializer(game);
		return false;
	}

}
