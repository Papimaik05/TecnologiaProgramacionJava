package es.ucm.tp1.commands;

import es.ucm.tp1.control.Buyable;
import es.ucm.tp1.control.excepciones.CommandExecuteException;
import es.ucm.tp1.control.excepciones.CommandParseException;
import es.ucm.tp1.control.excepciones.InvalidPositionException;
import es.ucm.tp1.logic.Game;
import es.ucm.tp1.logic.gameobjects.Grenade;

public class GrenadeCommand extends Command implements Buyable {

	private static final String NAME = "grenade";

	private static final String DETAILS = "[g]renade <x> <y> ";

	private static final String SHORTCUT = "g";

	private static final String HELP = "add a grenade in position x, y";
	
    private int x;
    private int y;

	public GrenadeCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}
	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		try {
			if (0<=x && x<=game.getVisibility() && 0<=y && y<=game.getlevelrow() && game.getObjectInPosition(x+game.posplayer(), y)==null) {
						
				if(buy(game)) {
					game.forceAddObject(new Grenade(game,game.posplayer()+x,y));
					return true;
				}
				return false;		
			}
			else {
				throw new InvalidPositionException(POSITION_MSG);
			}	
			}
		catch (CommandExecuteException c) {
			System.out.println(c.getMessage());
			 throw new CommandExecuteException(String.format("[ERROR]: %s", FAILED_MSG + HELP) );
		}
	}
	
	protected Command parse(String[] commandWords) throws CommandParseException {
		if(matchCommandName(commandWords[0])) {
			if(commandWords.length==3) {
				try {
						x=Integer.parseInt(commandWords[1]);
						y =Integer.parseInt(commandWords[2]);
						return this;
				}catch(NumberFormatException n ) {
					System.out.println(String.format(PARAMETER_FAIL));
				    throw new CommandParseException(String.format("[ERROR]: %s", FAILED_MSG + HELP) );
				}
				
			}
			else {
				throw new CommandParseException(String.format(INCORRECT_NUMBER_OF_ARGS_MSG));
			}
		}
		return null;
		}
	@Override
	public int cost() {
		return 3;
	}

}
