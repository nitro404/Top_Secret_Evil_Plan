package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

import planner.SystemManager;
import shared.*;

public class DisplayPanel extends JPanel implements Scrollable {
	
	private BufferedImage[] m_trackerImage;
	
	private FlowLayout m_layout;
	
	private static final long serialVersionUID = 1L;
	
	public DisplayPanel() {
		setPreferredSize(new Dimension(Position.MAX_X, Position.MAX_Y));
		m_layout = new FlowLayout();
		m_layout.setHgap(0);
		m_layout.setVgap(0);
		setLayout(m_layout);
		
		m_trackerImage = new BufferedImage[3];
	}
	
	public void setTrackerImage(byte trackerNumber, BufferedImage trackerImage) {
		if(trackerImage == null || trackerNumber <= 0 || trackerNumber > m_trackerImage.length) { return; }
		m_trackerImage[trackerNumber - 1] = trackerImage;
	}
	
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}
	
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		int currentPosition = 0;
		if(orientation == SwingConstants.HORIZONTAL) {
			currentPosition = visibleRect.x;
		}
		else {
			currentPosition = visibleRect.y;
		}
        
		int maxUnitIncrement = 16;
		if(direction < 0) {
			int newPosition = currentPosition -
							  (currentPosition / maxUnitIncrement)
                              * maxUnitIncrement;
            return (newPosition == 0) ? maxUnitIncrement : newPosition;
        }
		else {
            return ((currentPosition / maxUnitIncrement) + 1)
                   * maxUnitIncrement
                   - currentPosition;
        }
	}
	
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		if(orientation == SwingConstants.HORIZONTAL) {
			return visibleRect.width;
		}
		else {
			return visibleRect.height;
		}
	}
	
	public boolean getScrollableTracksViewportHeight() { return false; }
	public boolean getScrollableTracksViewportWidth() { return false; }
	
	public void paintComponent(Graphics g) {
		if(g == null) { return; }
		
		try {
			super.paintComponent(g);
			
			for(int i=0;i<m_trackerImage.length;i++) {
				if(m_trackerImage[i] != null) {
					g.drawImage(m_trackerImage[i], 0, i * 480, null);
				}
			}
			
			Graphics2D g2 = (Graphics2D) g;
			if(SystemManager.potSystem != null) { SystemManager.potSystem.draw(g2); }
			if(SystemManager.blockSystem != null) { SystemManager.blockSystem.draw(g2); }
			if(SystemManager.robotSystem != null) { SystemManager.robotSystem.draw(g2); }
		}
		catch(Exception e) { }
	}
	
}
