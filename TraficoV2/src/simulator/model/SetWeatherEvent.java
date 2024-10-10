package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event {
	private List<Pair<String,Weather>> ayuda;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		super(time);
		if(ws==null) {
			throw new IllegalArgumentException("Fail to create the event");
		}
		ayuda=ws;
	}

	@Override
	public void execute(RoadMap map) {
		for(int i=0;i<ayuda.size();i++) {
			if(map.getRoad(ayuda.get(i).getFirst())==null) throw new IllegalArgumentException("Fail to create the event");
			map.getRoad(ayuda.get(i).getFirst()).setWeather(ayuda.get(i).getSecond());
		}
		
	}
	
	@Override
	public String toString() {
		String aux="Change Weather : ";
		aux+="[";
		for(int i=0;i<ayuda.size();i++) {
			aux=aux + "("+ayuda.get(i).getFirst() +","+ayuda.get(i).getSecond() +")";
			if(i<ayuda.size()-1) {
				aux+=",";
			}
		}	
		aux+="]";
		return aux;
	}

}
