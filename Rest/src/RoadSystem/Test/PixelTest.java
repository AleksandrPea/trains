package Test;

import Map.Area;
import Map.AreaModel;

public class PixelTest {
	public static void main(String[] args) {
		Area area = new Area(1000, new AreaModel());
		//System.out.println(area.getXCoordinates(459));
		for(int i = -1233; i < 1244; i = i + 1) {
			System.out.println(i);
			System.out.println(area.getXOnPanel(area.getXCoordinates(i)));
			//System.out.println((int)(area.getXOnPanel(area.getXCoordinates(i))));
			System.out.println();
		}
	}

}
