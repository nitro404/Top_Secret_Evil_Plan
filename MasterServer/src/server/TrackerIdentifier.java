package server;

import java.net.InetAddress;
import java.util.Vector;
import javax.swing.JOptionPane;
import settings.*;
import shared.*;

public class TrackerIdentifier extends Thread {
	
	private Vector<Client> m_trackers;
	
	private Server m_server;
	private SettingsManager m_settings;
	private SystemConsole m_console;
	
	public TrackerIdentifier() {
		m_trackers = new Vector<Client>();
	}
	
	public void initialize(Server server, SettingsManager settings, SystemConsole console) {
		m_server = server;
		m_settings = settings;
		m_console = console;
		if(m_server == null || m_trackers == null || m_settings == null) { return; }
		if(getState() == Thread.State.NEW) { start(); }
	}

	public boolean isTerminated() {
		return getState() == Thread.State.TERMINATED; 
	}
	
	public void add(Client c) {
		if(c == null) { return; }
		m_trackers.add(c);
	}
	
	public void run() {
		while(true) {
			
			if(m_trackers.size() == 0) {
				try { sleep(Server.QUEUE_INTERVAL); }
				catch (InterruptedException e) { }
				
				continue;
			}
			
			int numberOfIdentifiedTrackers = 0;
			int numberOfUnidentifiedTrackers = 0;
			boolean identifiedTrackers[] = new boolean[SettingsManager.defaultTrackerIPAddress.length];
			
			for(int i=0;i<m_server.numberOfClients();i++) {
				Client c = m_server.getClient(i);
				if(c.getTrackerNumber() >= 1 && c.getTrackerNumber() <= identifiedTrackers.length && !identifiedTrackers[c.getTrackerNumber() - 1]) {
					identifiedTrackers[c.getTrackerNumber() - 1] = true;
				}
			}
			
			boolean allTrackersIdentified = true;
			for(int i=0;i<identifiedTrackers.length;i++) {
				if(!identifiedTrackers[i]) {
					allTrackersIdentified = false;
				}
				else {
					numberOfIdentifiedTrackers++;
				}
			}
			numberOfUnidentifiedTrackers = SettingsManager.defaultTrackerIPAddress.length - numberOfIdentifiedTrackers;
			
			if(allTrackersIdentified) {
				JOptionPane.showMessageDialog(null, "Maximum number of trackers already connected!", "Too Many Trackers", JOptionPane.ERROR_MESSAGE);
				for(int i=0;i<m_trackers.size();i++) {
					m_trackers.elementAt(i).disconnect();
					m_trackers.remove(i);
					i--;
				}
				continue;
			}
			
			for(int i=0;i<m_trackers.size();i++) {
				Client c = m_trackers.elementAt(i);
				
				boolean trackerIdentified = false;
				String[] trackerChoices = new String[numberOfUnidentifiedTrackers];
				int k = 0;
				for(int j=0;j<identifiedTrackers.length;j++) {
					if(!identifiedTrackers[j]) {
						trackerChoices[k++] = Integer.toString(j + 1);
					}
				}
				
				if(!c.isConnected() || c.isIdentified()) {
					m_trackers.remove(i);
					i--;
					continue;
				}
				
				int trackerNumber = -1;
				for(int j=0;j<SettingsManager.defaultTrackerIPAddress.length;j++) {
					InetAddress trackerIP = m_settings.getTrackerIPAddress(j + 1);
					if(trackerIP.equals(c.getIPAddress())) {
						trackerNumber = j + 1;
						break;
					}
				}
				
				if(trackerNumber != -1) {
					 trackerIdentified = JOptionPane.showConfirmDialog(null, "Tracker \"" + c.getIPAddressString() + "\" identified as Tracker #" + trackerNumber + ".\nIs this correct?", "Tracker Identified", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
				}
				
				if(!c.isConnected()) {
					m_trackers.remove(i);
					i--;
					continue;
				}
				
				if(!trackerIdentified) {
					Object input;
					boolean validInput;
					do {
						input = JOptionPane.showInputDialog(null, "Please choose which tracker this is.", "Identify Tracker", JOptionPane.QUESTION_MESSAGE, null, trackerChoices, trackerChoices[0]);
						validInput = input != null;
					} while(!validInput);
					
					try {
						trackerNumber = Integer.parseInt(input.toString());
						trackerIdentified = true;
					}
					catch(NumberFormatException e) { }
				}
				
				if(!c.isConnected()) {
					m_trackers.remove(i);
					i--;
					continue;
				}
				
				boolean trackerAlreadyIdentified = false;
				if(trackerIdentified) {
					for(int j=0;j<m_server.numberOfClients();j++) {
						if(m_server.getClient(j).getTrackerNumber() == trackerNumber) {
							JOptionPane.showMessageDialog(null, "This tracker number has already been taken, please choose another!", "Tracker Number Taken", JOptionPane.WARNING_MESSAGE);
							trackerAlreadyIdentified = true;
						}
					}
					if(!trackerAlreadyIdentified) {
						c.setTrackerNumber(trackerNumber);
						m_console.writeLine("Client #" + c.getClientNumber() + " identified as Tracker #" + c.getTrackerNumber() + ".");
					}
				}
				else {
					JOptionPane.showMessageDialog(null, "Unable to identify tracker.", "Tracker Unidentified", JOptionPane.ERROR_MESSAGE);
					c.disconnect();
				}
				
				if(trackerAlreadyIdentified) {
					break;
				}
				
				m_trackers.remove(i);
				i--;
			}
			
			try { sleep(Server.QUEUE_INTERVAL); }
			catch (InterruptedException e) { }
		}
	}
}
