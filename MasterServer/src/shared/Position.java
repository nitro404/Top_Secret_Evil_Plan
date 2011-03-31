package shared;

import java.awt.Point;

public class Position extends Point {
	
	final public static int MAX_X = 640;
	final public static int MAX_Y = 480 * 3;
	
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
		return x >= 0 && y >= 0 && x < MAX_X && y < MAX_Y; 
	}
	
	public static boolean isValid(Position p) {
		return p != null && p.isValid();
	}
	
	public static boolean isValid(Point p) {
		return p != null && p.x >= 0 && p.y >= 0 && p.x < MAX_X && p.y < MAX_Y;
	}

	public String toString() {
		return "(" + x + ", " +  y + ")";
	}
	
}
