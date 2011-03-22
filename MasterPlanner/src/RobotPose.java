import java.awt.Point;

public class RobotPose {
	
	private Position m_position;
	private int m_angle;
	
	public RobotPose(int x, int y, int angle) {
		this(new Position(x, y), angle);
	}
	
	public RobotPose(Vertex v, int angle) {
		this((v == null) ? new Position(-1, -1) : new Position(v.x, v.y), angle);
	}
	
	public RobotPose(Position position, int angle) {
		m_position = (position == null) ? new Position(-1, -1) : position;
		m_angle = angle; 
	}
	
	public int getX() {
		return m_position.x;
	}
	
	public int getY() {
		return m_position.y;
	}
	
	public Point getPoint() {
		return (Point) m_position;
	}
	
	public Vertex getVertex() {
		return new Vertex(m_position.x, m_position.y);
	}
	
	public int getAngleDegrees() {
		return m_angle;
	}
	
	public float getAngleRadians() {
		return (float) Math.toRadians(m_angle);
	}
	
	public void setX(int x) {
		m_position.x = x;
	}
	
	public void setY(int y) {
		m_position.y = y;
	}
	
	public void setPosition(Point p) {
		m_position = new Position(p.x, p.y);
	}
	
	public void setPosition(Vertex v) {
		m_position = new Position(v.x, v.y);
	}
	
	public void setAngleDegrees(int angle) {
		m_angle = angle;
	}
	
	public void setAngleRadians(float angle) {
		m_angle = (int) Math.toDegrees(angle);
	}
	
	public static boolean isValid(RobotPose pose) {
		return pose != null && pose.isValid();
	}
	
	public boolean isValid() {
		return m_position.x >= 0 &&
			   m_position.y >= 0 &&
			   m_position.x <= Constants.MAX_X &&
			   m_position.y <= Constants.MAX_Y &&
			   m_angle >= 0;
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof RobotPose)) { return false; }
		RobotPose p = (RobotPose) o;
		return m_position.equals(p.m_position) && m_angle == p.m_angle;
	}
	
	public String toString() {
		return m_position + ", angle";
	}

}
