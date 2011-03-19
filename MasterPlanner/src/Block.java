import java.awt.Color;
import java.awt.Graphics2D;

public class Block {
	
	private int m_id;
	private Position m_actualPosition;
	private Position m_estimatedPosition;
	private int m_state;
	
	public static int size = 2 * 3; // size in cm * pixel scaling
	public static Color colour = Color.ORANGE;
	
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
		
		g.setColor(colour);
		
		if(m_state == BlockState.Unknown) {
			g.drawOval(m_estimatedPosition.x - (size/2), m_estimatedPosition.y - (size/2), size, size);
		}
		else {
			g.fillOval(m_actualPosition.x - (size/2), m_actualPosition.y - (size/2), size, size);
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
