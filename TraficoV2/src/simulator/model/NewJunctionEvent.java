package simulator.model;

public class NewJunctionEvent extends Event {
	private String e_id;
	private LightSwitchingStrategy e_lsStrategy;
	private DequeuingStrategy e_dqStrategy;
	private int x;
	private int y;
	
	public NewJunctionEvent(int time, String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(time);
		e_id=id;
		e_lsStrategy=lsStrategy;
		e_dqStrategy=dqStrategy;
		x=xCoor;
		y=yCoor;		
		
	}

	@Override
	public void execute(RoadMap map) {
		map.addJunction(new Junction(e_id,e_lsStrategy,e_dqStrategy,x,y));
	}
	
	@Override
	public String toString() {
	return "New Junction '"+ e_id+"'";
	}

}
