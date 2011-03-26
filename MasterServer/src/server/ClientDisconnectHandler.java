package server;

import java.util.Vector;

public class ClientDisconnectHandler extends Thread {
	
	private Vector<Client> m_clients;
	
	public ClientDisconnectHandler() { }
	
	public void initialize(Vector<Client> clients) {
		m_clients = clients;
		if(m_clients == null) { return; }
		if(getState() == Thread.State.NEW) { start(); }
	}

	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void run() {
		Client c;
		while(true) {
			for(int i=0;i<m_clients.size();i++) {
				c = m_clients.elementAt(i);
				
				c.addTime(Server.TIMEOUT_INTERVAL);
				
				c.ping();
				
				if(!c.isConnected()) {
					c.disconnect();
					
					m_clients.remove(i);
					i--;
					
					if(c.timeout()) {
						SystemManager.console.writeLine((c.isIdentified() ? "Tracker #" + c.getTrackerNumber() : "Client #" + c.getClientNumber()) + " timed out");
					}
				}
			}
			
			try { sleep(Server.TIMEOUT_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
	
}
