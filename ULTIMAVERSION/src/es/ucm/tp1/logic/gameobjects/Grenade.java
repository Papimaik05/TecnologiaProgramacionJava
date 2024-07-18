package es.ucm.tp1.logic.gameobjects;

import es.ucm.tp1.actions.Explosion;
import es.ucm.tp1.actions.InstantAction;
import es.ucm.tp1.logic.Game;

public class Grenade extends GameObject {
    private int contador=4;
	public Grenade(Game game, int x, int y) {
		super(game, x, y,1);
		symbol="ð["+ contador +"]";
	}

	@Override
	public boolean receiveCollision(Player player) {
		
		return false;
	}


	@Override
	public void onEnter() {
		
	}

	@Override
	public void update() {
		contador-=1;
		if(contador==0) {
			InstantAction IA=new Explosion(x,y);
			IA.execute(game);
			game.delete(this);
		}
		
	}
	
	@Override
	public String toString() {
		if (isAlive()) {
			symbol="ð["+ contador +"]";
			return symbol;
		}

		return "";
	}

	@Override
	public void onDelete() {
		
	}

	@Override
	public boolean receiveThunder() {
		return false;
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
	public boolean receiveWave() {
		// TODO Auto-generated method stub
		return false;
	}  
	
	@Override
	public String serialize() {
		String sz;
		sz=symbol +" "+"("+x+ ","+y +")  " + contador+"\n";
		return sz ;
	}

}
