package signal;

import shared.*;

public class RobotStateChangeSignal extends Signal {
	
	private byte m_robotID;
	private byte m_robotState;
	
	final public static int LENGTH = (Byte.SIZE +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private RobotStateChangeSignal() {
		super(SignalType.BlockStateChange);
	}
	
	public RobotStateChangeSignal(byte robotID, byte robotState) {
		super(SignalType.BlockStateChange);
		m_robotID = robotID;
		m_robotState = robotState;
	}

	public byte getRobotID() {
		return m_robotID;
	}
	
	public byte getRobotState() {
		return m_robotState;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_robotID);
		checksum += ByteStream.getChecksum(m_robotState);
		return checksum;
	}
	
	public static RobotStateChangeSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		RobotStateChangeSignal s2 = new RobotStateChangeSignal();
		
		s2.m_robotID = byteStream.nextByte();
		s2.m_robotState = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_robotID);
		byteStream.addByte(m_robotState);
		byteStream.addLong(checksum());
	}
	
}
