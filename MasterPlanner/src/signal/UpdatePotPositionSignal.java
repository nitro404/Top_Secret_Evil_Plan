package signal;

import shared.*;

public class UpdatePotPositionSignal extends Signal {
	
	private byte m_potID;
	private int m_x;
	private int m_y;
	
	final public static int LENGTH = (Byte.SIZE +
									  Integer.SIZE +
									  Integer.SIZE +
									  Long.SIZE) / 8;
	
	private UpdatePotPositionSignal() {
		super(SignalType.UpdatePotPosition);
	}
	
	public UpdatePotPositionSignal(byte potID, int x, int y) {
		super(SignalType.UpdatePotPosition);
		m_potID = potID;
		m_x = x;
		m_y = y;
	}

	public byte getPotID() {
		return m_potID;
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
		checksum += ByteStream.getChecksum(m_potID);
		checksum += ByteStream.getChecksum(m_x);
		checksum += ByteStream.getChecksum(m_y);
		return checksum;
	}
	
	public static UpdatePotPositionSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		UpdatePotPositionSignal s2 = new UpdatePotPositionSignal();
		
		s2.m_potID = byteStream.nextByte();
		s2.m_x = byteStream.nextInteger();
		s2.m_y = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_potID);
		byteStream.addInteger(m_x);
		byteStream.addInteger(m_y);
		byteStream.addLong(checksum());
	}
	
	public String toString() {
		return super.toString() + " Pot ID: " + m_potID + " Position: (" + m_x + ", " + m_y + ")";
	}
	
}
