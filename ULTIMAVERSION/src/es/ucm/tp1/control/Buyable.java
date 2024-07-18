package es.ucm.tp1.control;

import es.ucm.tp1.control.excepciones.NotEnoughCoinsException;
import es.ucm.tp1.logic.Game;

public interface Buyable {
	public int cost();
	
	public default boolean buy(Game game) throws NotEnoughCoinsException{
		if(game.enoughcoinsplayer(cost())) {
			return true;
		}
		else {
			throw new NotEnoughCoinsException("Not enough coins");
		}
		
		
	}
}
