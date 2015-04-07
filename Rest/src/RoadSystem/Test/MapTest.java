package Test;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Map.Area;
import Map.AreaModel;
import Map.BuildingPalette;
import Map.BuildingView;
import Map.RoadPalette;
import Map.RoadView;

public class MapTest {
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setBounds(100, 100, 1000, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel p2 = new JPanel();
		p2.setBackground(Color.WHITE);
		frame.setContentPane(p2);
		p2.setLayout(new BorderLayout());
		
		Area panel = new Area(200, new AreaModel());
		panel.setBuildingView(new BuildingView(panel));
		panel.setRoadView(new RoadView(panel));
		//panel.setAxis(false);
		
		JPanel p3 = panel.getSizePanel();
		p3.setOpaque(false);
		//p2.add(p3, BorderLayout.SOUTH);
		
		JLabel label = panel.getCursorPane();
		p2.add(label, BorderLayout.EAST);
		
		BuildingPalette palette = new BuildingPalette(panel);
		panel.addPalette(palette);
		JButton button = palette.getCotrolButton();
		p2.add(button, BorderLayout.WEST);
		p2.add(p3, BorderLayout.SOUTH);
		RoadPalette roadPalette = new RoadPalette(panel);
		panel.addPalette(roadPalette);
		JButton roadBut = roadPalette.getCotrolButton();
		p2.add(roadBut, BorderLayout.SOUTH);
		
		
	
		p2.add(panel, BorderLayout.NORTH);
		frame.setVisible(true);
	}

}
