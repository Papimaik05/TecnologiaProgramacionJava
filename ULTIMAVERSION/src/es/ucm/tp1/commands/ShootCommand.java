package es.ucm.tp1.commands;

import es.ucm.tp1.actions.InstantAction;
import es.ucm.tp1.actions.Shoot;
import es.ucm.tp1.control.Buyable;
import es.ucm.tp1.control.excepciones.CommandExecuteException;
import es.ucm.tp1.control.excepciones.NotEnoughCoinsException;
import es.ucm.tp1.logic.Game;

public class ShootCommand extends Command implements Buyable {
     
	private static final String NAME = "shot";

	private static final String DETAILS = "[s]hoot";

	private static final String SHORTCUT = "s";

	private static final String HELP = "shoot bullet";
	


	public ShootCommand() {
		super(NAME, SHORTCUT, DETAILS, HELP);
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		try {
			
			if(buy(game)) {
				InstantAction IA=new Shoot();
				game.executeIA(IA);
				return true;
			}
			return false;
		}
		catch(NotEnoughCoinsException e) {
			System.out.println(e.getMessage());
			throw new CommandExecuteException(String.format("[ERROR]: %s", FAILED_MSG + HELP) );
			
		}	
	}

	@Override
	public int cost() {
		return 1;
	}
}
