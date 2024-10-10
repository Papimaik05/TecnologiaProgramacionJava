package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class EventsTableModel extends AbstractTableModel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Event> _events;
	private String[] _colNames = {"Time", "Desc" };

	public EventsTableModel(Controller _ctrl) {
		_events=null;
		_ctrl.addObserver(this);
	}
	
	public void setEventsList(List<Event> events) {
		_events = events;
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
		return _events == null ? 0 : _events.size();
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
			s = _events.get(rowIndex).getTime();
			break;
		case 1:
			s = _events.get(rowIndex).toString();
			break;
		}
		return s;
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_events=events;
		update();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_events=events;
		update();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_events=events;
		update();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_events=events;
		update();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_events=events;
		update();
	}

	@Override
	public void onError(String err) {
		update();
	}

	
}
