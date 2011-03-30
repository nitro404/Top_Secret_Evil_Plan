package signal;

import shared.*;

public class UpdateActualRobotPositionSignal extends Signal {
	
	private byte m_robotID;
	private int m_x;
	private int m_y;
	private int m_angle;
	
	final public static int LENGTH = (Byte.SIZE +
									  Integer.SIZE +
									  Integer.SIZE +
									  Integer.SIZE +
									  Long.SIZE) / 8;
	
	private UpdateActualRobotPositionSignal() {
		super(SignalType.UpdateActualRobotPosition);
	}
	
	public UpdateActualRobotPositionSignal(byte robotID, RobotPosition robotPosition) {
		this(robotID, (robotPosition == null ? -1 : robotPosition.getX()), (robotPosition == null ? -1 : robotPosition.getY()), (robotPosition == null ? -1 : robotPosition.getAngleDegrees()));
	}
	
	public UpdateActualRobotPositionSignal(byte robotID, int x, int y, int angle) {
		super(SignalType.UpdateActualRobotPosition);
		m_robotID = robotID;
		m_x = x;
		m_y = y;
		m_angle = angle;
	}

	public byte getRobotID() {
		return m_robotID;
	}
	
	public int getX() {
		return m_x;
	}
	
	public int getY() {
		return m_y;
	}
	
	public int getAngle() {
		return m_angle;
	}
	
	public Position getPosition() {
		return new Position(m_x, m_y);
	}
	
	public RobotPosition getRobotPosition() {
		return new RobotPosition(m_x, m_y, m_angle);
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_robotID);
		checksum += ByteStream.getChecksum(m_x);
		checksum += ByteStream.getChecksum(m_y);
		checksum += ByteStream.getChecksum(m_angle);
		return checksum;
	}
	
	public static UpdateActualRobotPositionSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		UpdateActualRobotPositionSignal s2 = new UpdateActualRobotPositionSignal();
		
		s2.m_robotID = byteStream.nextByte();
		s2.m_x = byteStream.nextInteger();
		s2.m_y = byteStream.nextInteger();
		s2.m_angle = byteStream.nextInteger();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_robotID);
		byteStream.addInteger(m_x);
		byteStream.addInteger(m_y);
		byteStream.addInteger(m_angle);
		byteStream.addLong(checksum());
	}
	
	public String toString() {
		return super.toString() + " Robot ID: " + m_robotID + " Position: (" + m_x + ", " + m_y + ") Angle: " + m_angle;
	}
	
}
