package signal;

import pot.*;
import shared.*;

public class PotStateChangeSignal extends Signal {
	
	private byte m_potID;
	private byte m_robotID;
	private byte m_potState;
	
	final public static int LENGTH = (Byte.SIZE +
									  Byte.SIZE +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private PotStateChangeSignal() {
		super(SignalType.PotStateChange);
	}
	
	public PotStateChangeSignal(byte potID, byte robotID, byte potState) {
		super(SignalType.PotStateChange);
		m_potID = potID;
		m_robotID = robotID;
		m_potState = potState;
	}
	
	public byte getPotID() {
		return m_potID;
	}

	public byte getRobotID() {
		return m_robotID;
	}
	
	public byte getPotState() {
		return m_potState;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_potID);
		checksum += ByteStream.getChecksum(m_robotID);
		checksum += ByteStream.getChecksum(m_potState);
		return checksum;
	}
	
	public static PotStateChangeSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		PotStateChangeSignal s2 = new PotStateChangeSignal();
		
		s2.m_potID = byteStream.nextByte();
		s2.m_robotID = byteStream.nextByte();
		s2.m_potState = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_potID);
		byteStream.addByte(m_robotID);
		byteStream.addByte(m_potState);
		byteStream.addLong(checksum());
	}

	public String toString() {
		return super.toString() + " Pot ID: " + m_potID + " Robot ID: " + m_robotID + " Pot State " + PotState.toString(m_potState);
	}
	
}
