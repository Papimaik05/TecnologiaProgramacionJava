package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event> {
	public NewVehicleEventBuilder() {
		super("new_vehicle");
	}
	@Override
	protected Event createTheInstance(JSONObject data) {
		return new NewVehicleEvent(data.getInt("time"),data.getString("id"),data.getInt("maxspeed"),data.getInt("class"),auxitinerary(data.getJSONArray("itinerary")));
	}
	
	public List<String> auxitinerary(JSONArray array){ //rellenr itinerario vehiculo
		List<String> aux =new ArrayList<>();
		for(int i=0;i<array.length();i++) {
			aux.add(array.getString(i));
		}
		return aux;
	}

}
