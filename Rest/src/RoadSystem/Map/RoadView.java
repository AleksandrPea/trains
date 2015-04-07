package Map;

import java.awt.Graphics2D;
import java.util.Iterator;

public class RoadView {
	private Area area;
	public RoadView(Area area) {
		this.area = area;
	}
	public void paint(Graphics2D g2) {
		Iterator<Road> it = area.getRoads().iterator();
		while(it.hasNext()) it.next().paint(g2, area);
	}
}
