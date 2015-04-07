package Map;

import java.awt.Graphics2D;

import javax.swing.JButton;

public interface Palette {
	public void paint(Graphics2D g2);
	public void setPaintMode();
	public JButton getCotrolButton();
}
