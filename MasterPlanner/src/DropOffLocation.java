import java.awt.Color;
import java.awt.Graphics;

public class DropOffLocation {
	
	private byte m_id;
	private Position m_position;
	private byte m_state;
	
	final public static int SIZE = (int) (2.8 * 3); // size in cm * pixel scaling
	final public static int SELECTION_RADIUS = SIZE + 6;
	
	public DropOffLocation(byte id, Position position) {
		m_id = id;
		m_position = position;
		m_state = DropOffLocationState.Empty;
	}
	
	public byte getID() {
		return m_id;
	}

	public Position getPosition() {
		return m_position;
	}
	
	public void setID(byte id) {
		m_id = id;
	}
	
	public boolean isFull() {
		return m_state == DropOffLocationState.Full;
	}
	
	public boolean isEmpty() {
		return m_state == DropOffLocationState.Empty;
	}
	
	public byte getState() {
		return m_state;
	}
	
	public boolean setPosition(Position position) {
		if(!Position.isValid(position)) { return false; }
		m_position = position;
		return true;
	}

	public boolean setState(byte state) {
		if(!DropOffLocationState.isValid(state)) { return false; }
		m_state = state;
		return true;
	}
	
	public void reset() {
		m_state = DropOffLocationState.Empty;
	}
	
	public void drawSelection(Graphics g, Color c) {
		if(g == null || c == null) { return; }
		
		g.setColor(c);
		
		g.drawOval(m_position.x - (Block.SELECTION_RADIUS/2), m_position.y - (Block.SELECTION_RADIUS/2), Block.SELECTION_RADIUS, Block.SELECTION_RADIUS);
	}
	
	public void draw(Graphics g) {
		if(g == null) { return; }
		
		g.setColor(SystemManager.settings.getDropOffLocationColour());
		
		g.drawOval(m_position.x - (SIZE/2), m_position.y - (SIZE/2), SIZE, SIZE);
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof DropOffLocation)) { return false; }
		DropOffLocation b = (DropOffLocation) o;
		return m_id == b.m_id;
	}
	
	public String toString() {
		return "Drop Off Location #" + m_id + " " + m_position + ": " + DropOffLocationState.toString(m_state);
	}
	
}
