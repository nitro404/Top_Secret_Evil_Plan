package planner;

import java.awt.Point;

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
		return x >= 0 && y >= 0 && x < Constants.MAX_X && y < Constants.MAX_Y; 
	}
	
	public static boolean isValid(Position p) {
		return p != null && p.isValid();
	}
	
	public static boolean isValid(Point p) {
		return p != null && p.x >= 0 && p.y >= 0 && p.x < Constants.MAX_X && p.y < Constants.MAX_Y;
	}
	
}
