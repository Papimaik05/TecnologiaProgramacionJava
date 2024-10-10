package simulator.model;

import java.util.List;
import simulator.misc.Pair;

public class SetContClassEvent extends Event {
	private List<Pair<String,Integer>> ayuda;

	public SetContClassEvent (int time, List<Pair<String,Integer>> cs){
		super(time);
		if(cs==null) {
			throw new IllegalArgumentException("Fail to create the event");
		}
		ayuda=cs;
	}

	@Override
	public void execute(RoadMap map) {
		for(int i=0;i<ayuda.size();i++) {
			if(map.getVehicle(ayuda.get(i).getFirst())==null) throw new IllegalArgumentException("Fail to create the event");	
			map.getVehicle(ayuda.get(i).getFirst()).setContaminationClass(ayuda.get(i).getSecond());
		}
		
	}

}
