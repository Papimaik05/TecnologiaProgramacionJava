package es.ucm.tp1.logic.gameobjects;

import es.ucm.tp1.logic.Collider;
import es.ucm.tp1.logic.Game;

public class Player extends GameObject {
	private int totalcoinsplayer=5;
	
	public Player(Game game, int x, int y) {
		super(game, x, y,1);
        symbol=">";
	}
	public int hppl() {
		return hp;
	}
	public void minushp() {
		hp-=1;
	}
	public void gouppl() {
		doCollision();
		if(isAlive()) {
			if(!game.limits(y-1)) {
				   x+=1;
			   }
			   else {
				   y-=1;
				   x+=1;
			   }
		}   
	   }
	public void go() {
		doCollision();
		if(isAlive()) {
		  x+=1;
		}
	   }
	public void godownpl() {
		doCollision();
		if(isAlive()) {
		 if(!game.limits(y+1)) {
			   x+=1;
		   }
		   else {
			   y+=1;
			   x+=1;
		   }
		}
	}
	public int money() {
		return totalcoinsplayer;
	}
	public boolean posplmeta() {
		   boolean check=false;
		   if(x==game.getlength()) {
			   check=true;
		   }
		   return check;
	}
    public boolean meta() {
	   	boolean meta=false;
	   	if(x>game.getlength()) {
	   		meta=true;
	   		System.out.println("[GAME OVER] Player wins!");
	   	}
	   	return meta;
	   }
    
	@Override
	public String toString() {
		if (isAlive()) {
			return getSymbol();
		}
		else{
			return "@";
		}
		
	}
	public boolean doCollision() {
		Collider other = game.getObjectInPosition(x, y);
		if (other != null) {
		return other.receiveCollision (this);
		}
		return false;
		}

	@Override
	public boolean receiveCollision(Player player) {
		
		return false;
	}

	public void onEnter() {
		
		
	}

	@Override
	public void update() {
	     
		
	}

	
	public void onDelete() {
		
		
	}
	@Override
	public boolean receiveShoot() {
		
		return false;
	}
	public void plusmoney(int plus) {
		totalcoinsplayer+=plus;
	}
	public void turbo() {
		x+=3;
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
		// TODO Auto-generated method stub
		return false;
	}
	public boolean enoughcoins(int cost) {
		if(totalcoinsplayer>=cost) {
			game.addmoney(-cost);
			return true;
		}
		
		else {
			return false;
		}
	}
	

}
