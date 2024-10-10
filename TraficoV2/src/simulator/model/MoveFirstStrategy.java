package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {
  
	public MoveFirstStrategy() {
		
	}
	@Override
	public List<Vehicle> dequeue(List<Vehicle> q) {
		
		List<Vehicle> nuevo;
		nuevo=new ArrayList<>();
		if(!q.isEmpty()) {
			nuevo.add(q.get(0));
		}
		return nuevo;
	}

}
