package block;

import java.util.Vector;
import java.awt.*;
import java.awt.event.*;
import planner.*;
import shared.*;

public class BlockSystem implements MouseListener, MouseMotionListener {
	
	private byte m_activeBlockID;
	private Vector<Block> m_blocks;
	private Vector<DropOffLocation> m_dropOffLocations;
	
	private int m_selectedBlock;
	private int m_blockToMove;
	private int m_selectedDropOffLocation;
	private int m_dropOffLocationToMove;
	
	final public static Position[] defaultBlockPositions = {
		// zone 1 (top)
		new Position(34, 310),
		new Position(49, 416),
		new Position(255, 417),
		new Position(378, 415),
		new Position(541, 400),
		new Position(538, 319),
		// zone 2 (middle)
		new Position(61, 546),
		new Position(56, 873),
		new Position(261, 715),
		new Position(410, 716),
		new Position(592, 903),
		new Position(596, 538),
		// zone 3 (bottom)
		new Position(38, 1022),
		new Position(35, 1131),
		new Position(258, 1017),
		new Position(388, 1008),
		new Position(556, 1044),
		new Position(552, 1122)
	};
	
	final public static Position[] defaultDropOffLocations = {
		new Position(192, 694),
		new Position(190, 736),
		new Position(192, 653),
		new Position(190, 778),
		new Position(153, 676),
		new Position(153, 746),
		new Position(154, 646),
		new Position(153, 779),
		new Position(153, 711)
	};
	
	public BlockSystem() {
		m_blocks = new Vector<Block>(defaultBlockPositions.length);
		for(byte i=0;i<defaultBlockPositions.length;i++) {
			m_blocks.add(new Block(i, SystemManager.settings.getInitialBlockPosition(i)));
		}
		m_dropOffLocations = new Vector<DropOffLocation>(defaultDropOffLocations.length);
		for(byte i=0;i<defaultDropOffLocations.length;i++) {
			m_dropOffLocations.add(new DropOffLocation(i, SystemManager.settings.getDropOffLocation(i)));
		}
		m_activeBlockID = -1;
		m_selectedBlock = -1;
		m_blockToMove = -1;
		m_selectedDropOffLocation = -1;
		m_dropOffLocationToMove = -1;
	}
	
	public int numberOfBlocks() { return m_blocks.size(); }
	
	public int numberOfDeliveredBlocks() {
		int numberOfDeliveredBlocks = 0;
		for(int i=0;i<m_blocks.size();i++) {
			if(m_blocks.elementAt(i).getState() == BlockState.Delivered) {
				numberOfDeliveredBlocks++;
			}
		}
		return numberOfDeliveredBlocks;
	}
	
	public int numberOfDropOffLocations() { return m_dropOffLocations.size(); }
	
	public Block getBlock(byte blockID) {
		if(blockID < 0 || blockID >= m_blocks.size()) { return null; }
		return m_blocks.elementAt(blockID);
	}
	
	public Block getActiveBlock() {
		return (m_activeBlockID < 0 || m_activeBlockID >= m_blocks.size()) ? null : m_blocks.elementAt(m_activeBlockID);
	}
	
	public byte getActiveBlockID() {
		return m_activeBlockID;
	}
	
	public boolean hasActiveBlock() {
		return m_activeBlockID >= 0 && m_activeBlockID < m_blocks.size();
	}
	
	public void setActiveBlockID(byte blockID) {
		m_activeBlockID = (blockID < -1) ? -1 : blockID;
	}
	
	public DropOffLocation getDropOffLocation(byte dropOffLocationID) {
		if(dropOffLocationID < 0 || dropOffLocationID >= m_dropOffLocations.size()) { return null; }
		return m_dropOffLocations.elementAt(dropOffLocationID);
	}
	
	public boolean setBlockState(byte blockID, byte robotID, byte blockState) {
		if(blockID < 0 || blockID >= m_blocks.size() || !BlockState.isValid(blockState)) { return false; }
		return m_blocks.elementAt(blockID).setState(blockState);
	}
	
	public boolean setActualBlockPosition(byte blockID, Position actualBlockPosition) {
		if(blockID < 0 || blockID >= m_blocks.size() || !Position.isValid(actualBlockPosition)) { return false; }
		return m_blocks.elementAt(blockID).setActualPosition(actualBlockPosition);
	}
	
	public byte closestBlock(Position p) {
		double distance, shortestDistance = Math.sqrt(Math.pow(m_blocks.elementAt(0).getActualPosition().getX() - p.x, 2) + Math.pow(m_blocks.elementAt(0).getActualPosition().getY() - p.y, 2));
		byte shortestIndex = 0;
		for(byte i=1;i<m_blocks.size();i++) {
			distance = Math.sqrt(Math.pow(m_blocks.elementAt(i).getActualPosition().getX() - p.x, 2) + Math.pow(m_blocks.elementAt(i).getActualPosition().getY() - p.y, 2));
			if(distance < shortestDistance) {
				shortestDistance = distance;
				shortestIndex = i;
			}
		}
		return shortestIndex;
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			selectBlock(e.getPoint());
			m_blockToMove = m_selectedBlock;
			
			if(m_blockToMove == -1) {
				selectDropOffLocation(e.getPoint());
				m_dropOffLocationToMove = m_selectedDropOffLocation;
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON2) {
			m_blockToMove = -1;
			m_dropOffLocationToMove = -1;
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(m_blockToMove != -1) {
			m_blocks.elementAt(m_blockToMove).setInitialPosition(new Position(e.getX(), e.getY()));
		}
		else if(m_dropOffLocationToMove != -1) {
			m_dropOffLocations.elementAt(m_dropOffLocationToMove).setPosition(new Position(e.getX(), e.getY()));
		}
	}
	
	public void mouseMoved(MouseEvent e) { }
	
	public boolean selectBlock(Point p) {
		m_selectedBlock = -1;
		m_selectedDropOffLocation = -1;
		
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
	
	public Block getSelectedBlock(Point p) {
		if(p == null) { return null; }
		Position position = new Position(p);
		if(!position.isValid()) { return null; }
		
		for(int i=0;i<m_blocks.size();i++) {
			if(Math.sqrt(Math.pow(m_blocks.elementAt(i).getInitialPosition().getX() - p.x, 2) + Math.pow(m_blocks.elementAt(i).getInitialPosition().getY() - p.y, 2)) <= Block.SIZE / 2) {
				return m_blocks.elementAt(i);
			}
		}
		return null;
	}
	
	public boolean selectDropOffLocation(Point p) {
		m_selectedBlock = -1;
		m_selectedDropOffLocation = -1;
		
		if(p == null) { return false; }
		Position position = new Position(p);
		if(!position.isValid()) { return false; }
		
		for(int i=0;i<m_dropOffLocations.size();i++) {
			if(Math.sqrt(Math.pow(m_dropOffLocations.elementAt(i).getPosition().getX() - p.x, 2) + Math.pow(m_dropOffLocations.elementAt(i).getPosition().getY() - p.y, 2)) <= DropOffLocation.SIZE / 2) {
				m_selectedDropOffLocation = i;
				return true;
			}
		}
		return false;
	}
	
	public DropOffLocation getSelectedDropOffLocation(Point p) {
		if(p == null) { return null; }
		Position position = new Position(p);
		if(!position.isValid()) { return null; }
		
		for(int i=0;i<m_dropOffLocations.size();i++) {
			if(Math.sqrt(Math.pow(m_dropOffLocations.elementAt(i).getPosition().getX() - p.x, 2) + Math.pow(m_dropOffLocations.elementAt(i).getPosition().getY() - p.y, 2)) <= DropOffLocation.SIZE / 2) {
				return m_dropOffLocations.elementAt(i);
			}
		}
		return null;
	}
	
	public void reset() {
		for(int i=0;i<m_blocks.size();i++) {
			m_blocks.elementAt(i).reset();
		}
		for(int i=0;i<m_dropOffLocations.size();i++) {
			m_dropOffLocations.elementAt(i).reset();
		}
	}
	
	public void clearSelection() {
		m_selectedBlock = -1;
		m_blockToMove = -1;
		m_selectedDropOffLocation = -1;
		m_dropOffLocationToMove = -1;
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_dropOffLocations.size();i++) {
			m_dropOffLocations.elementAt(i).draw(g);
		}
		for(int i=0;i<m_blocks.size();i++) {
			m_blocks.elementAt(i).draw(g);
		}
	}
	
}
