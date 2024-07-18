package es.ucm.tp1.logic.gameobjects;

import java.util.ArrayList;
public class GameObjectContainer {
		private ArrayList<GameObject> gameobjects;
		public GameObjectContainer() {
		gameobjects = new ArrayList<>();
		}
		
		public void addobjects(GameObject object) {
			gameobjects.add(object);
			object.onEnter();
		}
		public void delete(GameObject object) {
			gameobjects.remove(object);
			object.onDelete();
		}
		public void newgame() {
			gameobjects.clear();
		}
		public boolean isPositionEmpty(int x,int y) {
			return getObjectPosition(x,y)==null;
		}
		
		public GameObject getObjectPosition(int x,int y) {
			for(int i=0;i<gameobjects.size();i++) {
				if((gameobjects.get(i).isInPosition(x, y)==true)){
					return gameobjects.get(i);
				}
			}
			return null;
		}
		public void reset(){
			Obstacle.reset();
			Coin.reset();
			SuperCoin.reset();
			Obstacle.reset();
			Pedestrian.reset();
			Truck.reset();
			Turbo.reset();
			Wall.reset();
		}
		public  void aliveobjects() {
			for(int i=0;i<gameobjects.size();i++) {
				if(gameobjects.get(i).isAlive()==false) {
					delete(gameobjects.get(i));
				}
			}
		}

		public void updateobjects() {
			
			for(int i=0;i<gameobjects.size();i++) {
					gameobjects.get(i).update();
				}
		}
		
		public void deleteobjects() {
			for(int i=0;i<gameobjects.size();i++) {
				gameobjects.get(i).onDelete();
			}
		}
		public String objectstring(int x,int y) {
			String r = "";
			for(int i=0;i<gameobjects.size();i++) {
				if(gameobjects.get(i).isInPosition(x, y)==true) {
					r+=gameobjects.get(i).toString() + " ";
				}
			}
			return r;
		}

		public String serializeObjects(int x,int y) {
			StringBuilder sz=new StringBuilder();
			for(int i=0;i<gameobjects.size();i++) {
				if(gameobjects.get(i).isInPosition(x, y)) {
					sz.append(gameobjects.get(i).serialize());
				}
				
			}
			return sz.toString();
		}
}
