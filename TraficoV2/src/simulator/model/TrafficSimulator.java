package simulator.model;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator implements Observable<TrafficSimObserver> {
	
	private ArrayList<TrafficSimObserver> observers;
	private RoadMap mapacarreteras;
	private SortedArrayList<Event> listaeventos;
	private int time;
	
	public TrafficSimulator() {
		observers=new ArrayList<>();
		mapacarreteras=new RoadMap();
		listaeventos=new SortedArrayList<>();
		time=0;
	}
	public void addEvent(Event e) {
		listaeventos.add(e);
		notifyonEventAdded(mapacarreteras, listaeventos, e, time);
	}
	public void advance() {
		time++;
		notifyonAdvanceStart(mapacarreteras, listaeventos, time);
		int j=0;
		try {
		while(j<listaeventos.size() && listaeventos.get(j)._time<=time) {
			if(listaeventos.get(j)._time==time) {
				listaeventos.get(j).execute(mapacarreteras);
				listaeventos.remove(j);
			}
			else if(listaeventos.get(j)._time<time){
				listaeventos.remove(j);
			}
		}
		for(int i=0;i<mapacarreteras.getJunctions().size();i++) {
			mapacarreteras.getJunctions().get(i).advance(time);
		}
		for(int i=0;i<mapacarreteras.getRoads().size();i++) {
			mapacarreteras.getRoads().get(i).advance(time);
		}
		}catch(IllegalArgumentException e) {
			notifyonError(e.getMessage());
			throw e;
		}
		
		notifyonAdvanceEnd(mapacarreteras, listaeventos, time);
	}
	public void reset() {
		time=0;
		mapacarreteras.reset();
		listaeventos.clear();
		notifyonReset(mapacarreteras, listaeventos, time);
	}
	public JSONObject report() {
		JSONObject prueba=new JSONObject();
		prueba.put("time",time);
		prueba.put("state",mapacarreteras.report());
       return prueba;
		
	}
	@Override
	public void addObserver(TrafficSimObserver o) {
		observers.add(o);
		notifyonRegister(mapacarreteras, listaeventos, time);
	}
	@Override
	public void removeObserver(TrafficSimObserver o) {
	   observers.remove(o);
	}
	
	public void notifyonAdvanceStart(RoadMap map, List<Event> events, int time){
		for(int i=0;i<observers.size();i++) {
			observers.get(i).onAdvanceStart(map, events, time);
		}
	}
	
	public void notifyonAdvanceEnd(RoadMap map, List<Event> events, int time){
		for(int i=0;i<observers.size();i++) {
			observers.get(i).onAdvanceEnd(map, events, time);
		}
	}
	
	public void notifyonEventAdded(RoadMap map, List<Event> events, Event e, int time){
		
		for(int i=0;i<observers.size();i++) {
			observers.get(i).onEventAdded(map, events, e, time);
		}
		
	}
	
	public void notifyonReset(RoadMap map, List<Event> events, int time){
		for(int i=0;i<observers.size();i++) {
			observers.get(i).onReset(map, events, time);
		}
	}
	
	public void notifyonRegister(RoadMap map, List<Event> events, int time){
		for(int i=0;i<observers.size();i++) {
			observers.get(i).onRegister(map, events, time);
		}
	}
	
	public void notifyonError(String err){
		for(int i=0;i<observers.size();i++) {
			observers.get(i).onError(err);
		}
	}
}
