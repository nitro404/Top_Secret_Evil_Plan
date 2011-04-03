public class TaskCompletedSignal extends Signal {
	
	private byte m_taskID;
	private byte m_robotID;
	
	final public static int LENGTH = (Byte.SIZE +
									  Byte.SIZE +
									  Long.SIZE) / 8;
	
	private TaskCompletedSignal() {
		super(SignalType.TaskCompleted);
	}
	
	public TaskCompletedSignal(byte taskID, byte robotID) {
		super(SignalType.TaskCompleted);
		m_taskID = taskID;
		m_robotID = robotID;
	}

	public byte getTaskID() {
		return m_taskID;
	}
	
	public byte getRobotID() {
		return m_robotID;
	}
	
	public long checksum() {
		long checksum = 0;
		checksum += ByteStream.getChecksum(m_taskID);
		checksum += ByteStream.getChecksum(m_robotID);
		return checksum;
	}
	
	public static TaskCompletedSignal readFrom(ByteStream byteStream) {
		if(byteStream == null) { return null; }
		
		TaskCompletedSignal s2 = new TaskCompletedSignal();
		
		s2.m_taskID = byteStream.nextByte();
		s2.m_robotID = byteStream.nextByte();
		long checksum = byteStream.nextLong();
		if(checksum != s2.checksum()) { return null; }
		
		return s2;
	}

	public void writeTo(ByteStream byteStream) {
		if(byteStream == null) { return; }
		
		super.writeTo(byteStream);
		byteStream.addByte(m_taskID);
		byteStream.addByte(m_robotID);
		byteStream.addLong(checksum());
	}
	
	public String toString() {
		return super.toString() + " Task ID: " + m_taskID + " Robot ID: " + m_robotID;
	}
	
}
