package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent {
	public NewCityRoadEvent(int time, String id, String srcJun, String destJunc, int length, int co2Limit, int maxSpeed, Weather weather){
		super(time, id, srcJun, destJunc, length, co2Limit, maxSpeed, weather);
		
	}
	@Override
	public Road createRoadObject(RoadMap map) {
		return new CityRoad(e_id,map.getJunction(origen),map.getJunction(destino),vmax,limit,longitud,e_weather);
	}
	
}
