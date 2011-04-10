import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class DisplayPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener, Scrollable {
	
	private byte m_editMode;
	
	private BufferedImage[] m_trackerImage;
	
	private FlowLayout m_layout;
	
	private JPopupMenu m_popupMenu;
	private JMenu m_editModeMenu;
	private JRadioButtonMenuItem[] m_editModeMenuItem;
	private ButtonGroup m_editModeButtonGroup;
	private JCheckBoxMenuItem m_autoConnectVerticesMenuItem;
	private JMenuItem m_createVertexMenuItem;
	private JMenuItem m_removeVertexMenuItem;
	private JMenuItem m_choosePathMenuItem;
	private JMenuItem m_createPathMenuItem;
	private JMenuItem m_renamePathMenuItem;
	private JMenuItem m_removePathMenuItem;
	
	private boolean m_updating;
	
	private static final long serialVersionUID = 1L;
	
	public DisplayPanel() {
		setPreferredSize(new Dimension(Position.getMaxX(), Position.getMaxY()));
		m_layout = new FlowLayout();
		m_layout.setHgap(0);
		m_layout.setVgap(0);
		setLayout(m_layout);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		m_updating = false;
		
		m_editMode = EditMode.ViewOnly;
		
		m_trackerImage = new BufferedImage[SystemManager.settings.getNumberOfTrackers()];
		
		initPopupMenu();
	}
	
	private void initPopupMenu() {
		m_popupMenu = new JPopupMenu();
		
    	m_editModeMenu = new JMenu("Editing Mode");
    	m_editModeMenuItem = new JRadioButtonMenuItem[EditMode.displayEditModes.length];
    	for(int i=0;i<EditMode.displayEditModes.length;i++) {
    		m_editModeMenuItem[i] = new JRadioButtonMenuItem(EditMode.displayEditModes[i]);
    	}
    	m_autoConnectVerticesMenuItem = new JCheckBoxMenuItem("Auto-connect Vertices");
    	m_createVertexMenuItem = new JMenuItem("Create Vertex");
    	m_removeVertexMenuItem = new JMenuItem("Remove Vertex");
    	m_choosePathMenuItem = new JMenuItem("Choose Path");
    	m_createPathMenuItem = new JMenuItem("Create Path");
    	m_renamePathMenuItem = new JMenuItem("Rename Path");
    	m_removePathMenuItem = new JMenuItem("Remove Path");
    	
    	m_editModeMenuItem[0].setSelected(true);
    	m_autoConnectVerticesMenuItem.setSelected(SystemManager.pathSystem.getAutoConnectVertices());
    	
    	m_editModeButtonGroup = new ButtonGroup();
    	
    	for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeMenuItem[i].addActionListener(this);
        }
    	m_autoConnectVerticesMenuItem.addActionListener(this);
    	m_createVertexMenuItem.addActionListener(this);
    	m_removeVertexMenuItem.addActionListener(this);
    	m_choosePathMenuItem.addActionListener(this);
    	m_createPathMenuItem.addActionListener(this);
    	m_renamePathMenuItem.addActionListener(this);
    	m_removePathMenuItem.addActionListener(this);
    	
    	for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeButtonGroup.add(m_editModeMenuItem[i]);
        }
    	
        for(byte i=0;i<EditMode.displayEditModes.length;i++) {
        	m_editModeMenu.add(m_editModeMenuItem[i]);
        }
        
        m_popupMenu.add(m_editModeMenu);
        m_popupMenu.add(m_autoConnectVerticesMenuItem);
        m_popupMenu.add(m_createVertexMenuItem);
        m_popupMenu.add(m_removeVertexMenuItem);
        m_popupMenu.add(m_choosePathMenuItem);
        m_popupMenu.add(m_createPathMenuItem);
        m_popupMenu.add(m_renamePathMenuItem);
        m_popupMenu.add(m_removePathMenuItem);
	}
	
	public byte getEditMode() {
		return m_editMode;
	}
	
	public boolean setEditMode(byte editMode) {
		if(!EditMode.isValid(editMode)) { return false; }
		m_editMode = editMode;
		SystemManager.pathSystem.clearSelection();
		SystemManager.robotSystem.clearSelection();
		SystemManager.blockSystem.clearSelection();
		SystemManager.potSystem.clearSelection();
		return true;
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

	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void mousePressed(MouseEvent e) {
		if(m_editMode == EditMode.ViewOnly || SystemManager.client.isConnected()) { return; }
		else if(m_editMode == EditMode.Path) {
			SystemManager.pathSystem.mousePressed(e);
		}
		else if(m_editMode == EditMode.Robot) {
			SystemManager.robotSystem.mousePressed(e);
		}
		else if(m_editMode == EditMode.Block) {
			SystemManager.blockSystem.mousePressed(e);
		}
		else if(m_editMode == EditMode.Pot) {
			SystemManager.potSystem.mousePressed(e);
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(SystemManager.client.isConnected()) { return; }
		
		if(m_editMode == EditMode.ViewOnly) { }
		else if(m_editMode == EditMode.Path) {
			SystemManager.pathSystem.mouseReleased(e);
		}
		else if(m_editMode == EditMode.Robot) {
			SystemManager.robotSystem.mouseReleased(e);
		}
		else if(m_editMode == EditMode.Block) {
			SystemManager.blockSystem.mouseReleased(e);
		}
		else if(m_editMode == EditMode.Pot) {
			SystemManager.potSystem.mouseReleased(e);
		}
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			showPopupMenu(e.getX(), e.getY());
		}
		else if(e.getButton() == MouseEvent.BUTTON1) {
			if(SystemManager.taskEditorWindow == null || !SystemManager.taskEditorWindow.isVisible()) { return; }
			
			if(SystemManager.taskEditorWindow.lookingForVertex()) {
				Path selectedPath = SystemManager.pathSystem.getSelectedPath(e.getPoint());
				int vertexIndex = -1;
				if(selectedPath != null) {
					vertexIndex = selectedPath.getSelectedVertexIndex(e.getPoint());
				}
				
				SystemManager.taskEditorWindow.setSelectedVertex(selectedPath, vertexIndex);
			}
			else if(SystemManager.taskEditorWindow.lookingForBlock()) {
				Block selectedBlock = SystemManager.blockSystem.getSelectedBlock(e.getPoint());
				
				SystemManager.taskEditorWindow.setSelectedBlock(selectedBlock);
			}
			else if(SystemManager.taskEditorWindow.lookingForDropOffLocation()) {
				DropOffLocation selectedDropOffLocation = SystemManager.blockSystem.getSelectedDropOffLocation(e.getPoint());
				
				SystemManager.taskEditorWindow.setSelectedDropOffLocation(selectedDropOffLocation);
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(m_editMode == EditMode.ViewOnly || SystemManager.client.isConnected()) { return; }
		else if(m_editMode == EditMode.Path) {
			SystemManager.pathSystem.mouseDragged(e);
		}
		else if(m_editMode == EditMode.Robot) {
			SystemManager.robotSystem.mouseDragged(e);
		}
		else if(m_editMode == EditMode.Block) {
			SystemManager.blockSystem.mouseDragged(e);
		}
		else if(m_editMode == EditMode.Pot) {
			SystemManager.potSystem.mouseDragged(e);
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(m_editMode == EditMode.ViewOnly || SystemManager.client.isConnected()) { return; }
		else if(m_editMode == EditMode.Path) {
			SystemManager.pathSystem.mouseMoved(e);
		}
		else if(m_editMode == EditMode.Robot) {
			SystemManager.robotSystem.mouseMoved(e);
		}
		else if(m_editMode == EditMode.Block) {
			SystemManager.blockSystem.mouseMoved(e);
		}
		else if(m_editMode == EditMode.Pot) {
			SystemManager.potSystem.mouseMoved(e);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if(m_updating) { return; }
		
		if(e.getSource() == m_autoConnectVerticesMenuItem) {
			SystemManager.pathSystem.setAutoConnectVertices(m_autoConnectVerticesMenuItem.isSelected());
		}
		if(e.getSource() == m_createVertexMenuItem) {
			if(m_editMode == EditMode.Path) {
				SystemManager.pathSystem.createVertex();
			}
		}
		else if(e.getSource() == m_removeVertexMenuItem) {
			SystemManager.pathSystem.removeSelectedVertex();
		}
		else if(e.getSource() == m_choosePathMenuItem) {
			SystemManager.pathSystem.choosePath();
		}
		else if(e.getSource() == m_createPathMenuItem) {
			SystemManager.pathSystem.createPath();
		}
		else if(e.getSource() == m_renamePathMenuItem) {
			SystemManager.pathSystem.renamePath();
		}
		else if(e.getSource() == m_removePathMenuItem) {
			SystemManager.pathSystem.removePath();
		}
		
		for(byte i=0;i<EditMode.displayEditModes.length;i++) {
			if(e.getSource() == m_editModeMenuItem[i]) {
				setEditMode(i);
				SystemManager.plannerWindow.update();
				return;
			}
		}
	}
	
	private void showPopupMenu(int x, int y) {
		if(SystemManager.client.isConnected()) { return; }
		
		m_editModeMenuItem[m_editMode].setSelected(true);
		m_autoConnectVerticesMenuItem.setSelected(SystemManager.pathSystem.getAutoConnectVertices());
		m_autoConnectVerticesMenuItem.setEnabled(m_editMode == EditMode.Path);
		m_createVertexMenuItem.setEnabled(m_editMode == EditMode.Path && SystemManager.pathSystem.hasActivePath());
		m_removeVertexMenuItem.setEnabled(m_editMode == EditMode.Path && SystemManager.pathSystem.getSelectedVertex() != null);
		m_choosePathMenuItem.setEnabled(m_editMode == EditMode.Path && SystemManager.pathSystem.numberOfPaths() > 0);
		m_createPathMenuItem.setEnabled(m_editMode == EditMode.Path);
		m_renamePathMenuItem.setEnabled(m_editMode == EditMode.Path && SystemManager.pathSystem.numberOfPaths() > 0);
		m_removePathMenuItem.setEnabled(m_editMode == EditMode.Path && SystemManager.pathSystem.numberOfPaths() > 0);
		
		m_popupMenu.show(this, x, y);
	}
	
	public void paintComponent(Graphics g) {
		if(g == null) { return; }
		
		try {
			super.paintComponent(g);
			
			for(int i=0;i<m_trackerImage.length;i++) {
				if(m_trackerImage[i] != null) {
					g.drawImage(m_trackerImage[i], 0, i * 480, null);
				}
			}
			
			if(SystemManager.potSystem != null) { SystemManager.potSystem.draw(g); }
			if(SystemManager.blockSystem != null) { SystemManager.blockSystem.draw(g); }
			if(SystemManager.robotSystem != null) { SystemManager.robotSystem.draw(g); }
			if(SystemManager.pathSystem != null) {
				if(m_editMode == EditMode.Path) {
					SystemManager.pathSystem.draw(g);
				}
				else {
					if(SystemManager.settings.getDrawPathType() == DrawPathType.AllPaths) {
						SystemManager.pathSystem.drawAll(g);
					}
					else if(SystemManager.settings.getDrawPathType() == DrawPathType.ActivePathOnly) {
						SystemManager.pathSystem.draw(g);
					}
				}
				
			}
			if(SystemManager.taskManager != null) { SystemManager.taskManager.draw(g); }
		}
		catch(Exception e) { }
	}
	
}
