package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> listacruce;
	private List<Road> listacarretera;
	private List<Vehicle> listavehiculos;
	private Map<String,Junction> mapacruces;
	private Map<String,Road> mapacarreteras;
	private Map<String,Vehicle> mapavehiculos;
	
	protected RoadMap() {
		listacruce=new ArrayList<>();
		listacarretera=new ArrayList<>();
		listavehiculos=new ArrayList<>();
		mapacruces=new HashMap<String,Junction>();
		mapacarreteras=new HashMap<String,Road>();
		mapavehiculos=new HashMap<String,Vehicle>();

		
	}
	public void addJunction(Junction j) {
		if(mapacruces.containsKey(j._id)==false) {	
			listacruce.add(j);	
			mapacruces.put(j._id, j);
		}
		else{
			throw new IllegalArgumentException("Fail to add Junction");
		}
		
	}
	public void addRoad(Road r){
		if(mapacarreteras.containsValue(r)||mapacruces.containsValue(r.getDest())==false||mapacruces.containsValue(r.getSrc())==false) {
			throw new IllegalArgumentException("Fail to add Road");
		}
			listacarretera.add(r);
			mapacarreteras.put(r._id, r);
	}
	public void addVehicle(Vehicle v) {
		if(mapavehiculos.containsValue(v)||auxitinerary(v)==false) {
			throw new IllegalArgumentException("Fail to add Vehicle");
		}
			listavehiculos.add(v);
			mapavehiculos.put(v._id,v);
		
	}
	public boolean auxitinerary(Vehicle v) {
		boolean fin = true;
		List<Junction> aux=v.getItinerary();
		for(int i=0;i<aux.size()-1;i++) {
			if(aux.get(i).roadTo(aux.get(i+1)) == null) {
				fin=false;
			}
		}
		return fin;
	}
	public Junction getJunction(String id) {
		return mapacruces.get(id);
		
	}
	public Road getRoad(String id) {
		return mapacarreteras.get(id);
	}
	public Vehicle getVehicle(String id) {
		return mapavehiculos.get(id);
		
	}
	public List<Junction> getJunctions(){
		return Collections.unmodifiableList(listacruce);
	}
	public List<Road> getRoads(){
		return Collections.unmodifiableList(listacarretera);
		
	}
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(listavehiculos);
		
	}
	public void reset() {
		listacruce.clear();
		listacarretera.clear();
		listavehiculos.clear();
		mapacruces.clear();
		mapacarreteras.clear();
		mapavehiculos.clear();
	}
	public JSONObject report() {
		JSONObject prueba=new JSONObject();
		prueba.put("junctions",reportaux1());
		prueba.put("roads",reportaux2());
		prueba.put("vehicles",reportaux3());
        return prueba;	
	}
	
	public JSONArray reportaux1() {
		JSONArray aux= new JSONArray();
		for(int i=0;i<listacruce.size();i++) {
			aux.put(listacruce.get(i).report());
		}
		return aux;
	}
	
	public JSONArray reportaux2() {
		JSONArray aux= new JSONArray();
		for(int i=0;i<listacarretera.size();i++) {
			aux.put(listacarretera.get(i).report());
		}
		return aux;
	}
	
	public JSONArray reportaux3() {
		JSONArray aux= new JSONArray();
		for(int i=0;i<listavehiculos.size();i++) {
			aux.put(listavehiculos.get(i).report());
		}
		return aux;
	}
}
