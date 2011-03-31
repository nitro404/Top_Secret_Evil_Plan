package block;

import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import planner.*;
import shared.*;

public class BlockSystem implements MouseListener, MouseMotionListener {
	
	private Vector<Block> m_blocks;
	
	private int m_selectedBlock;
	private int m_blockToMove;
	
	final public static Position[] defaultBlockPositions = {
		// zone 1 (top)
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		// zone 2 (middle)
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		// zone 3 (bottom)
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0),
		new Position(0, 0)
	};
	
	public BlockSystem() {
		m_blocks = new Vector<Block>(defaultBlockPositions.length);
		for(byte i=0;i<defaultBlockPositions.length;i++) {
			m_blocks.add(new Block(i, SystemManager.settings.getInitialBlockPosition(i)));
		}
		m_selectedBlock = -1;
		m_blockToMove = -1;
	}
	
	public int numberOfBlocks() { return m_blocks.size(); }
	
	public Block getBlock(byte blockID) {
		if(blockID < 0 || blockID >= m_blocks.size()) { return null; }
		return m_blocks.elementAt(blockID);
	}
	
	public boolean setBlockState(byte blockID, byte robotID, byte blockState) {
		if(blockID < 0 || blockID >= m_blocks.size() || !BlockState.isValid(blockState)) { return false; }
		return m_blocks.elementAt(blockID).setState(blockState);
	}
	
	public boolean setActualBlockPosition(byte blockID, Position actualBlockPosition) {
		if(blockID < 0 || blockID >= m_blocks.size() || !Position.isValid(actualBlockPosition)) { return false; }
		return m_blocks.elementAt(blockID).setActualPosition(actualBlockPosition);
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			selectPot(e.getPoint());
			m_blockToMove = m_selectedBlock;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			m_blockToMove = -1;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(m_blockToMove != -1) {
			m_blocks.elementAt(m_blockToMove).setInitialPosition(new Position(e.getX(), e.getY()));
		}
	}
	
	public void mouseMoved(MouseEvent e) { }
	
	public boolean selectPot(Point p) {
		m_selectedBlock = -1;
		
		if(p == null) { return false; }
		Position position = new Position(p);
		if(!position.isValid()) { return false; }
		
		for(int i=0;i<m_blocks.size();i++) {
			if(Math.sqrt(Math.pow(m_blocks.elementAt(i).getInitialPosition().getX() - p.x, 2) + Math.pow(m_blocks.elementAt(i).getInitialPosition().getY() - p.y, 2)) <= Block.SIZE / 2) {
				m_selectedBlock = i;
				return true;
			}
		}
		return false;
	}
	
	public void reset() {
		for(int i=0;i<m_blocks.size();i++) {
			m_blocks.elementAt(i).reset();
		}
	}
	
	public void clearSelection() {
		m_selectedBlock = -1;
		m_blockToMove = -1;
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_blocks.size();i++) {
			m_blocks.elementAt(i).draw(g);
		}
	}
	
}
