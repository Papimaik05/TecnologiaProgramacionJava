package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class NewVehicleEvent extends Event{
    private String e_id;
    private int vmax; 
    private int Cont;
    private List<String> itinerario;
    private List<Junction> itinerario2;
    
	public NewVehicleEvent(int time, String id, int maxSpeed, int contClass, List<String> itinerary) {
		super(time);
		e_id=id;
		vmax=maxSpeed;
		Cont=contClass;
		itinerario=itinerary;
		itinerario2=new ArrayList<>();
	}

	@Override
	public void execute(RoadMap map) {
		Vehicle aux;
		for(int i=0;i<itinerario.size();i++) {
			itinerario2.add(map.getJunction(itinerario.get(i)));
		}
		aux= new Vehicle(e_id,vmax,Cont,itinerario2);
		map.addVehicle(aux);
		aux.moveToNextRoad();
	}
	
	@Override
	public String toString() {
	    return "New Vehicle '"+e_id+"'";
	}


}
