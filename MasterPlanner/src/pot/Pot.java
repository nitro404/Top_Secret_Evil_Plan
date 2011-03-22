package pot;

import java.awt.Color;
import java.awt.Graphics2D;

import shared.Position;

public class Pot {
	
	private byte m_id;
	private Position m_position;
	private byte m_state;
	
	final public static int SIZE = (int) (21.6 * 3); // size in cm * pixel scaling
	final public static Color DEFAULT_COLOUR = Color.BLUE;
	
	public Pot(byte id, Position position) {
		m_id = id;
		m_position = position;
		m_state = PotState.Origin;
	}
	
	public byte getID() {
		return m_id;
	}

	public Position getPosition() {
		return m_position;
	}

	public byte getState() {
		return m_state;
	}
	
	public void setID(byte id) {
		m_id = id;
	}

	public boolean setPosition(Position position) {
		if(!Position.isValid(position)) { return false; }
		m_position = position;
		return true;
	}

	public boolean setState(byte state) {
		if(!PotState.isValid(state)) { return false; }
		m_state = state;
		return true;
	}
	
	public void draw(Graphics2D g) {
		if(g == null) { return; }
		
		g.setColor(DEFAULT_COLOUR);
		
		if(m_state == PotState.Delivered) {
			g.fillOval(m_position.x - (SIZE/2), m_position.y - (SIZE/2), SIZE, SIZE);
		}
		else {
			g.drawOval(m_position.x - (SIZE/2), m_position.y - (SIZE/2), SIZE, SIZE);
		}
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Pot)) { return false; }
		Pot p = (Pot) o;
		return m_id == p.m_id;
	}
	
	public String toString() {
		return "Pot #" + m_id + " " + m_position + ": " + PotState.toString(m_state);
	}
	
}
