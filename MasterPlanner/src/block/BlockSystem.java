package block;

import java.util.Vector;
import java.awt.Graphics2D;
import shared.*;

public class BlockSystem {
	
	private Vector<Block> m_blocks;
	
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
			m_blocks.add(new Block(i, defaultBlockPositions[i]));
		}
	}
	
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
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_blocks.size();i++) {
			m_blocks.elementAt(i).draw(g);
		}
	}
	
}
