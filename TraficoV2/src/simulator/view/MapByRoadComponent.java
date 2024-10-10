package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int _JRADIUS = 10;

	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	private RoadMap _map;

	private Image _car;
	
	
	public MapByRoadComponent(Controller ctr) {
		initGUI();
		setPreferredSize (new Dimension (300, 200));
		ctr.addObserver(this);
	}

	private void initGUI() {
		_car = loadImage("car.png");
	}
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}

	private void drawMap(Graphics g) {
		
		for(int i =0;i<_map.getRoads().size();i++) {
			g.setColor(Color.BLACK);
			g.drawLine(50, (i+1)*50 , getWidth()-100, (i+1)*50);
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(50 - _JRADIUS / 2, (i+1)*50  - _JRADIUS / 2, _JRADIUS, _JRADIUS);
			g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(_map.getRoads().get(i).getSrc().getId(), 50, (i+1)*50 - 6);
			
			g.setColor(Color.BLACK);
			g.drawString(_map.getRoads().get(i).getId(), 20, (i+1)*50);
			
			
			Color arrowColor = _RED_LIGHT_COLOR;
			int idx = _map.getRoads().get(i).getDest().getGreenLightIndex();
		    if (idx != -1 && _map.getRoads().get(i).equals(_map.getRoads().get(i).getDest().getInRoads().get(idx))) {
			    arrowColor = _GREEN_LIGHT_COLOR;
			}
		    g.setColor(arrowColor);
		    g.fillOval( getWidth()-100 - _JRADIUS / 2, (i+1)*50  - _JRADIUS / 2, _JRADIUS, _JRADIUS);
		    g.setColor(_JUNCTION_LABEL_COLOR);
			g.drawString(_map.getRoads().get(i).getDest().getId(), getWidth()-100, (i+1)*50 - 6);
		    
		    //emoticonos
		    int C= (int) Math.floor(Math.min((double) _map.getRoads().get(i).getTotalCO2()/(1.0 + (double) _map.getRoads().get(i).getContLimit()),1.0) / 0.19);
		    g.drawImage(loadImage("cont_"+C+".png"), getWidth()-40, (i+1)*50 - 18,32, 32, this);
		    
		    
		    //weather    
		    g.drawImage(loadImage(TypeWeather(i)+".png"), getWidth()-85, (i+1)*50 - 18,32, 32, this);
		    //vehicles
		    for(int j=0;j<_map.getRoads().get(i).getVehicles().size();j++) {
		    	int x=50 + (int) ((getWidth()-150) * ((double) _map.getRoads().get(i).getVehicles().get(j).getLocation() / (double) _map.getRoads().get(i).getLength()));
				g.drawImage(_car, x, (i+1)*50 - 6, 16, 16, this);
				g.drawString(_map.getRoads().get(i).getVehicles().get(j).getId(), x, (i+1)*50 - 6);
		    }
		    
		}
		
	}
	
	public String TypeWeather(int i) {
        if(_map.getRoads().get(i).getWeather()==Weather.CLOUDY) {
            return "cloud";
        }
        else if(_map.getRoads().get(i).getWeather()==Weather.RAINY) {
            return "rain";
        }
        else if(_map.getRoads().get(i).getWeather()==Weather.STORM) {
            return "storm";
        }
        else if(_map.getRoads().get(i).getWeather()==Weather.SUNNY) {
            return "sun";
        }
        else {
            return "wind";
        }
    }

	

	// this method is used to update the preffered and actual size of the component,
	// so when we draw outside the visible area the scrollbars show up
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}

	// loads an image from a file
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}

	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}

}
