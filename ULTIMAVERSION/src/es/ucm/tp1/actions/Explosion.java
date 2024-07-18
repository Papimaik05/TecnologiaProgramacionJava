package es.ucm.tp1.actions;

import es.ucm.tp1.logic.Collider;
import es.ucm.tp1.logic.Game;

public class Explosion implements InstantAction{
	private int x;
	private int y;
	
    public Explosion(int x , int y) {
    	this.x=x;
    	this.y=y;
    }
	@Override
	public void execute(Game game) {	
		for(int i=x-1;i<= x+1 ;i++) {
			for(int j=y-1;j<=y+1;j++ ) {
				Collider other = game.getObjectInPosition(i,j);
				if (other != null) {
			        other.receiveExplosion();
				 }
			   }	
	        }
	}


}
