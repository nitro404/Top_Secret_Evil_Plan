package pot;

import java.util.Vector;
import java.awt.Graphics2D;

import shared.Position;

public class PotSystem {
	
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
	
	public Pot getBlock(byte id) {
		if(id < 0 || id >= m_pots.size()) { return null; }
		return m_pots.elementAt(id);
	}
	
	public boolean setBlockState(byte id, byte state) {
		if(id < 0 || id >= m_pots.size() || !PotState.isValid(state)) { return false; }
		return m_pots.elementAt(id).setState(state);
	}
	
	public boolean updatePosition(byte id, Position position) {
		if(id < 0 || id >= m_pots.size() || !Position.isValid(position)) { return false; }
		return m_pots.elementAt(id).setPosition(position);
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_pots.size();i++) {
			m_pots.elementAt(i).draw(g);
		}
	}
	
}
