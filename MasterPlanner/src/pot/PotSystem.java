package pot;

import java.util.Vector;
import java.awt.Graphics2D;
import java.awt.event.*;
import shared.*;

public class PotSystem implements MouseListener, MouseMotionListener {
	
	private Vector<Pot> m_pots;
	
	final public static Position[] estimatedPotPositions = {
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
	};
	
	public PotSystem() {
		m_pots = new Vector<Pot>(estimatedPotPositions.length);
		for(byte i=0;i<estimatedPotPositions.length;i++) {
			m_pots.add(new Pot(i, estimatedPotPositions[i]));
		}
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
		return m_pots.elementAt(potID).setPosition(potPosition);
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void mousePressed(MouseEvent e) {
		
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
	
	public void mouseDragged(MouseEvent e) {
		
	}
	
	public void mouseMoved(MouseEvent e) {
		
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_pots.size();i++) {
			m_pots.elementAt(i).draw(g);
		}
	}
	
}
