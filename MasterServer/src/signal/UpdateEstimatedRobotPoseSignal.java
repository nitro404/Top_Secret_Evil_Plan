package signal;

import shared.*;

public class UpdateEstimatedRobotPoseSignal extends Signal {
	
	private byte m_robotID;
	private int m_x;
	private int m_y;
	private int m_angle;
	
	final public static int LENGTH = (Byte.SIZE +
									  Integer.SIZE +
									  Integer.SIZE +
									  Integer.SIZE +
									  Long.SIZE) / 8;
	
	private UpdateEstimatedRobotPoseSignal() {
		super(SignalType.UpdateEstimatedRobotPose);
	}
	
	public UpdateEstimatedRobotPoseSignal(byte robotID, RobotPose robotPose) {
		this(robotID, (robotPose == null ? -1 : robotPose.getX()), (robotPose == null ? -1 : robotPose.getY()), (robotPose == null ? -1 : robotPose.getAngleDegrees()));
	}
	
	public UpdateEstimatedRobotPoseSignal(byte robotID, int x, int y, int angle) {
		super(SignalType.UpdateEstimatedRobotPose);
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
	
	public RobotPose getPose() {
		return new RobotPose(m_x, m_y, m_angle);
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_robotID);
		checksum += ByteStream.getChecksum(m_x);
		checksum += ByteStream.getChecksum(m_y);
		checksum += ByteStream.getChecksum(m_angle);
		return checksum;
	}
	
	public static UpdateEstimatedRobotPoseSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		UpdateEstimatedRobotPoseSignal s2 = new UpdateEstimatedRobotPoseSignal();
		
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
