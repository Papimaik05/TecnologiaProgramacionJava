package simulator.model;


public abstract class NewRoadEvent extends Event{
	
	protected String e_id;
	protected String origen;
	protected String destino;
	protected int longitud;
	protected int limit;
	protected int vmax;
	protected Weather e_weather;

	public NewRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather) {
		super(time);
		e_id=id;
		origen=srcJun;
		destino=destJunc;
		longitud=length;
		limit=co2Limit;
		vmax=maxSpeed;
		e_weather=weather;
	}

	@Override
	public void execute(RoadMap map) {
		Road aux= createRoadObject(map);
	    map.addRoad(aux);
	}
	
	public abstract Road createRoadObject(RoadMap map);
	
	@Override
	public String toString() {
	return "New Road '"+ e_id+"'";
	}

}
