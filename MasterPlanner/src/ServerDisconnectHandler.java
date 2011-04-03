public class ServerDisconnectHandler extends Thread {
	
	private Client m_client;
	
	public ServerDisconnectHandler() {
		
	}
	
	public void initialize(Client client) {
		m_client = client;
		if(m_client == null) { return; }
		if(getState() == Thread.State.NEW) { start(); }
	}

	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void run() {
		while(m_client.isConnected()) {
			m_client.addTime(Client.TIMEOUT_INTERVAL);
			
			m_client.ping();
			
			if(!m_client.isConnected()) {
				m_client.disconnect();
				
				if(m_client.timeout()) {
					SystemManager.console.writeLine("Connection to server timed out.");
				}
			}
			
			try { sleep(Client.TIMEOUT_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
