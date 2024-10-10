package simulator.model;

public class InterCityRoad extends Road {

	public InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,Weather weather){
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	public void reduceTotalContamination(){
		int aux = 0;
		int fin;
		if(getWeather().equals(Weather.SUNNY)) {
			aux=2;
		}
		else if(getWeather().equals(Weather.CLOUDY)){
			aux=3;
		}
		else if(getWeather().equals(Weather.RAINY)){
			aux=10;
		}
		else if(getWeather().equals(Weather.WINDY)){
			aux=15;
		}
		else if(getWeather().equals(Weather.STORM)){
			aux=20;
		}
		
		fin=((100-aux)*getTotalCO2())/100;
		
		reduceContamination(fin);
	}

	@Override
	public void updateSpeedLimit() {
		int aux=0;
		if(getTotalCO2()>getContLimit()) {
			aux=getMaxSpeed()/2;
			setSpeed(aux);
		}
		else {
			setSpeed(getMaxSpeed());
		}
		
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
		int aux=0;
		aux=getSpeedLimit();
		if(getWeather().equals(Weather.STORM)) {
			aux=(aux*8)/10;
		}
		return aux;
	}

}

	