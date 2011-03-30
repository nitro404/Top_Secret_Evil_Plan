package block;

import java.awt.Graphics2D;
import planner.*;
import shared.*;

public class Block {
	
	private byte m_id;
	private Position m_actualPosition;
	private Position m_defaultPosition;
	private byte m_state;
	
	final public static int SIZE = (int) (2.8 * 3); // size in cm * pixel scaling
	
	public Block(byte id, Position defaultPosition) {
		m_id = id;
		m_actualPosition = new Position(-1, -1);
		m_defaultPosition = defaultPosition;
		m_state = BlockState.Unknown;
	}
	
	public byte getID() {
		return m_id;
	}

	public Position getActualPosition() {
		return m_actualPosition;
	}
	
	public Position getDefaultPosition() {
		return m_defaultPosition;
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
	
	public boolean setDefaultPosition(Position defaultPosition) {
		if(!Position.isValid(defaultPosition)) { return false; }
		m_defaultPosition = defaultPosition;
		return true;
	}

	public boolean setState(byte state) {
		if(!BlockState.isValid(state)) { return false; }
		m_state = state;
		return true;
	}
	
	public void draw(Graphics2D g) {
		if(g == null) { return; }
		
		g.setColor(m_state == BlockState.Missing ? SystemManager.settings.getMissingColour() : SystemManager.settings.getBlockColour());
		
		if(m_state == BlockState.Unknown || m_state == BlockState.Missing) {
			g.drawOval(m_defaultPosition.x - (SIZE/2), m_defaultPosition.y - (SIZE/2), SIZE, SIZE);
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
		return "Block #" + m_id + " " + (m_state == BlockState.Unknown ? m_defaultPosition : m_actualPosition) + ": " + BlockState.toString(m_state);
	}
	
}
