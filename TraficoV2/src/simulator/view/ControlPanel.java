package simulator.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  JButton ficheroeventos;
	private  JButton cambiocontaminacion;
	private  JButton cambioweather;
	private  JButton exit;
	private  JButton run;
	private  JButton stop;
	private  JSpinner spinner;
	private  Controller ctr;
	private int _time;
	private boolean _stopped;
	private String numeroticks="50";
	private RoadMap _map;

	public ControlPanel(Controller _ctrl) {
		initGUI();
		ctr=_ctrl;
		_stopped=false;
		_ctrl.addObserver(this);
	}
	
	private void initGUI() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
	
		//archivos
		ficheroeventos=new JButton();
		ficheroeventos.setBounds(25, 50, 30, 30);
		ImageIcon archivo=new ImageIcon(loadImage("open.png"));
		ficheroeventos.setIcon(new ImageIcon(archivo.getImage().getScaledInstance(ficheroeventos.getWidth(), ficheroeventos.getHeight(), Image.SCALE_SMOOTH)));
		ficheroeventos.addActionListener(new MiActionListener());
		this.add(ficheroeventos);
		
		this.add(Box.createRigidArea(new Dimension(10,0)));
		
		//contaminacion
		cambiocontaminacion=new JButton();
		cambiocontaminacion.setBounds(25, 50, 30, 30);
		ImageIcon contaminacion=new ImageIcon(loadImage("co2class.png"));
		cambiocontaminacion.setIcon(new ImageIcon(contaminacion.getImage().getScaledInstance(cambiocontaminacion.getWidth(), cambiocontaminacion.getHeight(), Image.SCALE_SMOOTH)));
		cambiocontaminacion.addActionListener(new MiActionListener());
		this.add(cambiocontaminacion);
		
		//weather
		cambioweather=new JButton();
		cambioweather.setBounds(25, 50, 30, 30);
		ImageIcon weather=new ImageIcon(loadImage("weather.png"));
		cambioweather.setIcon(new ImageIcon(weather.getImage().getScaledInstance(cambioweather.getWidth(), cambioweather.getHeight(), Image.SCALE_SMOOTH)));
		cambioweather.addActionListener(new MiActionListener());
		this.add(cambioweather);
		
		this.add(Box.createRigidArea(new Dimension(10,0)));
		
		//play
		run=new JButton();
		run.setBounds(25, 50, 30, 30);
		ImageIcon runb=new ImageIcon(loadImage("run.png"));
		run.setIcon(new ImageIcon(runb.getImage().getScaledInstance(run.getWidth(), run.getHeight(), Image.SCALE_SMOOTH)));
		run.addActionListener(new MiActionListener());
		this.add(run);
		
		//stop
		stop=new JButton();
		stop.setBounds(25, 50, 30, 30);
		ImageIcon stopb=new ImageIcon(loadImage("stop.png"));
		stop.setIcon(new ImageIcon(stopb.getImage().getScaledInstance(stop.getWidth(), stop.getHeight(), Image.SCALE_SMOOTH)));
		stop.addActionListener(new MiActionListener());
		this.add(stop);
		
		
		//Ticks y spinner
		JPanel auxpanel=new JPanel();
		auxpanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		JLabel lbl;
		lbl=new JLabel("Ticks:");
		auxpanel.add(lbl);
		
	    spinner = new JSpinner(new SpinnerNumberModel(50,1,999,5));
		
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				
				numeroticks=spinner.getValue().toString();
			}
		});
		auxpanel.add(spinner);	
		this.add(auxpanel);
		
		
		this.add(Box.createHorizontalGlue());
		
		//salida
		exit=new JButton();
		exit.setBounds(25, 50, 30, 30);
		ImageIcon exitb=new ImageIcon(loadImage("exit.png"));
		exit.setIcon(new ImageIcon(exitb.getImage().getScaledInstance(exit.getWidth(), exit.getHeight(), Image.SCALE_SMOOTH)));
		exit.addActionListener(new MiActionListener());
		this.add(exit);	
	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	private class MiActionListener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			if(e.getSource()==ficheroeventos) {
				JFileChooser file;
				file=new JFileChooser();
				file.setCurrentDirectory(new File("resources\\examples"));
				file.setMultiSelectionEnabled(false);
				file.setFileFilter(new FileNameExtensionFilter("Archivo de json","json"));
				
				
				int seleccion=file.showOpenDialog(ControlPanel.this.getParent());
				if(seleccion== JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(ControlPanel.this.getParent(), "Se ha seleccionado abrir el archivo: " + file.getSelectedFile());
					try {
						ctr.reset();
						ctr.loadEvents(new FileInputStream(file.getSelectedFile()));
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(ControlPanel.this.getParent(), "Error en el archivo: " + file.getSelectedFile());
					}
				} 
				else {
					JOptionPane.showMessageDialog(ControlPanel.this.getParent(), "Se ha pulsado cancelar o ha ocurrido un error.");
				}
				
			}
			else if(e.getSource()==cambiocontaminacion) {
				try {
					new ChangeCO2ClassDialog(_map,ctr,_time);
				}catch(IllegalArgumentException error) {
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(ControlPanel.this),error.getMessage(),"Error CO2",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			else if(e.getSource()==cambioweather) {
				try {
					new ChangeWeatherDialog(_map,ctr,_time);
				}catch(IllegalArgumentException error) {
					JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(ControlPanel.this),error.getMessage(),"Error Weather",JOptionPane.ERROR_MESSAGE);
				}
			}
			else if(e.getSource()==run) {
				
				int aux=Integer.parseInt(numeroticks);
				_stopped=false;
				enableToolBar(false);
				run_sim(aux);
				
			}
			else if(e.getSource()==stop) {
				stop();
			}
			else if(e.getSource()==exit) {
				int reallyexit = JOptionPane.showConfirmDialog(SwingUtilities.getWindowAncestor(ControlPanel.this),"Are sure you want to quit?","Quit",JOptionPane.YES_NO_OPTION);	
				if(reallyexit == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				
			}
		}
	}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
		try {
		ctr.run(1);
		Thread.sleep(40);
		} catch (Exception e) {
		_stopped = true;
		return;
		}
		
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				run_sim(n - 1);
			}
			});
			} else {
				enableToolBar(true);
				_stopped = true;
			}
		}
	
	private void enableToolBar(boolean b) {
	   ficheroeventos.setEnabled(b);
	   cambiocontaminacion.setEnabled(b);
	   cambioweather.setEnabled(b);
	   exit.setEnabled(b);
	   run.setEnabled(b);
   }

	private void stop() {
	 _stopped = true;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		_map=map;
		_time=time;
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		_map=map;
		_time=time;
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		_map=null;
		_time=time;
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onError(String err) {
		JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(ControlPanel.this),err.toString(),"Error Run",JOptionPane.ERROR_MESSAGE);
		enableToolBar(true);
		stop();		
	}

}
