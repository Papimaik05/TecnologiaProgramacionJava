package es.ucm.tp1.logic;
import es.ucm.tp1.actions.InstantAction;
import es.ucm.tp1.control.Level;
import es.ucm.tp1.control.excepciones.InputOutputRecordException;
import es.ucm.tp1.logic.gameobjects.GameObject;
import es.ucm.tp1.logic.gameobjects.GameObjectContainer;
import es.ucm.tp1.logic.gameobjects.Player;
import java.util.Random;

public class Game implements InstantAction {
    private Level level2;
    private Random random;
    private Long sd;
    private Long starttime=(long) 0.0;
    private boolean seetime;
    private int numcicles;
    private boolean endGame;
    private boolean available;
    private GameObjectContainer container;
    private Player pl;
    private Record record;
	public Game(Long seed, Level level) throws InputOutputRecordException {
		seetime=true;
		sd=seed;
		random=new Random(sd);
		level2=level;
		available=true;
		pl=new Player(this,0,this.getlevelrow()/2);
		container=new GameObjectContainer();
		numcicles=0;
		endGame=false;
		record=new Record(level.toString());
	}
	//meta
	 public boolean checkmeta() {
	    boolean check=false;
	    if(pl.meta()) {
	    	check=true;
	    	if(newRecord()) {
	    		System.out.println("NEW RECORD:  "+time());
	    	}
	    }
	    return check;
	   }
	public int distance() {
		int distance;
		distance=getlength()-pl.getX();
		return distance;
	}
	//bucle controller
	public boolean finish() {
		endGame=true;
		return endGame;
	}
	public boolean isFinished() {
		if(checkmeta()) {
			finish();
		}
        if(!pl.isAlive()) {
        	System.out.println("[GAME OVER] Player crashed!");
			finish();
		}
		return endGame;
	}
	//reset
	public void reset() throws InputOutputRecordException {
      newreset(level2,sd);
	}
	public void newreset(Level l,long seed) throws InputOutputRecordException {
		sd=seed;
		available=false;
		level2=l;
		random=new Random(sd);
		pl=new Player(this,0,getlevelrow()/2);
		container.newgame();
		container.reset();
		addthings();
		numcicles=0;
	    valuefirsttime();
	    record=new Record(l.toString());
	    if(getlength()==10) {
	    	seetime=false;
	    }
	}
	//levels
    public int getVisibility() {
		
		return level2.returnlevelcol();
	}
    public int getlength() {
		return level2.returnlength();
	}
	public int getlevelrow() {
		return level2.returnlevelrow();
	}
	public Level getLevel() {
		return level2;
	}
	//time
    public void valuefirsttime() {
    	starttime=System.currentTimeMillis();
    }
    public double time() {
		if(starttime==0) {
			return 0;
		}
		else {
		return  (double) ((System.currentTimeMillis())-(starttime))/1000;
	}
    }
	//movimientos
    public void addmoney(int add) {
		pl.plusmoney(add);
	}
    public void collision() {
		pl.doCollision();
	}
    public boolean limits(int y) {
		if((y>=0)&&(y<getlevelrow())) {
			return true;
		}
		else {
			return false;
		}
	}
	public void goupg(){
		pl.gouppl();		
	}
     public void godowng(){
    	 pl.godownpl();    	
	}
    public void gogo() {
    	pl.go();
    }
    //pintar
	public String getPositionToString(int x,int y) {
		StringBuilder buffer = new StringBuilder();	
		if (pl.isInPosition(x,y)) {
		buffer.append(pl.toString() +" ");
		}
		if(container.isPositionEmpty(x, y)==false) {
		buffer.append(container.objectstring(x, y));  
		}
		if(x==getlength()) {
		buffer.append("¦"+ " ");
		}
		return buffer.toString();
}
	//crear objetos
	public void addthings() {
		GameObjectGenerator.generateGameObjects(this, level2);
	}
	public void tryToAddObject(GameObject object , double frequency) {
		 
		 if(getRandomNumber()<frequency)  {
			 if(container.isPositionEmpty(object.getX(),object.getY())) {
				 container.addobjects(object);
			 }
		 }
	}
	
	public int getRandomLane() {
		 return  (int) (getRandomNumber() *  (getlevelrow()));
	}
	public int getRandomColumn() {
		 return  (int) (getRandomNumber() * getVisibility());
	}
	public Double getRandomNumber() {
		 return random.nextDouble();
	}
	public Collider getObjectInPosition(int x, int y) {
		return container.getObjectPosition(x, y);
	}
	public void delete(GameObject obj) {
		container.delete(obj);
	}

	//infos
	public int posplayer() {
		return pl.getX();
	}
	public int rowplayer() {
		return pl.getY();
	}
	public int playerCoins() {
		return pl.money();
	}
	public int getCycle() {
		return numcicles;
	}
	//modo test
	public void toggleTest() {
		seetime=!seetime;
	}
	public boolean isTestMode() {
		return seetime;
	}
	//IA
	@Override
	public void execute(Game game) {
		
		
	}
	public void executeIA(InstantAction IA) {
		IA.execute(this);
	}
	//clear
	public void clear() {
		container.deleteobjects();
		container.newgame();
	}
	//cheat
	public void forceAddObject(GameObject o) {	
		container.addobjects(o);
	}
	
    public void addAvancedObject(int id) { 
       for(int y=0;y<=getlevelrow();y++) {
    	   if(container.isPositionEmpty(posplayer()+getVisibility()-1, y)==false) {
    		   container.delete(container.getObjectPosition(posplayer()+getVisibility()-1, y));
    	   }
       }
	   GameObjectGenerator.forceAdvanceObject(this, id, posplayer()+getVisibility()-1);
    }
    
	//update
	public void update() {
		if(available==true) {
			container.aliveobjects();
			container.updateobjects();
			GameObjectGenerator.generateRuntimeObjects(this);
	    	container.aliveobjects();
	    	numcicles++;
		}
		changeavailable(true);
		
	}
	public void changeavailable(boolean parametro) {
		available=parametro;
	}
	public boolean enoughcoinsplayer(int cost) {
		return pl.enoughcoins(cost);
	}
	//serialize
	public String serializetoString(int x,int y) {
		return container.serializeObjects(x,y);
	}
	//Record
	public double Record() {
		return record.showRecord(level2.toString());
	}
	public boolean newRecord() {
		return record.checkRecord((float)time());
	}
	public String symbolpl() {
		return pl.toString();
	}
}
	


