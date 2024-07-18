package es.ucm.tp1.logic.gameobjects;
import es.ucm.tp1.logic.Collider;
import es.ucm.tp1.logic.Game;

public abstract class GameObject implements Collider {

	protected int x;

	protected int y;
	
	protected int hp;

	protected Game game;

	protected String symbol;

	public GameObject(Game game, int x, int y,int hp) {
		this.x = x;
		this.y = y;
		this.game = game;
		this.hp=hp;
	}

	protected String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		if (isAlive()) {
			return getSymbol();
		}

		return "";
	}

	public boolean isInPosition(int x, int y) {
		return this.y == y && this.x == x;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	

	public boolean isAlive() {
		if(hp>0) {
			return true;
		}
		return false;
	}

	public abstract void onEnter();

	public abstract void update();

	public abstract void onDelete();

	public String serialize() {
		String sz;
		sz=symbol +" "+"("+x+ ","+y +")\n";
		return sz ;
	}
}