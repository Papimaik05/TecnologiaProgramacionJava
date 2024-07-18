package es.ucm.tp1.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import es.ucm.tp1.control.excepciones.CommandExecuteException;
import es.ucm.tp1.control.excepciones.CommandParseException;
import es.ucm.tp1.logic.Game;
import es.ucm.tp1.view.GameSerializer;

public class SaveCommand extends Command {
    private String fileName;
	private static final String NAME = "save";

	private static final String DETAILS = "save <filename>";

	private static final String SHORTCUT = "v";

	private static final String HELP = "save the game";

	public SaveCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		BufferedWriter archivo=null;
		try {
			archivo=new BufferedWriter(new FileWriter(fileName));
			archivo.write(WELCOME_MSG);
			archivo.write(GameSerializer.Serializermsg(game));
			archivo.close();
			System.out.println(SAVE_MSG + fileName+"\n");
		}
		catch(IOException i) {
			System.out.println(ERROR);
		}
		return false;
	}
	
	protected Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length!=2) {
				throw new CommandParseException(String.format(INCORRECT_NUMBER_OF_ARGS_MSG));
			}
			else {
				fileName=commandWords[1]+".txt";
				return this;
			}
		}
		return null;
	}
}