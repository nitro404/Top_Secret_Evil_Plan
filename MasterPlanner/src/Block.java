import java.awt.Color;
import java.awt.Graphics;

public class Block {
	
	private byte m_id;
	private Position m_actualPosition;
	private Position m_initialPosition;
	private byte m_state;
	
	final public static int SIZE = (int) (2.8 * 3); // size in cm * pixel scaling
	final public static int SELECTION_RADIUS = SIZE + 6;
	
	public Block(byte id, Position position) {
		m_id = id;
		m_actualPosition = position;
		m_initialPosition = position;
		m_state = BlockState.Unknown;
	}
	
	public byte getID() {
		return m_id;
	}

	public Position getActualPosition() {
		return m_actualPosition;
	}
	
	public Position getInitialPosition() {
		return m_initialPosition;
	}
	
	public void setID(byte id) {
		m_id = id;
	}

	public byte getState() {
		return m_state;
	}
	
	public boolean setActualPosition(Position actualPosition) {
		if(!Position.isValid(actualPosition)) { return false; }
		m_actualPosition = actualPosition;
		return true;
	}
	
	public boolean setInitialPosition(Position initialPosition) {
		if(!Position.isValid(initialPosition)) { return false; }
		m_initialPosition = initialPosition;
		return true;
	}

	public boolean setState(byte state) {
		if(!BlockState.isValid(state)) { return false; }
		m_state = state;
		return true;
	}
	
	public void reset() {
		m_actualPosition = m_initialPosition;
		m_state = BlockState.Unknown;
	}
	
	public void drawSelection(Graphics g, Color c) {
		if(g == null || c == null) { return; }
		
		g.setColor(c);
		
		if(m_state == BlockState.Unknown || m_state == BlockState.Missing) {
			g.drawOval(m_initialPosition.x - (SELECTION_RADIUS/2), m_initialPosition.y - (SELECTION_RADIUS/2), SELECTION_RADIUS, SELECTION_RADIUS);
		}
		else {
			g.drawOval(m_actualPosition.x - (SELECTION_RADIUS/2), m_actualPosition.y - (SELECTION_RADIUS/2), SELECTION_RADIUS, SELECTION_RADIUS);
		}
	}
	
	public void draw(Graphics g) {
		if(g == null) { return; }
		
		g.setColor(m_state == BlockState.Missing ? SystemManager.settings.getMissingColour() : SystemManager.settings.getBlockColour());
		
		if(m_state == BlockState.Unknown || m_state == BlockState.Missing) {
			g.drawOval(m_initialPosition.x - (SIZE/2), m_initialPosition.y - (SIZE/2), SIZE, SIZE);
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
		return "Block #" + m_id + " " + (m_state == BlockState.Unknown ? m_initialPosition : m_actualPosition) + ": " + BlockState.toString(m_state);
	}
	
}
