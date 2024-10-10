package simulator.model;


import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {
	private RoadMap mapacarreteras;
	private SortedArrayList<Event> listaeventos;
	private int time;
	
	public TrafficSimulator() {
		mapacarreteras=new RoadMap();
		listaeventos=new SortedArrayList<>();
		time=0;
	}
	public void addEvent(Event e) {
		
		listaeventos.add(e);
	}
	public void advance() {
		time++;
		int j=0;
		while(j<listaeventos.size() && listaeventos.get(j)._time<=time) {
			if(listaeventos.get(j)._time==time) {
				listaeventos.get(j).execute(mapacarreteras);
				listaeventos.remove(j);
			}
		}
		for(int i=0;i<mapacarreteras.getJunctions().size();i++) {
			mapacarreteras.getJunctions().get(i).advance(time);
		}
		for(int i=0;i<mapacarreteras.getRoads().size();i++) {
			mapacarreteras.getRoads().get(i).advance(time);
		}
		
		
	}
	public void reset() {
		time=0;
		mapacarreteras.reset();
		listaeventos.clear();
	}
	public JSONObject report() {
		JSONObject prueba=new JSONObject();
		prueba.put("time",time);
		prueba.put("state",mapacarreteras.report());
       return prueba;
		
	}
}
