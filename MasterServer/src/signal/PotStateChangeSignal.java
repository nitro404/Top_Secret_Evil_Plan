package signal;

import shared.*;

public class PotStateChangeSignal extends Signal {
	
	private byte m_robotID;
	private byte m_potState;
	
	final public static int LENGTH = (Byte.SIZE +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private PotStateChangeSignal() {
		super(SignalType.BlockStateChange);
	}
	
	public PotStateChangeSignal(byte robotID, byte potState) {
		super(SignalType.BlockStateChange);
		m_robotID = robotID;
		m_potState = potState;
	}

	public byte getRobotID() {
		return m_robotID;
	}
	
	public byte getPotState() {
		return m_potState;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_robotID);
		checksum += ByteStream.getChecksum(m_potState);
		return checksum;
	}
	
	public static PotStateChangeSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		PotStateChangeSignal s2 = new PotStateChangeSignal();
		
		s2.m_robotID = byteStream.nextByte();
		s2.m_potState = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_robotID);
		byteStream.addByte(m_potState);
		byteStream.addLong(checksum());
	}
	
}
