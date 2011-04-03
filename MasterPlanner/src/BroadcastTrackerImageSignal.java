import java.io.DataInputStream;
import java.awt.image.BufferedImage;

public class BroadcastTrackerImageSignal extends Signal {
	
	private byte m_sourceTrackerNumber;
	private BufferedImage m_sourceTrackerImage;
	
	final public static int LENGTH = (Byte.SIZE +
									  Long.SIZE + 
									  Integer.SIZE) / 8;
	
	private BroadcastTrackerImageSignal() {
		super(SignalType.BroadcastTrackerImage);
	}
	
	public BroadcastTrackerImageSignal(byte sourceTrackerNumber, BufferedImage sourceTrackerImage) {
		super(SignalType.BroadcastTrackerImage);
		m_sourceTrackerNumber = sourceTrackerNumber;
		m_sourceTrackerImage = sourceTrackerImage;
	}
	
	public byte getSourceTrackerNumber() {
		return m_sourceTrackerNumber;
	}
	
	public BufferedImage getSourceTrackerImage() {
		return m_sourceTrackerImage;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_sourceTrackerNumber);
		checksum += ByteStream.getChecksum(m_sourceTrackerImage);
		return checksum;
	}
	
	public static BroadcastTrackerImageSignal readFrom(ByteStream byteStream, DataInputStream in) {
		if(byteStream == null) { return null; }
		
		BroadcastTrackerImageSignal s2 = new BroadcastTrackerImageSignal();
		
		s2.m_sourceTrackerNumber = byteStream.nextByte();
		byteStream.nextLong(); // ignore checksum
		int dataLength = byteStream.nextInteger();
		ByteStream imageStream = ByteStream.readFrom(in, dataLength);
		if(imageStream == null) { return null; }
		s2.m_sourceTrackerImage = imageStream.nextBufferedImage(dataLength);
		//if(checksum != s2.checksum()) { return null; } // cannot run checksum (jpeg is lossy and will be encoded differently each time)
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_sourceTrackerNumber);
		byteStream.addLong(checksum());
		byteStream.addBufferedImage(m_sourceTrackerImage);
	}
	
	public String toString() {
		return super.toString() + " Source Tracker ID: " + m_sourceTrackerNumber;
	}
	
}
