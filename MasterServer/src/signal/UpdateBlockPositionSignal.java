package signal;

import shared.*;

public class UpdateBlockPositionSignal extends Signal {
	
	private byte m_blockID;
	private int m_x;
	private int m_y;
	
	final public static int LENGTH = (Byte.SIZE +
									  Integer.SIZE +
									  Integer.SIZE +
									  Long.SIZE) / 8;
	
	private UpdateBlockPositionSignal() {
		super(SignalType.BlockStateChange);
	}
	
	public UpdateBlockPositionSignal(byte blockID, int x, int y) {
		super(SignalType.BlockStateChange);
		m_blockID = blockID;
		m_x = x;
		m_y = y;
	}

	public byte getBlockID() {
		return m_blockID;
	}
	
	public int getX() {
		return m_x;
	}
	
	public int getY() {
		return m_y;
	}
	
	public Position getPosition() {
		return new Position(m_x, m_y);
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_blockID);
		checksum += ByteStream.getChecksum(m_x);
		checksum += ByteStream.getChecksum(m_y);
		return checksum;
	}
	
	public static UpdateBlockPositionSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		UpdateBlockPositionSignal s2 = new UpdateBlockPositionSignal();
		
		s2.m_blockID = byteStream.nextByte();
		s2.m_x = byteStream.nextInteger();
		s2.m_y = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_blockID);
		byteStream.addInteger(m_x);
		byteStream.addInteger(m_y);
		byteStream.addLong(checksum());
	}
	
}
