package simulator.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{
	
	private Junction origen; 
	private Junction destino;
	private int longitud;
	private int vmax; //velocidad maxima
	private int vlimit; //velocidad limite
	private int alarm; //alarma por contaminacion excesiva
	private Weather weather;
	private int conttotal; //contador contaminacion total
	private List<Vehicle> vehiclelist;
	
	

	public Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,int contLimit, int length, Weather weather){
		super(id);
		vehiclelist=new ArrayList<>();
		if(maxSpeed<=0)
			throw new IllegalArgumentException("MaxSpeed must be positive (" + maxSpeed + ")");
		vmax=maxSpeed;
		vlimit=vmax;
		if(contLimit<=0)
			throw new IllegalArgumentException("Limit of contamination must be positive (" + contLimit + ")");
		alarm=contLimit;
		if(length<=0)
			throw new IllegalArgumentException("Size of the road must be positive (" + length + ")");
		longitud=length;
		if(srcJunc==null||destJunc==null||weather==null)
			throw new IllegalArgumentException("Junction must be not null");
		origen=srcJunc;
		destino=destJunc;
		this.weather=weather;
		origen.addOutGoingRoad(this);
		destino.addIncommingRoad(this);
	}
	public void enter(Vehicle v){
		if(v.getLocation()!=0 || v.getSpeed()!=0 )
			throw new IllegalArgumentException("Location and Speed must be distinct of 0 ");
		vehiclelist.add(v);
	}
	
	public void exit(Vehicle v) {
		int aux;
		aux=vehiclelist.indexOf(v);
		vehiclelist.remove(aux);
	}
	public void setSpeed(int sp) {
		vlimit=sp;
	}
	public void setWeather(Weather w){
		if(w==null)
			throw new IllegalArgumentException("Weather must be not null ");
		weather=w;
	}
	public void addContamination(int c){
		if(c<0)
			throw new IllegalArgumentException("Contamination must be positive (" + c + ")");
		conttotal+=c;
	}
	public void minusContamination(int c) {
		conttotal-=c;
	}
	public void reduceContamination(int c) {
		conttotal=c;
	}
	
    public abstract void reduceTotalContamination();
    public abstract void updateSpeedLimit();
    public abstract int calculateVehicleSpeed(Vehicle v);
    
    public void orden() {
    	Vehicle aux;
    	for(int i=0;i<vehiclelist.size()-1;i++) {
    		for(int j=0;j<vehiclelist.size()-i-1;j++) {
    			if(vehiclelist.get(j).getLocation()<vehiclelist.get(j+1).getLocation()) {
    				aux=vehiclelist.get(j);
    			    vehiclelist.set(j,vehiclelist.get(j+1));
    			    vehiclelist.set(j+1, aux);
    			}
    			    
    		}
		}
    	
    }
	@Override
	public void advance(int time){
		reduceTotalContamination();
		updateSpeedLimit();
		for(int i=0;i<vehiclelist.size();i++) {
			vehiclelist.get(i).setSpeed(calculateVehicleSpeed(vehiclelist.get(i)));
			vehiclelist.get(i).advance(time);
		}
		orden();
	}

	@Override
	public JSONObject report() {
		JSONObject prueba=new JSONObject();
		prueba.put("id",_id);
		prueba.put("speedlimit",vlimit);
		prueba.put("weather",weather.toString());
		prueba.put("co2",conttotal);
		prueba.put("vehicles",aux());

       return prueba;
	}
	
	public JSONArray aux() {
		JSONArray aux = new JSONArray();
		
		for(int i=0;i<vehiclelist.size();i++) {
			aux.put(vehiclelist.get(i)._id);
		}
		return aux;
		
	}
	public int getLength() {
		return  longitud;
	}
	
	public Junction getSrc() {
		return origen;
	}
	public Junction getDest() {
		return destino;
	}
	public Weather getWeather() {
		return weather;
	}
	public int getContLimit() {
		return alarm;
	}
	public int getMaxSpeed() {
		return vmax;
	}
	public int getTotalCO2() {
		return conttotal;
	}
	public int getSpeedLimit() {
		return vlimit;
	}
	public List<Vehicle> getVehicles(){
		return Collections.unmodifiableList(vehiclelist);
	}
	

	
	

}
