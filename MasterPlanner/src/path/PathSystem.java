package path;

import java.util.Vector;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PathSystem implements MouseListener, MouseMotionListener {
	
	private Vector<Path> m_paths;
	private int m_activePath;
	
	private Point m_selectedPoint;
	private Vertex m_selectedVertex;
	private Vertex m_lastSelectedVertex;
	private Vertex m_vertexToMove;
	private Point m_mousePosition;
	private boolean m_autoConnectVertices;
	final private static long m_doubleClickSpeed = 200;
	private long m_lastMouseDown = 0;
	
	final private static int DEFAULT_SELECTION_RADIUS = 6; 
	
	public PathSystem() {
		m_paths = new Vector<Path>();
		
		m_activePath = -1;
		m_selectedPoint = null;
		m_selectedVertex = null;
		m_lastSelectedVertex = null;
		m_vertexToMove = null;
		m_mousePosition = null;
		m_autoConnectVertices = true;
	}
	
	// get the number of paths stored
	public int numberOfPaths() {
		return m_paths.size();
	}
	
	// get a path based on its index
	public Path getPath(int index) {
		if(index < 0 || index >= m_paths.size()) { return null; }
		return m_paths.elementAt(index);
	}
	
	public boolean containsPath(Path p) {
		if(p == null) { return false; }
		for(int i=0;i<m_paths.size();i++) {
			if(m_paths.elementAt(i).getName().equalsIgnoreCase(p.getName())) {
				return true;
			}
		}
		return false;
	}
	
	public boolean hasActivePath() { return m_paths.size() > 0 && m_activePath >= 0 && m_activePath < m_paths.size(); }
	
	public boolean getAutoConnectVertices() { return m_autoConnectVertices; }
	
	// get a path based on its name
	public Path getPath(String name) {
		if(name == null) { return null; }
		String data = name.trim();
		
		for(int i=0;i<m_paths.size();i++) {
			if(m_paths.elementAt(i).getName().equalsIgnoreCase(data)) {
				return m_paths.elementAt(i);
			}
		}
		return null;
	}
	
	public Vertex getSelectedVertex() { return m_selectedVertex; }
	
	public boolean choosePath() {
		if(m_paths.size() == 0) {
			JOptionPane.showMessageDialog(null, "Please create a path first!", "No Paths Available", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		Object[] pathNames = new Object[m_paths.size()];
		for(int i=0;i<m_paths.size();i++) {
			pathNames[i] = m_paths.elementAt(i).getName();
		}
		Object input = JOptionPane.showInputDialog(null, "Please choose a path to edit.", "Choose Path", JOptionPane.QUESTION_MESSAGE, null, pathNames, (m_activePath < 0 || m_activePath >= m_paths.size()) ? null : pathNames[m_activePath]);
		if(input == null) { return false; }
		
		for(int i=0;i<pathNames.length;i++) {
			if(input == pathNames[i]) {
				m_activePath = i;
				clearSelection();
				return true;
			}
		}
		return false;
	}
	
	public boolean setActivePath(int index) {
		if(index < 0 || index >= m_paths.size()) { return false; }
		if(m_activePath != index) {
			m_activePath = index;
			clearSelection();
		}
		return true;
	}
	
	public boolean setActivePath(String pathName) {
		if(pathName == null) { return false; }
		String data = pathName.trim();
		
		for(int i=0;i<m_paths.size();i++) {
			if(m_paths.elementAt(i).getName().equalsIgnoreCase(data)) {
				if(m_activePath != i) {
					clearSelection();
					m_activePath = i;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean setActivePath(Path path) {
		for(int i=0;i<m_paths.size();i++) {
			if(m_paths.elementAt(i).equals(path)) {
				if(m_activePath != i) {
					clearSelection();
					m_activePath = i;
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean createPath() {
		String pathName = JOptionPane.showInputDialog(null, "Please enter a name for this path:", "Path Name", JOptionPane.QUESTION_MESSAGE);
		if(pathName == null) { return false; }
		
		if(getPath(pathName) != null) {
			JOptionPane.showMessageDialog(null, "This path name already exists!\nPlease choose another name.", "Duplicate Path Name", JOptionPane.ERROR_MESSAGE);
			return createPath();
		}
		
		Path newPath = new Path(pathName);
		addPath(newPath);
		
		if(m_paths.size() == 1) { return true; }
		
		boolean activatePath = JOptionPane.showConfirmDialog(null, "Path created successfully!\nSet new path as active for editing?", "Set Path As Active?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
		
		if(activatePath) {
			m_activePath = m_paths.size() - 1;
			clearSelection();
		}
		
		return true;
	}
	
	// add a path
	public boolean addPath(Path p) {
		if(p == null || containsPath(p)) { return false; }
		
		m_paths.add(p);
		if(m_paths.size() == 1) {
			m_activePath = 0;
		}
		return true;
	}
	
	public boolean removePath() {
		if(m_paths.size() == 0) {
			JOptionPane.showMessageDialog(null, "No paths to remove!", "No Paths Available", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		Object[] pathNames = new Object[m_paths.size()];
		for(int i=0;i<m_paths.size();i++) {
			pathNames[i] = m_paths.elementAt(i).getName();
		}
		Object input = JOptionPane.showInputDialog(null, "Please choose a path to remove.", "Choose Path", JOptionPane.QUESTION_MESSAGE, null, pathNames, (m_activePath < 0 || m_activePath >= m_paths.size()) ? null : pathNames[m_activePath]);
		if(input == null) { return false; }
		
		for(int i=0;i<pathNames.length;i++) {
			if(input == pathNames[i]) {
				removePath(i);
				return true;
			}
		}
		return false;
	}
	
	public boolean removePath(int pathIndex) {
		if(pathIndex < 0 || pathIndex >= m_paths.size()) { return false; }
		if(m_activePath == pathIndex) {
			m_activePath = -1;
		}
		else if(m_activePath > pathIndex) {
			m_activePath--;
		}
		m_paths.remove(pathIndex);
		return true;
	}
	
	public boolean removePath(String pathName) {
		if(pathName == null) { return false; }
		String data = pathName.trim();
		
		for(int i=0;i<m_paths.size();i++) {
			if(m_paths.elementAt(i).getName().equalsIgnoreCase(data)) {
				return removePath(i);
			}
		}
		return false;
	}
	
	public boolean removePath(Path path) {
		if(path == null) { return false; }
		
		for(int i=0;i<m_paths.size();i++) {
			if(m_paths.elementAt(i).equals(path)) {
				return removePath(i);
			}
		}
		return false;
	}
	
	public boolean removeSelectedVertex() {
		if(!hasActivePath() || m_selectedVertex == null) { return false; }
		
		boolean vertexRemoved = m_paths.elementAt(m_activePath).removeVertex(m_selectedVertex);
		if(vertexRemoved) {
			m_selectedVertex = null;
			return true;
		}
		return false;
	}
	
	public void setAutoConnectVertices(boolean autoConnectVertices) { m_autoConnectVertices = autoConnectVertices; }
	
	public void createVertex() {
		Vertex newVertex = new Vertex(m_selectedPoint.x, m_selectedPoint.y);
		m_paths.elementAt(m_activePath).addVertex(newVertex);
		m_lastSelectedVertex = null;
		m_selectedVertex = null;
	}
	
	// read a set of paths out of a path data file
	public static PathSystem readFrom(String fileName) {
		if(fileName == null) { return null; }
		
		BufferedReader in;
		String input, data;
		Path newPath;
		PathSystem newPathSystem = new PathSystem();
		
		try {
			// open the path data file
			in = new BufferedReader(new FileReader(fileName));
			
			// initialize the path
			newPath = new Path();
			
			while((input = in.readLine()) != null) {
				data = input.trim();
				
				// ignore empty lines
				if(data.length() == 0) {
					continue;
				}
				
				// parse a path name
				if(data.charAt(0) == '[' && data.charAt(data.length() - 1) == ']') {
					// if a path was already read, then store it
					if(newPath.numberOfEdges() > 0) {
						newPathSystem.addPath(newPath);
						newPath = new Path();
					}
					
					//set the name for the new path
					newPath.setName(data.substring(1, data.length() - 1).trim());
				}
				// parse an edge and store it in the current path
				else {
					Edge e = Edge.parseFrom(data);
					if(e != null) {
						newPath.addEdge(e);
					}
				}
			}
			
			// if the end of the file is reached while a path was being parsed, store it
			if(newPath.numberOfEdges() > 0) {
				newPathSystem.addPath(newPath);
			}
			
			in.close();
		}
		catch(IOException e) {
			System.out.println("ERROR: Unable to read path data file.");
			return null;
		}
		
		return newPathSystem;
	}
	
	public boolean writeTo(String fileName) {
		if(fileName == null) { return false; }
		
		PrintWriter out;
		try {
			out = new PrintWriter(new FileWriter(fileName));
			
			for(int i=0;i<m_paths.size();i++) {
				out.println("[" + m_paths.elementAt(i).getName() + "]");
				m_paths.elementAt(i).writeTo(out);
				out.println();
			}
			
			out.close();
		}
		catch(IOException e) {
			System.out.println("ERROR: Unable to write path data to file.");
			return false;
		}
		return true;
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { }
	public void mouseExited(MouseEvent e) { }
	
	public void mousePressed(MouseEvent e) {
		if(m_activePath < 0) { return; }
		
		if(e.getButton() == MouseEvent.BUTTON1) {
			if(e.getWhen() - m_lastMouseDown < m_doubleClickSpeed) {
				Vertex newVertex = new Vertex(e.getPoint());
				m_paths.elementAt(m_activePath).addVertex(newVertex);
				if(m_autoConnectVertices && m_lastSelectedVertex != null) {
					m_paths.elementAt(m_activePath).addEdge(new Edge(m_lastSelectedVertex, newVertex));
				}
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON2) {
			m_vertexToMove = null;
			Vertex previousVertex = m_selectedVertex;
			
			m_selectedPoint = e.getPoint();
			selectVertex(e.getPoint(), DEFAULT_SELECTION_RADIUS);
			m_vertexToMove = m_selectedVertex;
			
			m_selectedVertex = previousVertex;
			m_lastSelectedVertex = m_selectedVertex;
		}
		
		m_lastMouseDown = e.getWhen();
	}
	
	public void mouseReleased(MouseEvent e) {
		if(m_activePath < 0) { return; }
		
		if(e.getButton() == MouseEvent.BUTTON3) {
			m_selectedPoint = e.getPoint();
			selectVertex(e.getPoint(), DEFAULT_SELECTION_RADIUS);
		}
		else if(e.getButton() == MouseEvent.BUTTON2) {
			m_vertexToMove = null;
		}
		else if(e.getButton() == MouseEvent.BUTTON1) {
			Vertex previousVertex = null;
			if(m_selectedVertex != null) {
				previousVertex = m_selectedVertex; 
			}
			m_selectedPoint = e.getPoint();
			selectVertex(e.getPoint(), DEFAULT_SELECTION_RADIUS);
			
			if(previousVertex != null && m_selectedVertex != null && !previousVertex.equals(m_selectedVertex)) {
				Edge newEdge = new Edge(previousVertex, m_selectedVertex);
				
				if(m_paths.elementAt(m_activePath).containsEdge(newEdge)) {
					return;
				}
				
				if(JOptionPane.showConfirmDialog(null, "Create edge?", "Edge Creation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
					m_paths.elementAt(m_activePath).addEdge(newEdge);
				}
			}
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		if(m_activePath < 0) { return; }
		
		m_mousePosition = e.getPoint();
		if(m_vertexToMove != null) {
			m_vertexToMove.x = e.getX();
			m_vertexToMove.y = e.getY();
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(m_activePath < 0) { return; }
		
		m_mousePosition = e.getPoint();
	}
	
	private void selectVertex(Point p, int r) {
		if(p == null) { return; }
		if(r <= 0) { r = DEFAULT_SELECTION_RADIUS; }
		m_selectedVertex = null;
		for(int i=0;i<m_paths.elementAt(m_activePath).numberOfVertices();i++) {
			Vertex v = m_paths.elementAt(m_activePath).getVertex(i);
			if(Math.sqrt( Math.pow( (m_selectedPoint.x - v.x) , 2) + Math.pow( (m_selectedPoint.y - v.y) , 2) ) <= r) {
				m_selectedVertex = v;
				m_lastSelectedVertex = m_selectedVertex;
			}
		}
	}
	
	public void clearSelection() {
		m_selectedPoint = null;
		m_selectedVertex = null;
		m_lastSelectedVertex = null;
		m_vertexToMove = null;
		m_mousePosition = null;
	}
	
	public void drawAll(Graphics g) {
		if(g == null) { return; }
		
		for(int i=0;i<m_paths.size();i++) {
			m_paths.elementAt(i).draw(g, Color.GREEN, Color.BLUE);
		}
	}
	
	public void draw(Graphics g) {
		if(g == null || m_activePath < 0 || m_activePath >= m_paths.size()) { return; }
		
		m_paths.elementAt(m_activePath).draw(g, Color.GREEN, Color.BLUE);
	}
	
	public String toString() {
		String s = "";
		for(int i=0;i<m_paths.size();i++) {
			s += m_paths.elementAt(i);
			if(i < m_paths.size() - 1) {
				s += '\n';
			}
		}
		return s;
	}
	
}
