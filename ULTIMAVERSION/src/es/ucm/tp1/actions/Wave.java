package es.ucm.tp1.actions;

import es.ucm.tp1.logic.Game;

public class Wave implements InstantAction{
	@Override
	public void execute(Game game) {
			for(int x=game.posplayer() + game.getVisibility()-1;x>=game.posplayer();x--) {
				for(int y=0;y<=game.getlevelrow();y++) {
					if(game.getObjectInPosition(x, y)!=null) {
						if(game.getObjectInPosition(x+1, y)==null) {
							game.getObjectInPosition(x, y).receiveWave();
						}
					}
				}
			}
	}
}
