package Map;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class Area extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	AreaModel model;
	private Point left_max_point;
	private Point right_max_point;
	private int x_km;
	private int y_km;
	
	public AreaModel getModel() {
		return model;
	}

	public void setModel(AreaModel model) {
		this.model = model;
	}

	private int basic_size_of_1km;
	private int size_of_1km;
	private int panel_height;
	private int panel_width;
	
	private double zoom = 1;
	private ArrayList<SizePanel> zoom_panel;
	private ArrayList<JLabel> label_for_cursor;
	
	private ArrayList<Building> buildings;
	private BuildingView buildingView;
	
	private ArrayList<Road> roads = new ArrayList<Road>();
	private RoadView roadView;
	
	
	private boolean is_axis = true;
	
	private ArrayList<Palette> palette = null; 
	
	public Area(int size_of_1km, AreaModel model) {
		this.model = model;
		left_max_point = model.getLeft_max_point();
		x_km = model.getX_km();
		y_km = model.getY_km();
		right_max_point = new Point(
				left_max_point.x + x_km, left_max_point.y - y_km);
		buildings = model.getBuildings();
		this.setFocusable(true);
		zoom_panel = new ArrayList<SizePanel>();
		label_for_cursor = new ArrayList<JLabel>();
		this.basic_size_of_1km = size_of_1km;
		this.size_of_1km = size_of_1km;
		panel_height = (x_km + 2) * size_of_1km;
		panel_width = (y_km + 2) * size_of_1km;
		this.setBounds(0, 0, panel_height, panel_width);
		AreaMoveListener move = new AreaMoveListener();
		this.addMouseListener(move);
		this.addMouseMotionListener(move);
		PopupMenuListener pop = new PopupMenuListener();
		this.addMouseListener(pop);
		ZoomListener zl = new ZoomListener();
		this.addMouseWheelListener(zl);
		this.addKeyListener(zl);
		this.addMouseMotionListener(zl);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.GREEN);
		Rectangle2D rect = this.getArea_field();
		if(rect != null) g2.fill(rect);
		ArrayList<Line2D> netList = this.getNet();
		
		g2.setColor(Color.BLACK);
		Iterator<Line2D> it = netList.iterator();
		while(it.hasNext()) g2.draw(it.next());
		this.paintAxis(g2);
		
		this.buildingView.paint(g2);
		this.roadView.paint(g2);
		
		if(palette != null) {
			Iterator<Palette> it2 = palette.iterator();
			while(it2.hasNext()) it2.next().paint(g2);
		}
		
		g2.setColor(Color.BLACK);
		g2.drawString("" + zoom, 20, 20);
		
	}
	
	private Rectangle2D getArea_field() {
		if(left_max_point == null) return null;
		else {
			return new Rectangle2D.Double(size_of_1km , size_of_1km , 
					(Math.abs(left_max_point.x) + Math.abs(right_max_point.x))*size_of_1km,
					(Math.abs(left_max_point.y) + Math.abs(right_max_point.y))*size_of_1km);
		}
	}

	private ArrayList<Line2D> getNet() {
		ArrayList<Line2D> result = new ArrayList<Line2D>();
		for(int i = 1; i<=x_km + 1; i++) {
			result.add(new Line2D.Double(i * size_of_1km, 0, i * size_of_1km, (y_km + 2) * size_of_1km));
		}
		for(int i = 1; i<=y_km + 1; i++) {
			result.add(new Line2D.Double(0, i * size_of_1km, (x_km + 2) * size_of_1km, i * size_of_1km));
		}
		return result;
	}
	
	public JPanel getSizePanel() {
		SizePanel panel = new SizePanel(zoom, basic_size_of_1km);
		zoom_panel.add(panel);
		return panel;
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height) {
		if(x == 0 & y == 0) super.setBounds(this.getX(), this.getY(), panel_height, panel_width);
		else super.setBounds(x, y, panel_height, panel_width);
	}
	public void addArea(int x, int y) {
		boolean add = true;
		Rectangle rect = Area.this.getBounds();
		if(x < size_of_1km && add) {
			if(y < size_of_1km && add) {
				x_km++;
				y_km++;
				left_max_point.x = left_max_point.x - 1;
				left_max_point.y = left_max_point.y + 1;
				rect.x = rect.x - Area.this.size_of_1km;
				rect.y = rect.y - Area.this.size_of_1km;
				add = false;
			}
			if(y > size_of_1km && y < size_of_1km * (y_km + 1) && add) {
				x_km++;
				left_max_point.x = left_max_point.x - 1;
				rect.x = rect.x - Area.this.size_of_1km;
				add = false;
			}
			if(y > size_of_1km * (y_km + 1) && add) {
				x_km++;
				y_km++;
				left_max_point.x = left_max_point.x - 1;
				right_max_point.y = right_max_point.y - 1;
				rect.x = rect.x - Area.this.size_of_1km;
				add = false;
			}
		}
		if(x > size_of_1km *(x_km + 1) && add) {
			if(y < size_of_1km && add) {
				x_km++;
				y_km++;
				left_max_point.y = left_max_point.y + 1;
				right_max_point.x = right_max_point.x + 1;
				rect.y = rect.y - Area.this.size_of_1km;
				add = false;
			}
			if(y > size_of_1km && y < size_of_1km * (y_km + 1) && add) {
				x_km++;
				right_max_point.x = right_max_point.x + 1;
				add = false;
			}
			if(y > size_of_1km * (y_km + 1) && add) {
				x_km++;
				y_km++;
				right_max_point.x = right_max_point.x + 1;
				right_max_point.y = right_max_point.y - 1;
				add = false;
			}
		}
		if(x > size_of_1km && x < size_of_1km * (x_km + 1) && add) {
			if(y < size_of_1km && add) {
				y_km++;
				left_max_point.y = left_max_point.y + 1;
				rect.y = rect.y - Area.this.size_of_1km;
				add = false;
			}
			if(y > size_of_1km * (y_km + 1) && add) {
				y_km++;
				right_max_point.y = right_max_point.y - 1;
				add = false;
			}
		}
		panel_height = (x_km + 2) * size_of_1km;
		panel_width = (y_km + 2) * size_of_1km;
		this.setBounds(rect.x, rect.y, panel_height, panel_width);
		repaint();
	}
	
	public class SizePanel extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private double zoom;
		private int length;
		public SizePanel(double zoom, int length) {
			this.zoom = zoom;
			this.length = length;
		}
		public void setZoom(double zoom) {
			this.zoom = zoom;
			this.repaint();
		}
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			BasicStroke b = new BasicStroke(2);
			g2.setStroke(b);
			g2.drawLine(0, 5, 0, 10);
			g2.drawLine(0, 10, length, 10);
			g2.drawLine(length, 10, length, 5);
			g2.drawString("" + NumberFormat.getInstance().format(1/zoom) + "km", length + 3, 10);
		}
	}
	
	public void setZoom(double zoom) {
		this.zoom = zoom;
		Iterator<SizePanel> it = zoom_panel.iterator();
		while(it.hasNext()) it.next().setZoom(zoom);
	}
	public boolean isAxisPaint() {
		return is_axis;
	}
	public void setAxis(boolean paint_axis) {
		is_axis = paint_axis;
	}
	public void paintAxis(Graphics2D g2) {
		if(is_axis) {
			int x = (Math.abs(left_max_point.x) + 1) * size_of_1km;
			int y = (Math.abs(left_max_point.y) + 1) * size_of_1km;
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(x, 0, x, (y_km + 2) * size_of_1km);
			g2.drawLine(0, y, (x_km + 2) * size_of_1km, y);
		}
	}
	public int getXCoordinates(int x) {
		double temp_x = ((double)x)/size_of_1km;
		return (int) ((left_max_point.x + temp_x - 1) * 1000000.0);
	}
	public int getXOnPanel(int x) {
		return  (int) Math.round((x/1000000.0 + 1 - left_max_point.x) * size_of_1km);
	}
	public int getYCoordinates(int y) {
		double temp_y = ((double)y)/size_of_1km;
		return (int) ((left_max_point.y - temp_y + 1) * 1000000);
	}
	public int getYOnPanel(int y) {
		return (int) Math.round(-(y/1000000.0 - 1 - left_max_point.y) * size_of_1km);
	}
	public JLabel getCursorPane() {
		JLabel label = new JLabel("(#, #)");
		label_for_cursor.add(label);
		return label;
	}
	public boolean isPointBelongMap(Point p) {
		boolean result = false;
		if(p.x >= size_of_1km && p.x <= size_of_1km * (x_km + 1))
			if(p.y >= size_of_1km && p.y <= size_of_1km * (y_km + 1)) result = true;
		return result;
	}
	public void addBuilding(Building building) {
		Area.this.buildings.add(building);
		repaint();
	}
	public void addRoad(Road road) {
		Area.this.roads.add(road);
		repaint();
	}
	public void addPalette(Palette p) {
		if(palette == null) palette = new ArrayList<Palette>();
		palette.add(p);
	}

	public BuildingView getBuildingView() {
		return buildingView;
	}

	public void setBuildingView(BuildingView buildingView) {
		this.buildingView = buildingView;
		this.addMouseListener(buildingView);
	}
	public ArrayList<Building> getBuildings() {
		return this.buildings;
	}
	public Polygon transformBuilding(Building b) {
		int[] x = Arrays.copyOf(b.x, b.x.length);
		int[] y = Arrays.copyOf(b.y, b.y.length);
		for(int i = 0; i < x.length; i++) {
			x[i] = this.getXOnPanel(x[i]);
			y[i] = this.getYOnPanel(y[i]);
		}
		return  new Polygon(x, y, x.length);
	}

	public class ZoomListener extends MouseAdapter implements KeyListener {
		
		@Override
		public void keyPressed(KeyEvent event) {
			double zoom_before = zoom;
			Point p =  Area.this.getMousePosition();
			if(event.getKeyCode() == 61) {
				if(zoom >= 1) Area.this.setZoom(zoom + 1);
				else Area.this.setZoom(1/(1/zoom - 1));
			}
			if(event.getKeyCode() == 45) {
				if(zoom > 1) Area.this.setZoom(zoom - 1);
				else Area.this.setZoom(1/(1/zoom + 1));
			}
			int delta_x = 0;
			int delta_y = 0;
			if(p != null) {
				int x_before = p.x;
				int y_before = p.y;
				int x_after = (int) (p.x * zoom/zoom_before);
				int y_after = (int) (p.y * zoom/zoom_before);
				delta_x = x_before - x_after;
				delta_y = y_before - y_after;

			}

			Area.this.size_of_1km = (int) Math.round(basic_size_of_1km * zoom);
			panel_height = (x_km + 2) * Area.this.size_of_1km;
			panel_width = (y_km + 2) * Area.this.size_of_1km;
			Area.this.setBounds(Area.this.getX() + delta_x, Area.this.getY() + delta_y, 
					panel_height, panel_width);
			Area.this.repaint();
		}
		
		@Override
		public void keyReleased(KeyEvent event) {}

		@Override
		public void keyTyped(KeyEvent arg0) {}

		@Override
		public void mouseWheelMoved(MouseWheelEvent event) {
			int x_before = event.getX();
			int y_before = event.getY();
			double zoom_before = zoom;
			if(event.getWheelRotation() >= 0) {
				if(zoom >= 1) Area.this.setZoom(zoom + 1);
				else Area.this.setZoom(1/(1/zoom - 1));
			}
			else {
				if(zoom > 1) Area.this.setZoom(zoom - 1);
				else Area.this.setZoom(1/(1/zoom + 1));
			}
			int x_after = (int) (event.getX() * zoom/zoom_before);
			int y_after = (int) (event.getY() * zoom/zoom_before);
			int delta_x = x_before - x_after;
			int delta_y = y_before - y_after;
			Area.this.size_of_1km = (int)Math.round(basic_size_of_1km * zoom);
			panel_height = (x_km + 2) * Area.this.size_of_1km;
			panel_width = (y_km + 2) * Area.this.size_of_1km;
			Area.this.setBounds(Area.this.getX() + delta_x, Area.this.getY() + delta_y, panel_height, panel_width);
			Area.this.repaint();
		}
	}
	
	public class PopupMenuListener extends MouseAdapter {
		private int x;
		private int y;
		private JPopupMenu menu;
		private JMenuItem add;
		private JMenuItem deleteObj;
		private Building choseBuilding;
		
		@Override
		public void mousePressed(MouseEvent event) {
			Area.this.requestFocusInWindow();
			if(event.getButton() == MouseEvent.BUTTON3) {
				if(menu == null) {
					menu = new JPopupMenu();
					add = new JMenuItem("Add area");
					menu.add(add);
					add.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
								Area.this.addArea(x, y);							
						}
					});
					menu.addSeparator();
					deleteObj = new JMenuItem("Delete object");
					menu.add(deleteObj);
					deleteObj.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent arg0) {
							Area.this.removeBuilding(choseBuilding);
						}
					});
				}
				if(Area.this.getArea_field().contains(new Point(event.getX(), event.getY()))) add.setEnabled(false);
				else add.setEnabled(true);
				choseBuilding = Area.this.getBuilding(event.getPoint());
				if(choseBuilding == null) deleteObj.setEnabled(false);
				else deleteObj.setEnabled(true);
				x = event.getX();
				y = event.getY();
				menu.show(Area.this, event.getX(), event.getY());
			}
		}
	}
	public Building getBuilding(Point point) {
		Building result = null;
		if(buildings.size() > 0) {
			Point p = new Point(this.getXCoordinates(point.x),
					this.getYCoordinates(point.y));
			Iterator<Building> it = buildings.iterator();
			boolean f = true;
			while(it.hasNext() && f) {
				Building b = it.next();
				Polygon pol = new Polygon(b.x, b.y, b.x.length);
				if(pol.contains(p)) {
					result = b;
					f = false;
				}
			}
		}
		return result;
	}
	public void removeBuilding(Building b) {
		buildings.remove(b);
		buildingView.buildingsChange();
		this.repaint();
	}
	
	public ArrayList<Road> getRoads() {
		return roads;
	}

	public void setRoads(ArrayList<Road> roads) {
		this.roads = roads;
	}

	public RoadView getRoadView() {
		return roadView;
	}

	public void setRoadView(RoadView roadView) {
		this.roadView = roadView;
	}

	public class AreaMoveListener extends MouseAdapter {
		private Point cursor;
		private int delta_x = 0;
		private int delta_y = 0;

		@Override
		public void mouseDragged(MouseEvent event) {
			if(event.getModifiersEx() == InputEvent.BUTTON1_DOWN_MASK) {
				if(cursor == null) cursor = event.getPoint();
				else {
					Point p2 = event.getPoint();
					delta_x = delta_x + p2.x - cursor.x;
					delta_y = delta_y + p2.y - cursor.y;
				
					Rectangle bounds = Area.this.getBounds();
					bounds.x = bounds.x + delta_x;
					bounds.y = bounds.y + delta_y;
					Area.this.setBounds(bounds);
				
					cursor = p2;
					Area.this.repaint();
				}
			
			}
		}

		@Override
		public void mouseMoved(MouseEvent event) {
			if(event.getX() >= Area.this.size_of_1km && event.getX() <= Area.this.size_of_1km * (x_km + 1)
					&& event.getY() >= Area.this.size_of_1km && event.getY() <= Area.this.size_of_1km * (y_km + 1))	{
				Iterator<JLabel> it = label_for_cursor.iterator();
				while(it.hasNext()) it.next().setText("(" + Area.this.getXCoordinates(event.getX())/1000 +
						", " + Area.this.getYCoordinates(event.getY())/1000 + ")");
			}
			else {
				Iterator<JLabel> it = label_for_cursor.iterator();
				while(it.hasNext()) it.next().setText("(#, #)");
			}
		}
		
		@Override
		public void mouseReleased(MouseEvent arg0) {
			cursor = null;
		}
	}
}
