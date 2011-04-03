public class AutomaticUpdater extends Thread {
	
	private long m_interval;
	private Updatable m_updatable;
	
	public AutomaticUpdater(long interval) {
		m_interval = (interval < 5L) ? 50L : interval;
	}
	
	public void initialize() {
		if(getState() == Thread.State.NEW) { start(); }
	}

	public void setTarget(Updatable updatable) {
		m_updatable = updatable;
	}

	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void run() {
		while(true) {
			if(m_updatable != null) { m_updatable.update(); }
			
			try { sleep(m_interval); }
			catch (InterruptedException e) { }
		}
	}
	
}
