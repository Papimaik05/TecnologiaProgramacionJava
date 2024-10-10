package simulator.model;

public class CityRoad extends Road {

	public CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,Weather weather){
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
	}

	@Override
	public void reduceTotalContamination() {
		int aux = 0;
		
		if(getWeather().equals(Weather.WINDY)||getWeather().equals(Weather.STORM)){
			aux=10;
		}
		else {
			aux=2;
		}
		
		if((getTotalCO2()-aux)<0) {
			
			minusContamination(getTotalCO2());
		}
		else {
			minusContamination(aux);
		}
		
		
	}

	@Override
	public void updateSpeedLimit() {
		setSpeed(getMaxSpeed());
	}

	@Override
	public int calculateVehicleSpeed(Vehicle v) {
        int aux=0;
        aux=((11-v.getContClass())*this.getSpeedLimit())/11;
		return aux;
	}

}
