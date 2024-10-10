package simulator.model;

import java.util.List;

public class RoundRobinStrategy implements LightSwitchingStrategy{
    private int time;
    public RoundRobinStrategy(int TimeSlot) {
    	time=TimeSlot;
    }
    
	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,int currTime) {
		int fin=-1;
		if(roads.isEmpty()) {
			return fin;
		}
		else if( currGreen==-1) {
			fin=0;
			return fin;
		}
		else if(currTime-lastSwitchingTime < time) {
			return currGreen;
		}
		
		else {
			return (currGreen+1)%roads.size();
		}
		
	}
}
