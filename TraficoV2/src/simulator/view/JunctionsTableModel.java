package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver {

private static final long serialVersionUID = 1L;
	
	private List<Junction> _junction;
	private String[] _colNames = {"Id", "Green","Queues" };

	public JunctionsTableModel(Controller _ctrl) {
		_junction=null;
		_ctrl.addObserver(this);
	}
	
	public void setEventsList(List<Junction> junctions) {
		_junction = junctions;
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
		return _junction == null ? 0 : _junction.size();
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
			s = _junction.get(rowIndex).getId();
			break;
		case 1:
			s = _junction.get(rowIndex).getNameJunction();
			break;
		case 2:
			s = _junction.get(rowIndex).paintInRoads();
			break;
		}
		return s;
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_junction=map.getJunctions();
		update();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_junction=map.getJunctions();
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_junction=map.getJunctions();
		update();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_junction=map.getJunctions();
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_junction=map.getJunctions();
		update();
	}

	@Override
	public void onError(String err) {
		update();
	}


}
