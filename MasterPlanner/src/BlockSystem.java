import java.util.Vector;
import java.awt.Graphics2D;

public class BlockSystem {
	
	private Vector<Block> m_blocks;
	
	final public static Position[] estimatedBlockPositions = {
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
		m_blocks = new Vector<Block>(estimatedBlockPositions.length);
		for(int i=0;i<estimatedBlockPositions.length;i++) {
			m_blocks.add(new Block(i, estimatedBlockPositions[i]));
		}
	}
	
	public Block getBlock(int id) {
		if(id < 0 || id >= m_blocks.size()) { return null; }
		return m_blocks.elementAt(id);
	}
	
	public boolean setBlockState(int id, int state) {
		if(id < 0 || id >= m_blocks.size() || !BlockState.isValid(state)) { return false; }
		return m_blocks.elementAt(id).setState(state);
	}
	
	public boolean updateActualBlockPosition(int id, Position actualPosition) {
		if(id < 0 || id >= m_blocks.size() || !Position.isValid(actualPosition)) { return false; }
		return m_blocks.elementAt(id).setActualPosition(actualPosition);
	}
	
	public void draw(Graphics2D g) {
		for(int i=0;i<m_blocks.size();i++) {
			m_blocks.elementAt(i).draw(g);
		}
	}
	
}
