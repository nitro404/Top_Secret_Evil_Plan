package pot;

import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.*;
import shared.*;

public class PotSystem implements MouseListener, MouseMotionListener {
	
	private Vector<Pot> m_pots;
	
	private int m_selectedPot;
	private int m_potToMove;
	
	final public static Position[] defaultPotPositions = {
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
	};
	
	public PotSystem() {
		m_pots = new Vector<Pot>(defaultPotPositions.length);
		for(byte i=0;i<defaultPotPositions.length;i++) {
			m_pots.add(new Pot(i, defaultPotPositions[i]));
		}
		m_selectedPot = -1;
		m_potToMove = -1;
	}
	
	public Pot getPot(byte potID) {
		if(potID < 0 || potID >= m_pots.size()) { return null; }
		return m_pots.elementAt(potID);
	}
	
	public boolean setPotState(byte potID, byte robotID, byte potState) {
		if(potID < 0 || potID >= m_pots.size() || !PotState.isValid(potState)) { return false; }
		return m_pots.elementAt(potID).setState(potState);
	}
	
	public boolean setActualPotPosition(byte potID, Position potPosition) {
		if(potID < 0 || potID >= m_pots.size() || !Position.isValid(potPosition)) { return false; }
		return m_pots.elementAt(potID).setActualPosition(potPosition);
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			selectPot(e.getPoint());
			m_potToMove = m_selectedPot;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			m_potToMove = -1;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(m_potToMove != -1) {
			m_pots.elementAt(m_potToMove).setInitialPosition(new Position(e.getX(), e.getY()));
		}
	}
	
	public void mouseMoved(MouseEvent e) { }
	
	public boolean selectPot(Point p) {
		m_selectedPot = -1;
		
		if(p == null) { return false; }
		Position position = new Position(p);
		if(!position.isValid()) { return false; }
		
		for(int i=0;i<m_pots.size();i++) {
			if(Math.sqrt(Math.pow(m_pots.elementAt(i).getInitialPosition().getX() - p.x, 2) + Math.pow(m_pots.elementAt(i).getInitialPosition().getY() - p.y, 2)) <= Pot.SIZE / 2) {
				m_selectedPot = i;
				return true;
			}
		}
		return false;
	}

	public void reset() {
		for(int i=0;i<m_pots.size();i++) {
			m_pots.elementAt(i).reset();
		}
	}
	
	public void clearSelection() {
		m_selectedPot = -1;
		m_potToMove = -1;
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_pots.size();i++) {
			m_pots.elementAt(i).draw(g);
		}
	}
	
}
