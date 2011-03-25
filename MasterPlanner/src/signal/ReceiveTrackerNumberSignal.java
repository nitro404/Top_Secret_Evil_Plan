package signal;

import shared.*;

public class ReceiveTrackerNumberSignal extends Signal {
	
	private byte m_trackerID;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private ReceiveTrackerNumberSignal() {
		super(SignalType.ReceiveTrackerNumber);
	}
	
	public ReceiveTrackerNumberSignal(byte trackerID) {
		super(SignalType.ReceiveTrackerNumber);
		m_trackerID = trackerID;
	}
	
	public byte getTrackerID() {
		return m_trackerID;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_trackerID);
		return checksum;
	}
	
	public static ReceiveTrackerNumberSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ReceiveTrackerNumberSignal s2 = new ReceiveTrackerNumberSignal();
		
		s2.m_trackerID = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_trackerID);
		byteStream.addLong(checksum());
	}
	
	public String toString() {
		return super.toString() + " Tracker ID: " + m_trackerID;
	}
	
}
