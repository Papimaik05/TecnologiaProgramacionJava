package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy {
	private int time;
	public MostCrowdedStrategy(int TimeSlot) {
		time=TimeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,int currTime) {
		int fin=-1;
		int mayor=0;
		if(roads.isEmpty()) {
			return fin;
		}
		else if(currGreen==-1) {
			for(int i=0;i<qs.size();i++) {
				if(qs.get(i).size()>mayor) {
					mayor=qs.get(i).size();
					fin=i;
				}
			}
			
			return fin;
		}
		else if(currTime-lastSwitchingTime < time) {
			return currGreen;
		}
		
		else { //busqueda circular		
			int aux=(currGreen+1)%roads.size();		
			for(int i=0;i<qs.size();i++) {
				if(qs.get((i+aux)%qs.size()).size()>mayor) {
					mayor=qs.get((i+aux)%qs.size()).size();
					fin=i+aux;
				}
			}
			return fin;
		}
	}

}
