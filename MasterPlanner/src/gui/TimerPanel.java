package gui;

import javax.swing.*;
import planner.*;
import shared.*;

public class TimerPanel extends JPanel implements Updatable {
	
	private AutomaticUpdater m_timerUpdater;
	
	private JLabel m_timerLabel;
	
	public static long DEFAULT_UPDATE_RATE = 1000;
	
	private static final long serialVersionUID = 1L;
	
	public TimerPanel() {
		this(DEFAULT_UPDATE_RATE);
	}
	
	public TimerPanel(long updateRate) {
		m_timerUpdater = new AutomaticUpdater(updateRate < 50 ? DEFAULT_UPDATE_RATE : updateRate);
		m_timerUpdater.setTarget(this);
		
		initComponents();
	}
	
	private void initComponents() {
		m_timerLabel = new JLabel(SystemManager.timer.toString());
	}
	
	public void update() {
		m_timerLabel.setText(SystemManager.timer.toString());
	}
	
}
