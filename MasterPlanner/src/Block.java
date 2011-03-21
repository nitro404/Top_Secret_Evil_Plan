import java.awt.Color;
import java.awt.Graphics2D;

public class Block {
	
	private int m_id;
	private Position m_actualPosition;
	private Position m_estimatedPosition;
	private int m_state;
	
	final public static int SIZE = (int) (2.8 * 3); // size in cm * pixel scaling
	final public static Color DEFAULT_COLOUR = Color.ORANGE;
	final public static Color MISSING_COLOUR = Color.GRAY;
	
	public Block(int id, Position estimatedPosition) {
		m_id = id;
		m_actualPosition = new Position(-1, -1);
		m_estimatedPosition = estimatedPosition;
		m_state = BlockState.Unknown;
	}
	
	public Position getActualPosition() {
		return m_actualPosition;
	}
	
	public Position getEstimatedPosition() {
		return m_estimatedPosition;
	}
	
	public int getState() {
		return m_state;
	}
	
	public boolean setActualPosition(Position actualPosition) {
		if(!Position.isValid(actualPosition)) { return false; }
		m_actualPosition = actualPosition;
		return true;
	}
	
	public boolean setEstimatedPosition(Position estimatedPosition) {
		if(!Position.isValid(estimatedPosition)) { return false; }
		m_estimatedPosition = estimatedPosition;
		return true;
	}
	
	public boolean setState(int state) {
		if(!BlockState.isValid(state)) { return false; }
		m_state = state;
		return true;
	}
	
	public void draw(Graphics2D g) {
		if(g == null) { return; }
		
		g.setColor(m_state == BlockState.Missing ? MISSING_COLOUR : DEFAULT_COLOUR);
		
		if(m_state == BlockState.Unknown || m_state == BlockState.Missing) {
			g.drawOval(m_estimatedPosition.x - (SIZE/2), m_estimatedPosition.y - (SIZE/2), SIZE, SIZE);
		}
		else {
			g.fillOval(m_actualPosition.x - (SIZE/2), m_actualPosition.y - (SIZE/2), SIZE, SIZE);
		}
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Block)) { return false; }
		Block b = (Block) o;
		return m_id == b.m_id;
	}
	
	public String toString() {
		return "Block #" + m_id + " " + (m_state == BlockState.Unknown ? m_estimatedPosition : m_actualPosition) + ": " + BlockState.toString(m_state);
	}
	
}
