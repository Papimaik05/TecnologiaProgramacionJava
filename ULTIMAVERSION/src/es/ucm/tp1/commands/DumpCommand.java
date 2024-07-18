package es.ucm.tp1.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import es.ucm.tp1.control.excepciones.CommandExecuteException;
import es.ucm.tp1.control.excepciones.CommandParseException;
import es.ucm.tp1.logic.Game;
import es.ucm.tp1.view.GameSerializer;

public class DumpCommand extends Command{

	private String fileName;
	private static final String NAME = "dump";

	private static final String DETAILS = "dump <filename>";

	private static final String SHORTCUT = "d";

	private static final String HELP = "read the game";

	public DumpCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		BufferedReader archivo=null;
		String rd="";
		try {
			archivo=new BufferedReader(new FileReader(fileName));
			rd=archivo.readLine();
			while(rd!=null) {
				GameSerializer.dump(rd);
				rd=archivo.readLine();
			}
			
			archivo.close();
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
