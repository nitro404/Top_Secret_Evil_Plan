package server;

public class ClientThread extends Thread {
	
	private Client m_client;
	
	public ClientThread() {
		
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
			m_client.readSignal();
			
			try { sleep(Server.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
