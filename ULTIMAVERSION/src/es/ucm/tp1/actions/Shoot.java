package es.ucm.tp1.actions;

import es.ucm.tp1.logic.Collider;
import es.ucm.tp1.logic.Game;


public class Shoot implements InstantAction{	
	public Shoot() {
		
	}
	@Override
	public void execute(Game game) {
		boolean fin=false;
		for(int i=game.posplayer();i<=game.posplayer()+game.getVisibility()-1 && !fin ;i++) {
			Collider other = game.getObjectInPosition(i,game.rowplayer());
			if (other != null) {
			       fin=other.receiveShoot();
				}
			  }
		
	}	
			


	

}
