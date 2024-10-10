package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver {

private static final long serialVersionUID = 1L;
	
	private List<Road> _roads;
	private String[] _colNames = {"Id", "Length","Weather","Max Speed","Speed Limit","Total C02","C02 Limit" };

	public RoadsTableModel(Controller _ctrl) {
		_roads=null;
		_ctrl.addObserver(this);
	}
	
	public void setEventsList(List<Road> roads) {
		_roads = roads;
		update();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}
	
	public void update() {
		fireTableDataChanged();		
	}
	
	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}

	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	public int getRowCount() {
		return _roads == null ? 0 : _roads.size();
	}
	
	@Override
	// método obligatorio
	// así es como se va a cargar la tabla desde el ArrayList
	// el índice del arrayList es el número de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _roads.get(rowIndex).getId();
			break;
		case 1:
			s = _roads.get(rowIndex).getLength();
			break;
		case 2:
			s = _roads.get(rowIndex).getWeather();
			break;
		case 3:
			s = _roads.get(rowIndex).getMaxSpeed();
			break;
		case 4:
			s = _roads.get(rowIndex).getSpeedLimit();
			break;
		case 5:
			s = _roads.get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = _roads.get(rowIndex).getContLimit();
			break;
		}
		return s;
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time){
		_roads=map.getRoads();
		update();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_roads=map.getRoads();
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_roads=map.getRoads();
		update();
		
	}
	
	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_roads=map.getRoads();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_roads=map.getRoads();
		update();
	}

	@Override
	public void onError(String err) {
		update();
	}
}
