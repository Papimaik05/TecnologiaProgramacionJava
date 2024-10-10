package simulator.factories;

import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{
	private Factory <LightSwitchingStrategy> laux;
	private Factory <DequeuingStrategy> daux;
	
	public NewJunctionEventBuilder(Factory <LightSwitchingStrategy> lsStrategy, Factory <DequeuingStrategy> dqStrategy) {
		super("new_junction");
		laux=lsStrategy;
		daux=dqStrategy;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		return new NewJunctionEvent(data.getInt("time"),data.getString("id"),laux.createInstance(data.getJSONObject("ls_strategy")),daux.createInstance(data.getJSONObject("dq_strategy")),data.getJSONArray("coor").getInt(0),data.getJSONArray("coor").getInt(1));
	}

}
