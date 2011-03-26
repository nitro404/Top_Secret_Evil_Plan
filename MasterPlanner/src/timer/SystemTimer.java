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
	
	public long getTimeElapsed() {
		if(m_startTime < 0) { return 0; }
		return System.currentTimeMillis() - m_startTime;
	}
	
	public long getTimeRemaining() {
		if(m_startTime < 0) { return 0; }
		return m_endTime - System.currentTimeMillis();
	}
	
	public boolean isStarted() {
		return m_startTime >= 0;
	}
	
	public boolean isFinished() {
		return System.currentTimeMillis() >= m_endTime;
	}
	
	public String toString() {
		long timeElapsed = 0;
		long elapsedMinutes = 0, elapsedSeconds = 0;
		long maxMinutes = 0, maxSeconds = 0;
		if(isStarted()) {
			timeElapsed = getTimeElapsed() / 1000L;
			elapsedMinutes = (timeElapsed / 60);
			elapsedSeconds = (timeElapsed % 60);
		}
		maxMinutes = (m_maxTime / 60);
		maxSeconds = (m_maxTime % 60);
		
		String elapsed = "Time Elapsed: " + ((elapsedMinutes < 10) ? "0" : "") + elapsedMinutes + ":" + ((elapsedSeconds < 10) ? "0" : "") + elapsedSeconds;
		String limit = "Time Limit: " + ((maxMinutes < 10) ? "0" : "") + maxMinutes + ":" + ((maxSeconds < 10) ? "0" : "") + maxSeconds;
		return elapsed + " " + limit;
	}
	
}
