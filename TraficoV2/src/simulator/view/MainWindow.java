package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class MainWindow extends JFrame{

	private Controller _ctrl;
	
	public MainWindow(Controller ctrl) {
		super("Traffic Simulator");
		_ctrl = ctrl;
		initGUI();
	}
	
	private void initGUI() {
		
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		this.setContentPane(mainPanel);
		mainPanel.add(new ControlPanel(_ctrl), BorderLayout.PAGE_START);
		mainPanel.add(new StatusBar(_ctrl),BorderLayout.PAGE_END);
		JPanel viewsPanel = new JPanel(new GridLayout(1, 2));
		mainPanel.add(viewsPanel, BorderLayout.CENTER);
		
		
		JPanel tablesPanel = new JPanel();
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(tablesPanel);
		
		
		JPanel mapsPanel = new JPanel();
		mapsPanel.setLayout(new BoxLayout(mapsPanel, BoxLayout.Y_AXIS));
		viewsPanel.add(mapsPanel);
		
		
		// tables
		JPanel eventsView =createViewPanel(new JTable(new EventsTableModel(_ctrl)), "Events");
		eventsView.setBorder(new TitledBorder("Events"));
		eventsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(eventsView);
		
		JPanel VehiclesView =createViewPanel(new JTable(new VehiclesTableModel(_ctrl)), "Vehicles");
		VehiclesView.setBorder(new TitledBorder("Vehicles"));
		VehiclesView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(VehiclesView);
		
		JPanel RoadsView =createViewPanel(new JTable(new RoadsTableModel(_ctrl)), "Roads");
		RoadsView.setBorder(new TitledBorder("Roads"));
		RoadsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(RoadsView);
		
		JPanel JunctionsView =createViewPanel(new JTable(new JunctionsTableModel(_ctrl)), "Junctions");
		JunctionsView.setBorder(new TitledBorder("Junctions"));
		JunctionsView.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(JunctionsView);
		
		
		// ...
		// maps
		JPanel mapView = createViewPanel(new MapComponent(_ctrl), "Map");
		mapView.setBorder(new TitledBorder("Map"));
		mapView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapView);
		
		JPanel mapRoadView = createViewPanel(new MapByRoadComponent(_ctrl), "Map By Road");
		mapRoadView.setBorder(new TitledBorder("Map By Road"));
		mapRoadView.setPreferredSize(new Dimension(500, 400));
		mapsPanel.add(mapRoadView);
		// TODO add a map for MapByRoadComponent
		// ...
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.pack();
		
		Dimension dimpantalla = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimpantalla.width/2 -this.getSize().width/2,dimpantalla.height/2 - this.getSize().height/2);
		this.setVisible(true);
		}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel( new BorderLayout() );
		// TODO add a framed border to p with title
		p.add(new JScrollPane(c));
		return p;
		}
}



