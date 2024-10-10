package simulator.model;

import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject{
	private List<Road> roadlist; //carreteras que entran al cruce
	private Map<Junction,Road> mapsaliente; //carreteras que salen de un cruce a otro
	private List<List<Vehicle>> colas; //lista de colas en el cruce con su lista de vehiculos
	private int greensem; //indice cola carretera  con semaforo verde
	private int changesem; // el paso en el cual el índice del semáforo en verde ha cambiado de valor
	private LightSwitchingStrategy estrategiasem; //estrategia cambio semaforo
	private DequeuingStrategy estrategiacola; //estrategia extraccion vehiculos
	private int x;
	private int y; //coordenadas para cruce
	

	public Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy,int xCoor, int yCoor){
		super(id);
		roadlist=new ArrayList<>();
		if(lsStrategy==null||dqStrategy==null)
			throw new IllegalArgumentException("Strategies must be not null");
		estrategiasem=lsStrategy;
	    estrategiacola=dqStrategy;
		if(xCoor<0||yCoor<0)
			throw new IllegalArgumentException("Location  must be positive ");
		x=xCoor;
		y=yCoor;
		changesem=0;
		greensem=-1;
		mapsaliente=new HashMap<Junction,Road>();
		colas=new ArrayList<>();
		
	}
    public void addIncommingRoad(Road r){
    	if(r.getDest()!=this) {
    		throw new IllegalArgumentException("The Destiny and the junction are not equal");
    	}
    	LinkedList<Vehicle> cola =new LinkedList<>() ; 
    	roadlist.add(r);
    	colas.add(cola);
    	
    }
    public void addOutGoingRoad(Road r){
    	
    	if(mapsaliente.containsKey(r.getDest())||r.getSrc()!=this) {
    		throw new  IllegalArgumentException ("Fail to add the OutGoingRoad");
    	}
    	mapsaliente.put(r.getDest(), r);
    }
    public void enter(Vehicle v){
    	int aux=0;
    	aux=roadlist.indexOf(v.getRoad());
    	colas.get(aux).add(v);
    }
    public Road roadTo(Junction j2) {
    	return mapsaliente.get(j2);
    }
	@Override
	public void advance(int time) {
		int cambio;
		List<Vehicle> aux;
		if(greensem !=-1) {
			aux = estrategiacola.dequeue(colas.get(greensem));
			for(int i=0;i<aux.size();i++) {
				aux.get(i).moveToNextRoad();
				colas.get(greensem).remove(aux.get(i));
	    	}
		}
		cambio=estrategiasem.chooseNextGreen(roadlist, colas, greensem, changesem, time);
		if(cambio!=greensem) {
			greensem=cambio;
			changesem=time;
		}
		
		
	}

	@Override
	public JSONObject report() {
		JSONObject aux =new JSONObject();
		aux.put("id", _id);
		if(greensem == -1) {
			aux.put("green", "none");
		}
		else {
			aux.put("green", roadlist.get(greensem)._id);
		}
		
		aux.put("queues", aux());
		return aux;
	}
   
	public JSONArray  aux() {
		JSONArray aux=new JSONArray();
		for(int i =0;i<colas.size();i++) {
			JSONObject aux2=new JSONObject();
			aux2.put("road",roadlist.get(i)._id);
			aux2.put("vehicles",auxVehicle(i));
			aux.put(aux2);
		}
		return aux;
		
	}
	
	public JSONArray auxVehicle(int j) {
		JSONArray aux = new JSONArray();
		for(int i=0;i<colas.get(j).size();i++) {
			aux.put(colas.get(j).get(i)._id);
		}
		return aux;	
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getGreenLightIndex() {
		return greensem;
	}
	public String getNameJunction() {
		if(greensem==-1) {
			return "NONE";
		}
		else {
			return roadlist.get(greensem)._id;
		}
	}
	public String paintInRoads() {
			String aux="";
			for(int i=0;i<roadlist.size();i++) {
				aux+=roadlist.get(i)._id + ":[";
				for(int j=0;j<colas.get(i).size();j++) {
					aux+= colas.get(i).get(j)._id +",";
				}
				aux+="],";
			}
			return aux;
	}
	
	
	
	public List<Road> getInRoads() {
		return roadlist;
	}
}
