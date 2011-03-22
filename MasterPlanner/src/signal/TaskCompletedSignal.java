package signal;

import shared.*;

public class TaskCompletedSignal extends Signal {
	
	private byte m_robotID;
	private byte m_taskID;
	
	final public static int LENGTH = (Byte.SIZE +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private TaskCompletedSignal() {
		super(SignalType.BlockStateChange);
	}
	
	public TaskCompletedSignal(byte robotID, byte taskID) {
		super(SignalType.BlockStateChange);
		m_robotID = robotID;
		m_taskID = taskID;
	}

	public byte getRobotID() {
		return m_robotID;
	}
	
	public byte getTaskID() {
		return m_taskID;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_robotID);
		checksum += ByteStream.getChecksum(m_taskID);
		return checksum;
	}
	
	public static TaskCompletedSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		TaskCompletedSignal s2 = new TaskCompletedSignal();
		
		s2.m_robotID = byteStream.nextByte();
		s2.m_taskID = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_robotID);
		byteStream.addByte(m_taskID);
		byteStream.addLong(checksum());
	}
	
}
