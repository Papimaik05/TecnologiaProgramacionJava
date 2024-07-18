package es.ucm.tp1.control;

import java.util.Scanner;

import es.ucm.tp1.commands.Command;
import es.ucm.tp1.control.excepciones.CommandExecuteException;
import es.ucm.tp1.control.excepciones.GameException;
import es.ucm.tp1.logic.Game;
import es.ucm.tp1.view.GamePrinter;

public class Controller {

	private static final String PROMPT = "Command > ";

	private static final String UNKNOWN_COMMAND_MSG = "Unknown command";
	/* @formatter:off */
	private static final String[] HELP = new String[] {
		"Available commands:",
		"[h]elp: show this help",
		"[i]nfo: prints gameobjet info",
		"[n]one | []: update",
		"[q]: go up",
		"[a]: go down",
		"[e]xit: exit game",
		"[r]eset: reset game",
		"[t]est: enables test mode",	
	};
	/* @formatter:off */
	private Game game;
	private Scanner scanner2;
	private GamePrinter printer;
	

	public Controller(Game game, Scanner scanner) {
		this.game = game;
		scanner2 = scanner;
		this.printer = new GamePrinter(game);
	}
	
	public void printGame() {
		System.out.println(printer);
	}
	

	public void run() throws GameException {
		boolean paint=true;
		boolean firsttime=true;
		scanner2=new Scanner(System.in);
		game.addthings();
		printGame();
		while(!game.isFinished()) {
			paint=false;
			System.out.println(PROMPT);
			String s=scanner2.nextLine();
			String[] parameters=s.toLowerCase().trim().split(" ");
			System.out.println("[DEBUG] Executing : "+ s);	
				try {
					Command command = Command.getCommand(parameters);
					paint = command.execute(game);
					}
					catch (GameException ex) {
						System.out.format(ex.getMessage() + " %n %n");
					}
				if(paint==true) {
					game.collision();
					if(firsttime) {
						game.valuefirsttime();
					}
					firsttime=false;
					game.update();
					printGame();
				}
			}	
  }
}
