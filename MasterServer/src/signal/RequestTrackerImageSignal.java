package signal;

import shared.*;

public class RequestTrackerImageSignal extends Signal {
	
	private byte m_sourceTrackerID;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private RequestTrackerImageSignal() {
		super(SignalType.RequestTrackerImage);
	}
	
	public RequestTrackerImageSignal(byte sourceTrackerID) {
		super(SignalType.RequestTrackerImage);
		m_sourceTrackerID = sourceTrackerID;
	}
	
	public byte getSourceTrackerID() {
		return m_sourceTrackerID;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_sourceTrackerID);
		return checksum;
	}
	
	public static RequestTrackerImageSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		RequestTrackerImageSignal s2 = new RequestTrackerImageSignal();
		
		s2.m_sourceTrackerID = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_sourceTrackerID);
		byteStream.addLong(checksum());
	}
	
	public String toString() {
		return super.toString() + " Source Tracker ID: " + m_sourceTrackerID;
	}
	
}
