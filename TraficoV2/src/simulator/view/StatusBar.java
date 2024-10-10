package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class StatusBar extends JPanel implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel label1;
	private JLabel label2;
	private int _time;

	public StatusBar(Controller _ctrl) {
		initGUI();
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		label1=new JLabel(" Time: 0");
		label1.setBounds(10,20,300,30);
        add(label1);
        
        this.add(Box.createRigidArea(new Dimension(30,0)));
        
        label2=new JLabel("");
        label2.setBounds(10,20,300,30);
        add(label2);
	}
	
	private void updateTime() {
		label1.setText("Time: " + _time);
		label2.setText(" ");
	}
	private void updateEvent(Event e) {
		label2.setText("Event added "+ e.toString());
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_time=time;
		updateTime();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_time=time;
		updateTime();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		_time=time;
		updateEvent(e);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_time=time;
		updateTime();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		_time=time;
		
	}

	@Override
	public void onError(String err) {
	}

}
