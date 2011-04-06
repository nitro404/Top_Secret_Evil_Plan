import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

public class Position extends Point {
	
	public static int SELECTION_SIZE = 6;
	
	private static final long serialVersionUID = 1L;
	
	public Position() {
		super();
	}
	
	public Position(int x, int y) {
		super(x, y);
	}
	
	public Position(Point p) {
		super(p);
	}
	
	public boolean isValid() {
		return x >= 0 && y >= 0 && x < getMaxX() && y < getMaxY(); 
	}
	
	public static boolean isValid(Position p) {
		return p != null && p.isValid();
	}
	
	public static boolean isValid(Point p) {
		return p != null && p.x >= 0 && p.y >= 0 && p.x < getMaxX() && p.y < getMaxY();
	}
	
	public static int getMaxX() {
		return SystemManager.settings.getWebcamResolution().width;
	}
	
	public static int getMaxY() {
		return SystemManager.settings.getWebcamResolution().height * SystemManager.settings.getNumberOfTrackers();
	}
	
	public void drawSelection(Graphics g, Color c) {
		if(g == null || c == null) { return; }
		
		g.setColor(c);
		
		g.drawOval(x - (SELECTION_SIZE/2), y - (SELECTION_SIZE/2), SELECTION_SIZE, SELECTION_SIZE);
	}
	
	public void draw(Graphics g) {
		if(g == null) { return; }
		
		int radius = 2;
		g.fillOval(x - radius, y - radius,radius * 2, radius * 2);
	}
	
	
	public String toString() {
		return "(" + x + ", " +  y + ")";
	}
	
}
