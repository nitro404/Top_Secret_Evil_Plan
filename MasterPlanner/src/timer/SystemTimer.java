package timer;

public class SystemTimer {
	
	private long m_startTime;
	private long m_endTime;
	private int m_maxTime;

	public SystemTimer(int maxSeconds) {
		m_maxTime = (maxSeconds < 0) ? 0 : maxSeconds;
		m_startTime = -1;
		m_endTime = -1;
	}
	
	public boolean start() {
		if(m_maxTime <= 0) { return false; }
		m_startTime = System.currentTimeMillis();
		m_endTime = m_startTime + (m_maxTime * 1000L);
		return true;
	}
	
	public String getTimeLimitString() {
		long maxMinutes = 0, maxSeconds = 0;
		maxMinutes = (m_maxTime / 60);
		maxSeconds = (m_maxTime % 60);
		
		return ((maxMinutes < 10) ? "0" : "") + maxMinutes + ":" + ((maxSeconds < 10) ? "0" : "") + maxSeconds;
	}
	
	public long getTimeElapsed() {
		if(m_startTime < 0) { return 0; }
		return System.currentTimeMillis() - m_startTime;
	}
	
	public String getTimeElapsedString() {
		long timeElapsed = 0;
		long elapsedMinutes = 0, elapsedSeconds = 0;
		if(isStarted()) {
			timeElapsed = getTimeElapsed() / 1000L;
			elapsedMinutes = (timeElapsed / 60);
			elapsedSeconds = (timeElapsed % 60);
		}
		
		return ((elapsedMinutes < 10) ? "0" : "") + elapsedMinutes + ":" + ((elapsedSeconds < 10) ? "0" : "") + elapsedSeconds;
	}
	
	public long getTimeRemaining() {
		if(m_startTime < 0) { return 0; }
		return m_endTime - System.currentTimeMillis();
	}
	
	public String getTimeRemainingString() {
		long timeRemaining = 0;
		long remainingMinutes = 0, remainingSeconds = 0;
		if(isStarted()) {
			timeRemaining = getTimeRemaining() / 1000L;
			remainingMinutes = (timeRemaining / 60);
			remainingSeconds = (timeRemaining % 60);
		}
		
		return ((remainingMinutes < 10) ? "0" : "") + remainingMinutes + ":" + ((remainingSeconds < 10) ? "0" : "") + remainingSeconds;
	}
	
	public boolean isStarted() {
		return m_startTime >= 0;
	}
	
	public boolean isFinished() {
		return System.currentTimeMillis() >= m_endTime;
	}
	
	public String toString() {
		return "Time Elapsed: " + getTimeElapsedString() + " Time Limit: " + getTimeLimitString();
	}
	
}
