package es.ucm.tp1.actions;

import es.ucm.tp1.logic.Collider;
import es.ucm.tp1.logic.Game;

public class Thunder implements InstantAction {
	public Thunder() {
	}

	@Override
	public void execute(Game game) {
		int x=game.getRandomColumn();
		int y=game.getRandomLane();
		Collider other = game.getObjectInPosition(x+game.posplayer(),y);
		System.out.print("Thunder hit position: (" + x + ","+y+") ");
		if (other != null) {
	        other.receiveThunder();
		 }
		System.out.println();
		
	}
}
