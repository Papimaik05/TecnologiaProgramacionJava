package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver {

private static final long serialVersionUID = 1L;
	
	private List<Vehicle> _vehicles;
	private String[] _colNames = {"Id", "Location","Iterinary","C02 Class","Max Speed","Speed","Total C02","Distance" };

	public VehiclesTableModel(Controller _ctrl) {
		_vehicles=null;
		_ctrl.addObserver(this);
	}
	
	public void setEventsList(List<Vehicle> vehicles) {
		_vehicles = vehicles;
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
		return _vehicles == null ? 0 : _vehicles.size();
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
			s = _vehicles.get(rowIndex).getId();
			break;
		case 1:
			s = _vehicles.get(rowIndex).getLocationpaint();
			break;
		case 2:
			s = _vehicles.get(rowIndex).getItinerary();
			break;
		case 3:
			s = _vehicles.get(rowIndex).getContClass();
			break;
		case 4:
			s = _vehicles.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = _vehicles.get(rowIndex).getSpeed();
			break;
		case 6:
			s = _vehicles.get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = _vehicles.get(rowIndex).getDistance();
			break;
		}
		return s;
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_vehicles=map.getVehicles();
		update();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_vehicles=map.getVehicles();
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_vehicles=map.getVehicles();
		update();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_vehicles=map.getVehicles();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_vehicles=map.getVehicles();
		update();
	}

	@Override
	public void onError(String err) {
		update();
	}
}

	
