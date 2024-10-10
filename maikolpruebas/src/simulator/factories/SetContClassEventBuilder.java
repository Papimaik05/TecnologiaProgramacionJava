package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetContClassEvent;


public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
		
	}

	@Override
	protected Event createTheInstance(JSONObject data) {

		return new SetContClassEvent(data.getInt("time"),aux(data.getJSONArray("info")));
	}
	
	
	public List<Pair<String,Integer>> aux(JSONArray array){
		List<Pair<String,Integer>> aux =new  ArrayList<>();
		
		for(int i=0;i<array.length();i++) {
			Pair<String,Integer> aux2= new Pair<String,Integer>(array.getJSONObject(i).getString("vehicle"),array.getJSONObject(i).getInt("class"));
			aux.add(aux2);
		}
		return aux;
		
	}
	

}
