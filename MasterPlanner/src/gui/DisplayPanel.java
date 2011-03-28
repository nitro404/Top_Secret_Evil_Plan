package gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import imaging.*;
import planner.*;

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
	private JMenuItem m_removePathMenuItem;
	
	private boolean m_updating;
	
	private static final long serialVersionUID = 1L;
	
	public DisplayPanel() {
		setPreferredSize(new Dimension(Webcam.DEFAULT_WIDTH, Webcam.DEFAULT_HEIGHT * SystemManager.MAX_NUMBER_OF_TRACKERS));
		m_layout = new FlowLayout();
		m_layout.setHgap(0);
		m_layout.setVgap(0);
		setLayout(m_layout);
		addMouseListener(this);
		addMouseMotionListener(this);
		
		m_updating = false;
		
		m_editMode = EditMode.ViewOnly;
		
		m_trackerImage = new BufferedImage[SystemManager.MAX_NUMBER_OF_TRACKERS];
		
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
        m_popupMenu.add(m_removePathMenuItem);
	}
	
	public byte getEditMode() {
		return m_editMode;
	}
	
	public boolean setEditMode(byte editMode) {
		if(!EditMode.isValid(editMode)) { return false; }
		m_editMode = editMode;
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
		if(m_editMode == EditMode.ViewOnly) { return; }
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
		
		/*
		if(e.getButton() == MouseEvent.BUTTON2) {
			if(mode == MODE_TILING) {
				editorWindow.activeSprite = null;
				vertexToMove = null;
				
				selectSprite(e.getPoint());
				spriteToMove = selectedSprite;
			}
		}
		*/
	}
	
	public void mouseReleased(MouseEvent e) {
		if(m_editMode == EditMode.ViewOnly) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				showPopupMenu(e.getX(), e.getY());
			}
		}
		else if(m_editMode == EditMode.Path) {
			SystemManager.pathSystem.mouseReleased(e);
			if(e.getButton() == MouseEvent.BUTTON3) {
				showPopupMenu(e.getX(), e.getY());
			}
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
		
		/*
		if(e.getButton() == MouseEvent.BUTTON3) {
			if(mode == MODE_TILING) {
				selectedPoint = e.getPoint();
				selectSprite(e.getPoint());
				tilingPopupMenuBringSpriteToFront.setEnabled(selectedSprite != null);
				tilingPopupMenuSendSpriteToBack.setEnabled(selectedSprite != null);
				tilingPopupMenuDeleteSprite.setEnabled(selectedSprite != null);
				tilingPopupMenu.show(this, e.getX(), e.getY());
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON2) {
			spriteToMove = null;
		}
		else if(e.getButton() == MouseEvent.BUTTON1) {
			else if(mode == MODE_TILING) {
				if(selectedGridBlock != null && editorWindow.activeSprite != null) {
					Vertex v = new Vertex((editorWindow.activeSprite.isTiled()) ? selectedGridBlock.x : (int) (e.getX() - (editorWindow.activeSprite.getWidth() / 2.0f)),
							 			  (editorWindow.activeSprite.isTiled()) ? selectedGridBlock.y : (int) (e.getY() - (editorWindow.activeSprite.getHeight() / 2.0f)));
					Entity newEntity = new Entity(v, editorWindow.activeSprite);
					newEntity.spriteSheetIndex = editorWindow.spriteSheets.getSpriteSheetIndex(editorWindow.activeSprite.getParentName());
					if(newEntity.isTiled()) {
						level.addTile(newEntity);
					}
					else {
						level.addEntity(newEntity);
					}
				}
			}
		}
		*/
	}
	
	public void mouseDragged(MouseEvent e) {
		if(m_editMode == EditMode.ViewOnly) { return; }
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
		
		/*
		mousePosition = e.getPoint();
		if(mode == MODE_TILING) {
			getSelectedGridBlock(e.getPoint());
			if(spriteToMove != null) {
				spriteToMove.location.x = spriteToMove.isTiled() ? selectedGridBlock.x : (int) (e.getX() - (spriteToMove.getWidth() / 2.0f));
				spriteToMove.location.y = spriteToMove.isTiled() ? selectedGridBlock.y : (int) (e.getY() - (spriteToMove.getHeight() / 2.0f));
			}
		}
		*/
	}
	
	public void mouseMoved(MouseEvent e) {
		if(m_editMode == EditMode.ViewOnly) { return; }
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
		/*
		mousePosition = e.getPoint();
		if(mode == MODE_TILING) {
			getSelectedGridBlock(e.getPoint());
		}
		*/
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
		else if(e.getSource() == m_removePathMenuItem) {
			SystemManager.pathSystem.removePath();
		}
		
		for(byte i=0;i<EditMode.displayEditModes.length;i++) {
			if(e.getSource() == m_editModeMenuItem[i]) {
				setEditMode(i);
				SystemManager.debugWindow.update();
				return;
			}
		}
	}
	
	private void showPopupMenu(int x, int y) {
		m_editModeMenuItem[m_editMode].setSelected(true);
		m_autoConnectVerticesMenuItem.setSelected(SystemManager.pathSystem.getAutoConnectVertices());
		m_autoConnectVerticesMenuItem.setEnabled(m_editMode == EditMode.Path);
		m_createVertexMenuItem.setEnabled(m_editMode == EditMode.Path && SystemManager.pathSystem.hasActivePath());
		m_removeVertexMenuItem.setEnabled(m_editMode == EditMode.Path && SystemManager.pathSystem.getSelectedVertex() != null);
		m_choosePathMenuItem.setEnabled(m_editMode == EditMode.Path && SystemManager.pathSystem.numberOfPaths() > 0);
		m_createPathMenuItem.setEnabled(m_editMode == EditMode.Path);
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
			
			Graphics2D g2 = (Graphics2D) g;
			if(SystemManager.potSystem != null) { SystemManager.potSystem.draw(g2); }
			if(SystemManager.blockSystem != null) { SystemManager.blockSystem.draw(g2); }
			if(SystemManager.robotSystem != null) { SystemManager.robotSystem.draw(g2); }
			if(SystemManager.pathSystem != null) {
				if(m_editMode == EditMode.ViewOnly) {
					SystemManager.pathSystem.drawAll(g2);
				}
				else {
					SystemManager.pathSystem.draw(g);
				}
			}
		}
		catch(Exception e) { }
	}
	
}
