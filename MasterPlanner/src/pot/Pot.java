package pot;

import gui.EditMode;

import java.awt.Graphics2D;
import planner.*;
import shared.*;

public class Pot {
	
	private byte m_id;
	private Position m_actualPosition;
	private Position m_initialPosition;
	private byte m_state;
	
	final public static int SIZE = (int) (21.6 * 3); // size in cm * pixel scaling
	
	public Pot(byte id, Position position) {
		m_id = id;
		m_actualPosition = position;
		m_initialPosition = position;
		m_state = PotState.Origin;
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

	public byte getState() {
		return m_state;
	}
	
	public void setID(byte id) {
		m_id = id;
	}

	public boolean setActualPosition(Position actualPosition) {
		if(!Position.isValid(actualPosition)) { return false; }
		m_actualPosition = actualPosition;
		return true;
	}
	
	public boolean setInitialPosition(Position position) {
		if(!Position.isValid(position)) { return false; }
		m_initialPosition = position;
		return true;
	}

	public boolean setState(byte state) {
		if(!PotState.isValid(state)) { return false; }
		m_state = state;
		return true;
	}
	
	public void reset() {
		m_actualPosition = m_initialPosition;
		m_state = PotState.Origin;
	}
	
	public void draw(Graphics2D g) {
		if(g == null) { return; }
		
		g.setColor(SystemManager.settings.getPotColour());
		
		Position p = SystemManager.isStarted() ? m_actualPosition : m_initialPosition ;
		
		if(m_state == PotState.Delivered) {
			g.fillOval(p.x - (SIZE/2), p.y - (SIZE/2), SIZE, SIZE);
		}
		else {
			g.drawOval(p.x - (SIZE/2), p.y - (SIZE/2), SIZE, SIZE);
		}
	}
	
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Pot)) { return false; }
		Pot p = (Pot) o;
		return m_id == p.m_id;
	}
	
	public String toString() {
		return "Pot #" + m_id + " " + m_actualPosition + ": " + PotState.toString(m_state);
	}
	
}
