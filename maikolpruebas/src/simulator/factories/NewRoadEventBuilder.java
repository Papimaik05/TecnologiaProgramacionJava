package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;

public abstract class NewRoadEventBuilder extends Builder<Event> {

	public NewRoadEventBuilder(String type) {
		super(type);
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		return createTheRoad(data);
	}
	abstract Event createTheRoad(JSONObject data);

}
