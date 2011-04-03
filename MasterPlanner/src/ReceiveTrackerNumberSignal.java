public class ReceiveTrackerNumberSignal extends Signal {
	
	private byte m_trackerNumber;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE) / 8;
	
	private ReceiveTrackerNumberSignal() {
		super(SignalType.ReceiveTrackerNumber);
	}
	
	public ReceiveTrackerNumberSignal(byte trackerNumber) {
		super(SignalType.ReceiveTrackerNumber);
		m_trackerNumber = trackerNumber;
	}
	
	public byte getTrackerNumber() {
		return m_trackerNumber;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_trackerNumber);
		return checksum;
	}
	
	public static ReceiveTrackerNumberSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		ReceiveTrackerNumberSignal s2 = new ReceiveTrackerNumberSignal();
		
		s2.m_trackerNumber = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_trackerNumber);
		byteStream.addLong(checksum());
	}
	
	public String toString() {
		return super.toString() + " Tracker ID: " + m_trackerNumber;
	}
	
}
