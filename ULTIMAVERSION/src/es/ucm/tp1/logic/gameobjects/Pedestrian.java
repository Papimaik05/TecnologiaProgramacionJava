package es.ucm.tp1.logic.gameobjects;

import es.ucm.tp1.logic.Game;

public class Pedestrian extends GameObject {
    private int last=1;
    private String direccion="down";
    private static int totalobstacles=0;
	public Pedestrian(Game game, int x, int y) {
		super(game, x, y,1);
		symbol="☺";
	}
	@Override
	public boolean receiveCollision(Player player) {
		if(isAlive()) {
			player.minushp();
			player.plusmoney(-player.money());
			hp--;
		}
		return true;
	}
	@Override
	public void onEnter() {
		totalobstacles++;
	}

	@Override
	public void update() {
		if(y==0) {
			last=y;
			y+=1;
			direccion="down";
		}
		else if(y==game.getlevelrow()-1) {
			last=y;
			y-=1;
			direccion="up";
		}
		else if (lmove(last,y)) {
			last=y;
			y+=1;
			direccion="down";
		}
		else if(!lmove(last,y)) {
			last=y;
			y-=1;
			direccion="up";
		}
	}
	
	public boolean lmove(int last,int next) {
		if(last<=next) {
			return true;
		}
		else {
			return false;
		}
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
		game.addmoney(-game.playerCoins());
		return true;
	}
	@Override
	public boolean receiveExplosion() {
		hp=0;
		game.addmoney(-game.playerCoins());
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
	
	@Override
	public String serialize() {
		String sz;
		sz=symbol +" "+"("+x+ ","+y +")  " + direccion+"\n";
		return sz ;
	}

}
