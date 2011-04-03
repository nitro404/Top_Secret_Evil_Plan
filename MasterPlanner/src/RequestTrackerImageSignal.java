public class RequestTrackerImageSignal extends Signal {
	
	private byte m_sourceTrackerNumber;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private RequestTrackerImageSignal() {
		super(SignalType.RequestTrackerImage);
	}
	
	public RequestTrackerImageSignal(byte sourceTrackerNumber) {
		super(SignalType.RequestTrackerImage);
		m_sourceTrackerNumber = sourceTrackerNumber;
	}
	
	public byte getSourceTrackerNumber() {
		return m_sourceTrackerNumber;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_sourceTrackerNumber);
		return checksum;
	}
	
	public static RequestTrackerImageSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		RequestTrackerImageSignal s2 = new RequestTrackerImageSignal();
		
		s2.m_sourceTrackerNumber = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_sourceTrackerNumber);
		byteStream.addLong(checksum());
	}
	
	public String toString() {
		return super.toString() + " Source Tracker ID: " + m_sourceTrackerNumber;
	}
	
}
