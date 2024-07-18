package es.ucm.tp1.logic.gameobjects;

import es.ucm.tp1.logic.Game;

public class Wall extends GameObject {
    private int reward=5;
    private static int totalobstacles=0;
	public Wall(Game game, int x, int y) {
		super(game, x, y,3);
		symbol="█";
	}

	@Override
	public boolean receiveCollision(Player player) {
		if(isAlive()) {
			player.minushp();
		}
		return true;
	}

	@Override
	public void onEnter() {
		totalobstacles++;
		
	}

	@Override
	public void update() {
		if(hp==3) {
			symbol="█";
		}
		else if(hp==2) {
			symbol="▒";
		}
		else if(hp==1) {
			symbol="░";
		}
	}

	@Override
	public void onDelete() {
		game.addmoney(reward);
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
		hp-=3;
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
     System.out.print("-> "+ symbol );
     return true;
	}
	
	@Override
	public String serialize() {
		String sz;
		sz=symbol +" "+"("+x+ ","+y +")  " + hp+"\n";
		return sz ;
	}

	}
		


