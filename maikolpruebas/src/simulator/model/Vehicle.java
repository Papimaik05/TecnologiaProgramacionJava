package simulator.model;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;



public class Vehicle extends SimulatedObject {
	
	private int vmax; //velocidad max
	private int vnow; //velocidad actual
	private int location; //localizacion del coche
	private int contaminaciong; //grado de contaminacion
	private int conttotal; //contaminacion total
	private int totaldistance; //distancia total del vehiculo
	private List<Junction> itinerario;
	private VehicleStatus status; 
	private Road roadcar; //carretera sobre la que esta 
	private int lastjunction; //ultimo cruce
	
	public Vehicle(String id,int maxSpeed, int contClass,List<Junction> itinerary)  {
		super(id);
		if(maxSpeed <=0) 
			throw new IllegalArgumentException("maxSpeed of the vehicle must be positive (" + maxSpeed + ")");
		vmax=maxSpeed;
		
		if(contClass < 0 ||contClass>10)
			throw new IllegalArgumentException("Grade of Contamination must be positive (" + contClass + ")");
		contaminaciong=contClass;
		
		if(itinerary.size()<2)
			throw new IllegalArgumentException("Size of the array must be bigger than 2");
		
		itinerario=Collections.unmodifiableList(new ArrayList<>(itinerary));//ni idea de que es esto
		lastjunction=0;
		totaldistance=0;
		status=VehicleStatus.PENDING;
		
	}
    public void setSpeed(int s){
    	if(status.equals(VehicleStatus.TRAVELING)) {
    		if (s<0)
        		throw new IllegalArgumentException("Speed must be positive (" + s + ")");
        	if(s>vmax)
        	   vnow=vmax;
        	else
        		vnow=s;
    	}
    	else {
    		vnow=0;
    	}
    	
    }
    public void setContaminationClass(int c){
    	
    	if(c < 0 ||c>10)
			throw new IllegalArgumentException("Contamination must be positive (" + c + ")");
    	
    	contaminaciong=c;
    }
	@Override
	public void advance(int time){
		int aux1=0;
		int auxlocation=location;
		int contaminacionturno;
		if(status.equals(VehicleStatus.TRAVELING)) {
			aux1=location + vnow;	
			if(aux1>roadcar.getLength()) {
				location=roadcar.getLength();
				totaldistance+=(location-auxlocation);
			}
			else {
				location=aux1;
				totaldistance+=vnow;
			}
			contaminacionturno=(location-auxlocation) * contaminaciong;		
			conttotal+=contaminacionturno;
			roadcar.addContamination(contaminacionturno);
			
			if(location >=roadcar.getLength()) {
				roadcar.getDest().enter(this);
				status=VehicleStatus.WAITING;
				setSpeed(0);
			}
			
		}
		else {
			System.out.println("The vehicle will not advance");
		}
		   
	}
	
	public void moveToNextRoad(){
	     Road aux=null;
		if(status==VehicleStatus.ARRIVED||status==VehicleStatus.TRAVELING) {
			throw new IllegalArgumentException("Fail to move to the next Road");
		}
		
		else if(status==VehicleStatus.PENDING) {
			aux=itinerario.get(lastjunction).roadTo(itinerario.get(lastjunction+1));
			roadcar=aux;
			roadcar.enter(this);
			location=0;
			status=VehicleStatus.TRAVELING;
			lastjunction++;
		}
		else if(status==VehicleStatus.WAITING) {
			roadcar.exit(this);
			if(lastjunction+1==itinerario.size()) {
				status=VehicleStatus.ARRIVED;
				roadcar=null;
			}
			else {
				setSpeed(0);
				location=0;
				itinerario.get(lastjunction).roadTo(itinerario.get(lastjunction + 1)).enter(this);
				status=VehicleStatus.TRAVELING;
				roadcar=itinerario.get(lastjunction).roadTo(itinerario.get(lastjunction + 1));		
				lastjunction++;
				
			}
			
		}
		
		
	}

	@Override
	public JSONObject report() {
		JSONObject prueba=new JSONObject();
		prueba.put("id",_id);
		prueba.put("speed",vnow);
		prueba.put("distance",totaldistance);
		prueba.put("co2",conttotal);
		prueba.put("class",contaminaciong);
		prueba.put("status",status.toString());
		
		if(status==VehicleStatus.TRAVELING||status==VehicleStatus.WAITING) {
			prueba.put("road",roadcar._id);
			prueba.put("location",location);
		}
       return prueba;
	}
	public int getLocation() {
		return location;
	}
	public int getSpeed() {
		return vnow;
	}
	public int  getMaxSpeed() {
		return vmax;
	}
	public int getContClass(){
		return contaminaciong;
	}
	public int getTotalCO2() {
		return conttotal;
	}
	public VehicleStatus getStatus() {
		return status;
	}
	public List<Junction> getItinerary(){
		return itinerario;
	}
	public Road getRoad() {
		return roadcar;
	}
	

}
