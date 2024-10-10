package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimObserver;
import simulator.model.TrafficSimulator;

public class Controller {
	private TrafficSimulator ts;
	private Factory<Event> event;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory) {
		if(sim==null||eventsFactory==null) {
			throw new IllegalArgumentException("Fail in the constructor of the controller");
		}
		ts=sim;
		event=eventsFactory;
	}
	public void loadEvents(InputStream in) { 
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		if(!jo.has("events")) {
			throw new IllegalArgumentException("No events to load");
		}
		JSONArray arrayaux=jo.getJSONArray("events");
		for(int i=0;i<arrayaux.length();i++) {
			ts.addEvent(event.createInstance(arrayaux.getJSONObject(i)));
		}
		
		
	}
	public void run(int n, OutputStream out) {
		
		PrintStream p = new PrintStream(out);
		JSONObject exit=new JSONObject();
		JSONArray aux=new JSONArray();
		for(int i=0;i<n;i++) {
			ts.advance();
			aux.put(ts.report());
		}
		exit.put("states",aux);
		p.print(exit.toString(3));
		
	}
	
	public void run(int n) {
		JSONArray aux=new JSONArray();
		for(int i=0;i<n;i++) {
			ts.advance();
			aux.put(ts.report());
			
		}
	}
	public void reset() {
		ts.reset();
	}
	
	public void addObserver(TrafficSimObserver o) {
		ts.addObserver(o);
	}
	public void removeObserver(TrafficSimObserver o) {
		ts.removeObserver(o);
	}
	
	public void addEvent(Event e) {
		ts.addEvent(e);
	}
	
}
