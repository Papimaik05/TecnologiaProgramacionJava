package es.ucm.tp1.logic.gameobjects;

import es.ucm.tp1.logic.Game;

public class Truck extends GameObject {
	private static int totalobstacles=0;
	public Truck(Game game, int x, int y) {
		super(game, x, y,1);
		symbol="←";
	}
	@Override
	public boolean receiveCollision(Player player) {
		if(isAlive()) {
			player.minushp();
		}
		return true;
	}
	
	@Override
	public void update() {
		x--;
	}
	
	@Override
	public void onEnter() {
		totalobstacles++;

	}
	@Override
	public void onDelete() {
		totalobstacles--;

	}
	public static  void reset() {
		totalobstacles=0;
		
	}
	public static int getobstacles() {
		return totalobstacles;
	}
	
	@Override
	public boolean receiveShoot() {
		hp--;
		return true;
		
	}
	@Override
	public boolean receiveExplosion() {
		hp=0;
		return true;
	}
	@Override
	public boolean receiveThunder() {
		hp=0;
		System.out.print("-> "+ symbol );
		return true;
	}
	@Override
	public boolean receiveWave() {
		  x=x+1;
		     return true;
	}

}
