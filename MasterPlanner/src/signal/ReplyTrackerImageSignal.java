package signal;

import java.io.DataInputStream;
import java.awt.image.BufferedImage;
import shared.*;

public class ReplyTrackerImageSignal extends Signal {
	
	private byte m_sourceTrackerID;
	private byte m_destinationTrackerID;
	private BufferedImage m_sourceTrackerImage;
	
	final public static int LENGTH = (Byte.SIZE +
									  Byte.SIZE +
									  Long.SIZE +
									  Integer.SIZE) / 8;
	
	private ReplyTrackerImageSignal() {
		super(SignalType.ReplyTrackerImage);
	}
	
	public ReplyTrackerImageSignal(byte sourceTrackerID, byte destinationTrackerID, BufferedImage sourceTrackerImage) {
		super(SignalType.ReplyTrackerImage);
		m_sourceTrackerID = sourceTrackerID;
		m_destinationTrackerID = destinationTrackerID;
		m_sourceTrackerImage = sourceTrackerImage;
	}
	
	public byte getSourceTrackerID() {
		return m_sourceTrackerID;
	}
	
	public byte getDestinationTrackerID() {
		return m_destinationTrackerID;
	}
	
	public BufferedImage getSourceTrackerImage() {
		return m_sourceTrackerImage;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_sourceTrackerID);
		checksum += ByteStream.getChecksum(m_destinationTrackerID);
		checksum += ByteStream.getChecksum(m_sourceTrackerImage);
		return checksum;
	}
	
	public static ReplyTrackerImageSignal readFrom(ByteStream byteStream, DataInputStream in) {
		if(byteStream == null) { return null; }
		
		ReplyTrackerImageSignal s2 = new ReplyTrackerImageSignal();
		
		s2.m_sourceTrackerID = byteStream.nextByte();
		s2.m_destinationTrackerID = byteStream.nextByte();
		byteStream.nextLong(); // ignore checksum
		int dataLength = byteStream.nextInteger();
		ByteStream imageStream = ByteStream.readFrom(in, dataLength);
		if(imageStream == null) { return null; }
		s2.m_sourceTrackerImage = imageStream.nextBufferedImage(dataLength);
		//if(checksum != s2.checksum()) { return null; }  // cannot run checksum (jpeg is lossy and will be encoded differently each time)
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_sourceTrackerID);
		byteStream.addByte(m_destinationTrackerID);
		byteStream.addLong(checksum());
		byteStream.addBufferedImage(m_sourceTrackerImage);
	}
	
	public String toString() {
		return super.toString() + " Source Tracker ID: " + m_sourceTrackerID + " Destination Tracker ID: " + m_destinationTrackerID;
	}
	
}
