package signal;

import shared.*;

public class BlockStateChangeSignal extends Signal {
	
	private byte m_blockID;
	private byte m_robotID;
	private byte m_blockState;
	
	final public static int LENGTH = (Byte.SIZE +
									  Byte.SIZE +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private BlockStateChangeSignal() {
		super(SignalType.BlockStateChange);
	}
	
	public BlockStateChangeSignal(byte blockID, byte robotID, byte blockState) {
		super(SignalType.BlockStateChange);
		m_blockID = blockID;
		m_robotID = robotID;
		m_blockState = blockState;
	}
	
	public byte getBlockID() {
		return m_blockID;
	}
	
	public byte getRobotID() {
		return m_robotID;
	}
	
	public byte getBlockState() {
		return m_blockState;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_blockID);
		checksum += ByteStream.getChecksum(m_robotID);
		checksum += ByteStream.getChecksum(m_blockState);
		return checksum;
	}
	
	public static BlockStateChangeSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		BlockStateChangeSignal s2 = new BlockStateChangeSignal();
		
		s2.m_blockID = byteStream.nextByte();
		s2.m_robotID = byteStream.nextByte();
		s2.m_blockState = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_blockID);
		byteStream.addByte(m_robotID);
		byteStream.addByte(m_blockState);
		byteStream.addLong(checksum());
	}
	
}
