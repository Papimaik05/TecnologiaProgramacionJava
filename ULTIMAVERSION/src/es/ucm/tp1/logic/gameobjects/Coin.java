package es.ucm.tp1.logic.gameobjects;

import es.ucm.tp1.logic.Game;

public class Coin extends GameObject {
	
	private static int totalmoney=0;
	private int value=1;
	public Coin(Game game, int x, int y) {
		super(game, x, y,1);
		symbol="¢";
	}
	@Override
	public boolean receiveCollision(Player player) {
		player.plusmoney(value);
		hp--;
		return true;
	}

	@Override
	public void onEnter() {
		totalmoney++;
	}

	@Override
	public void update() {
	
	}

	@Override
	public void onDelete() {
	   totalmoney--;
		
	}

	public static void reset() {
		totalmoney=0;
		
	}
	public static int  getmoney() {
		return totalmoney;
	}

	@Override
	public boolean receiveShoot() {
		return false;
	}
	@Override
	public boolean receiveExplosion() {
		return false;
	}
	@Override
	public boolean receiveThunder() {
		return false;
	}
	@Override
	public boolean receiveWave() {
		  x=x+1;
		  return true;
	}

}
