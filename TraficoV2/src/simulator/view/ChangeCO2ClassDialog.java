package simulator.view;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.RoadMap;
import simulator.model.SetContClassEvent;
import simulator.model.Vehicle;


public class ChangeCO2ClassDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private RoadMap _map;
	private Controller _ctr;
	private int _time;
	private String numeroticks="50";
	
	public ChangeCO2ClassDialog(RoadMap map,Controller ctr,int time) {
		super();
		if(map==null) {
			throw new IllegalArgumentException("Error al cambiar el C02");
		}
		_map=map;
		_ctr=ctr;
		_time=time;
		initGUI();
		
	}

	private void initGUI() {
		
		this.setTitle("Change CO2 class");
		this.setBounds(new Rectangle(500,200));
		
		JPanel mainPanel = new JPanel();
		this.add(mainPanel);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);
		
		
		JLabel helpMsg = new JLabel("<html><p>Schedule an event to change the C02 class of a vehicle after a given number of simulation ticks from now</p>");
		helpMsg.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(helpMsg);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel centralPanel = new JPanel();
		mainPanel.add(centralPanel);
		centralPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		JLabel lblv=new JLabel("Vehicle:");
		centralPanel.add(lblv);
		
		JComboBox <Vehicle> vehicles = new JComboBox<Vehicle>();
		centralPanel.add(vehicles);
		vehicles.setBounds(20,20,100,30);
		
		for(int i=0;i<_map.getVehicles().size();i++) {
			vehicles.insertItemAt(_map.getVehicles().get(i),i);
		}
		vehicles.setSelectedItem(_map.getVehicles().get(0));
		
		JLabel lblc=new JLabel("CO2 Class:");
		centralPanel.add(lblc);
		
		JComboBox <Integer> contamination = new JComboBox<Integer>();
		centralPanel.add(contamination);
		for(int i=0;i<=10;i++) {
			contamination.addItem(i);
	    }
		contamination.setBounds(20,20,100,30);
		
		JLabel lbls=new JLabel("Ticks:");
		centralPanel.add(lbls);
		
		JSpinner spinner = new JSpinner(new SpinnerNumberModel(50,1,999,5));
			
			spinner.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					
					 numeroticks=spinner.getValue().toString();
				}
			});
			
		centralPanel.add(spinner);
		
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel lowPanel = new JPanel();
		mainPanel.add(lowPanel);
		lowPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		
		
		JButton cancelButton = new JButton("CANCEL");
		cancelButton.setBounds(25, 50, 30, 30);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		lowPanel.add(cancelButton);
		
		lowPanel.add(Box.createRigidArea(new Dimension(5,0)));
		
		JButton okButton = new JButton("   OK   ");
		okButton.setBounds(25, 50, 30, 30);
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int auxticks=Integer.parseInt(ChangeCO2ClassDialog.this.numeroticks);
				List<Pair<String,Integer>> cs= new ArrayList<Pair<String,Integer>>();
				Pair<String,Integer> aux= new Pair<String,Integer>(vehicles.getSelectedItem().toString(),(Integer) contamination.getSelectedItem());
				cs.add(aux);
				ChangeCO2ClassDialog.this._ctr.addEvent(new SetContClassEvent(_time+auxticks,cs));
				ChangeCO2ClassDialog.this.dispose();
				
				
			}
		});
		lowPanel.add(okButton);
		
		Dimension dimpantalla = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation(dimpantalla.width/2 -this.getSize().width/2,dimpantalla.height/2 -this.getSize().height/2);
		this.setVisible(true);
		
	}

}
