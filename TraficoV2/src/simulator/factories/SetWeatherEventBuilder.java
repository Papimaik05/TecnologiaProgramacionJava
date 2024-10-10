package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event> {

	public SetWeatherEventBuilder() {
		super("set_weather");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		return new SetWeatherEvent(data.getInt("time"),aux(data.getJSONArray("info")));
	}
	
	public List<Pair<String,Weather>> aux(JSONArray array){
		List<Pair<String,Weather>> aux =new  ArrayList<>();
		
		for(int i=0;i<array.length();i++) {
			Pair<String,Weather> aux2= new Pair<String,Weather>(array.getJSONObject(i).getString("road"),Weather.valueOf(array.getJSONObject(i).getString("weather")));
			aux.add(aux2);
		}
		return aux;
		
	}

}
