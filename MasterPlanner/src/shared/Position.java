package shared;

import java.awt.Point;

import planner.SystemManager;

public class Position extends Point {
	
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
	
}
