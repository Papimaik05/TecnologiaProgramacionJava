package es.ucm.tp1.logic.gameobjects;

import es.ucm.tp1.logic.Game;

public class SuperCoin extends GameObject {

	private static boolean isSuperCoin = false;
    private int value=1000;
	private static int totalmoney=0;
	public SuperCoin(Game game, int x, int y) {
		super(game, x, y,1);
		symbol="$";
	}
	@Override
	public boolean receiveCollision(Player player) {
		player.plusmoney(value);
		hp--;
		return true;
	}
	@Override
	public void onEnter() {
		isSuperCoin=true;
		totalmoney++;
	}

	@Override
	public void update() {
			
	}

	@Override
	public void onDelete() {
		isSuperCoin=false;
		totalmoney--;
	}
	public static void reset() {
		totalmoney=0;
		isSuperCoin=false;
	}
	public static int  getmoney() {
		return totalmoney;
	}
	public static boolean hasSuperCoin() {
		return isSuperCoin;
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
