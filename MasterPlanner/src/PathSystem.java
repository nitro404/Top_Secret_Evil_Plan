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
	private boolean m_autoConnectVertices;
	final private static long m_doubleClickSpeed = 200;
	private long m_lastMouseDown = 0; 
	
	public PathSystem() {
		m_paths = new Vector<Path>();
		
		m_activePath = -1;
		m_selectedPoint = null;
		m_selectedVertex = null;
		m_lastSelectedVertex = null;
		m_vertexToMove = null;
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
	
	public boolean containsPath(String pathName) {
		if(pathName == null) { return false; }
		String value = pathName.trim();
		if(value.length() == 0) { return false; }
		
		for(int i=0;i<m_paths.size();i++) {
			if(m_paths.elementAt(i).getName().equalsIgnoreCase(value)) {
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
		
		if(containsPath(pathName)) {
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
	
	public boolean renamePath() {
		if(m_paths.size() == 0) {
			JOptionPane.showMessageDialog(null, "Please create a path first!", "No Paths Available", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		
		Object[] pathNames = new Object[m_paths.size()];
		for(int i=0;i<m_paths.size();i++) {
			pathNames[i] = m_paths.elementAt(i).getName();
		}
		Object input = JOptionPane.showInputDialog(null, "Please choose a path to rename.", "Choose Path", JOptionPane.QUESTION_MESSAGE, null, pathNames, (m_activePath < 0 || m_activePath >= m_paths.size()) ? null : pathNames[m_activePath]);
		if(input == null) { return false; }
		
		int pathIndex = -1;
		for(int i=0;i<pathNames.length;i++) {
			if(input == pathNames[i]) {
				pathIndex = i;
				break;
			}
		}
		
		return renamePath(pathIndex);
	}
	
	public boolean renamePath(int pathIndex) {
		if(m_paths.size() == 0 || pathIndex < 0 || pathIndex >= m_paths.size()) { return false; }
		
		String newPathName = JOptionPane.showInputDialog(null, "Please enter a new name for this path:", "Rename Path", JOptionPane.QUESTION_MESSAGE);
		if(newPathName == null) { return false; }
		
		if(containsPath(newPathName)) {
			JOptionPane.showMessageDialog(null, "This path name already exists!\nPlease choose another name.", "Duplicate Path Name", JOptionPane.ERROR_MESSAGE);
			return renamePath(pathIndex);
		}
		
		String value = newPathName.trim();
		if(value.length() == 0) {
			JOptionPane.showMessageDialog(null, "Path names must contain at least one character!\nPlease choose another name.", "Invalid Path Name", JOptionPane.ERROR_MESSAGE);
			return renamePath(pathIndex);
		}
		
		String oldPathName = m_paths.elementAt(pathIndex).getName();
		m_paths.elementAt(pathIndex).setName(value);
		
		boolean updateTasks = JOptionPane.showConfirmDialog(null, "Update references to path \"" + oldPathName + "\" in Task Manager to new path name?", "Update Tasks?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION;
		
		if(updateTasks) {
			SystemManager.taskManager.renamePathReferences(oldPathName, newPathName);
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
			clearSelection();
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
	
	public static Path getDefaultPath() {
		Path defaultPath = new Path("Main Path");
		Vertex[] vertices = new Vertex[] { new Vertex(269, 777), new Vertex(319, 715), new Vertex(273, 656), new Vertex(269, 871), new Vertex(273, 565), new Vertex(390, 1104), new Vertex(390, 1038), new Vertex(390, 868), new Vertex(378, 386), new Vertex(378, 329), new Vertex(378, 566), new Vertex(285, 715), new Vertex(271, 822), new Vertex(203, 882), new Vertex(122, 882), new Vertex(278, 608), new Vertex(206, 550), new Vertex(119, 550), new Vertex(58, 807), new Vertex(58, 617), new Vertex(82, 877), new Vertex(85, 548), new Vertex(122, 770), new Vertex(117, 664), new Vertex(261, 715), new Vertex(261, 604), new Vertex(56, 1041), new Vertex(73, 1015), new Vertex(171, 1015), new Vertex(68, 393), new Vertex(226, 421), new Vertex(226, 1015), new Vertex(123, 1104), new Vertex(171, 1104), new Vertex(134, 330), new Vertex(173, 330), new Vertex(81, 421), new Vertex(173, 421), new Vertex(320, 826), new Vertex(417, 826), new Vertex(417, 742), new Vertex(417, 608), new Vertex(573, 886), new Vertex(596, 872), new Vertex(596, 563), new Vertex(498, 563), new Vertex(572, 543), new Vertex(236, 1313), new Vertex(53, 1160), new Vertex(191, 160), new Vertex(53, 284), new Vertex(390, 1313), new Vertex(390, 1171), new Vertex(553, 1171), new Vertex(553, 1150), new Vertex(378, 160), new Vertex(378, 262), new Vertex(538, 262), new Vertex(538, 291), new Vertex(556, 1070), new Vertex(541, 376) };
		Edge[] edges = new Edge[] { new Edge(new Vertex(269, 777), new Vertex(319, 715)), new Edge(new Vertex(319, 715), new Vertex(273, 656)), new Edge(new Vertex(269, 777), new Vertex(269, 871)), new Edge(new Vertex(273, 656), new Vertex(273, 565)), new Edge(new Vertex(390, 1104), new Vertex(390, 1038)), new Edge(new Vertex(390, 1038), new Vertex(390, 868)), new Edge(new Vertex(378, 386), new Vertex(378, 329)), new Edge(new Vertex(378, 566), new Vertex(378, 386)), new Edge(new Vertex(319, 715), new Vertex(285, 715)), new Edge(new Vertex(390, 868), new Vertex(271, 822)), new Edge(new Vertex(271, 822), new Vertex(203, 882)), new Edge(new Vertex(122, 882), new Vertex(203, 882)), new Edge(new Vertex(378, 566), new Vertex(278, 608)), new Edge(new Vertex(278, 608), new Vertex(206, 550)), new Edge(new Vertex(206, 550), new Vertex(119, 550)), new Edge(new Vertex(122, 882), new Vertex(58, 807)), new Edge(new Vertex(119, 550), new Vertex(58, 617)), new Edge(new Vertex(122, 882), new Vertex(82, 877)), new Edge(new Vertex(119, 550), new Vertex(85, 548)), new Edge(new Vertex(58, 807), new Vertex(122, 770)), new Edge(new Vertex(58, 617), new Vertex(117, 664)), new Edge(new Vertex(261, 715), new Vertex(261, 604)), new Edge(new Vertex(56, 1041), new Vertex(73, 1015)), new Edge(new Vertex(73, 1015), new Vertex(171, 1015)), new Edge(new Vertex(226, 1015), new Vertex(171, 1015)), new Edge(new Vertex(123, 1104), new Vertex(56, 1041)), new Edge(new Vertex(123, 1104), new Vertex(171, 1104)), new Edge(new Vertex(171, 1104), new Vertex(171, 1015)), new Edge(new Vertex(171, 1104), new Vertex(390, 1104)), new Edge(new Vertex(134, 330), new Vertex(68, 393)), new Edge(new Vertex(134, 330), new Vertex(173, 330)), new Edge(new Vertex(81, 421), new Vertex(68, 393)), new Edge(new Vertex(81, 421), new Vertex(173, 421)), new Edge(new Vertex(173, 421), new Vertex(173, 330)), new Edge(new Vertex(226, 421), new Vertex(173, 421)), new Edge(new Vertex(378, 329), new Vertex(173, 330)), new Edge(new Vertex(285, 715), new Vertex(261, 715)), new Edge(new Vertex(319, 715), new Vertex(320, 826)), new Edge(new Vertex(417, 826), new Vertex(320, 826)), new Edge(new Vertex(417, 742), new Vertex(417, 826)), new Edge(new Vertex(417, 608), new Vertex(417, 742)), new Edge(new Vertex(278, 608), new Vertex(417, 608)), new Edge(new Vertex(417, 742), new Vertex(573, 886)), new Edge(new Vertex(573, 886), new Vertex(596, 872)), new Edge(new Vertex(596, 872), new Vertex(596, 563)), new Edge(new Vertex(596, 563), new Vertex(498, 563)), new Edge(new Vertex(573, 886), new Vertex(498, 563)), new Edge(new Vertex(236, 1313), new Vertex(53, 1160)), new Edge(new Vertex(191, 160), new Vertex(53, 284)), new Edge(new Vertex(498, 563), new Vertex(572, 543)), new Edge(new Vertex(572, 543), new Vertex(596, 563)), new Edge(new Vertex(498, 563), new Vertex(278, 608)), new Edge(new Vertex(390, 1313), new Vertex(236, 1313)), new Edge(new Vertex(390, 1313), new Vertex(390, 1171)), new Edge(new Vertex(390, 1171), new Vertex(390, 1104)), new Edge(new Vertex(553, 1171), new Vertex(390, 1171)), new Edge(new Vertex(553, 1150), new Vertex(553, 1171)), new Edge(new Vertex(378, 160), new Vertex(191, 160)), new Edge(new Vertex(378, 160), new Vertex(378, 262)), new Edge(new Vertex(378, 262), new Vertex(378, 329)), new Edge(new Vertex(538, 262), new Vertex(378, 262)), new Edge(new Vertex(538, 262), new Vertex(538, 291)), new Edge(new Vertex(553, 1150), new Vertex(556, 1070)), new Edge(new Vertex(538, 291), new Vertex(541, 376)) };
		for(int i=0;i<vertices.length;i++) { defaultPath.addVertex(vertices[i]); }
		for(int i=0;i<edges.length;i++) { defaultPath.addEdge(edges[i]); }
		return defaultPath;
	}
	
	// read a set of paths out of a path data file
	public static PathSystem readFrom(String fileName) {
		if(fileName == null) { return null; }
		
		BufferedReader in;
		String input, data;
		Path newPath;
		PathSystem newPathSystem = new PathSystem();
		Variable v;
		int numberOfVertices = 0;
		int numberOfEdges = 0;
		
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
						numberOfVertices = 0;
						numberOfEdges = 0;
					}
					
					//set the name for the new path
					newPath.setName(data.substring(1, data.length() - 1).trim());
					
					// get the vertex list header and parse it
					v = Variable.parseFrom(input = in.readLine());
					if(v == null) { return newPathSystem; }
					
					// verify the vertex list header
					if(!v.getID().equalsIgnoreCase("Vertices")) { return newPathSystem; }
					
					// parse and verify the number of vertices
					try { numberOfVertices = Integer.parseInt(v.getValue()); }
					catch(NumberFormatException e) { return newPathSystem; }
					
					// parse the vertices
					for(int i=0;i<numberOfVertices;i++) {
						newPath.addVertex(Vertex.parseFrom(input = in.readLine()));
					}
					
					// get the edge list header and parse it
					v = Variable.parseFrom(input = in.readLine());
					if(v == null) { return newPathSystem; }
					
					// verify the edge list header
					if(!v.getID().equalsIgnoreCase("Edges")) { return newPathSystem; }
					
					// parse and verify the number of vertices
					try { numberOfEdges = Integer.parseInt(v.getValue()); }
					catch(NumberFormatException e) { return newPathSystem; }
					
					// parse an edge and store it in the current path
					for(int i=0;i<numberOfEdges;i++) {
						newPath.addEdge(Edge.parseFrom(input = in.readLine()));
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
				out.println("Vertices: " + m_paths.elementAt(i).numberOfVertices());
				for(int j=0;j<m_paths.elementAt(i).numberOfVertices();j++) {
					out.print("\t");
					m_paths.elementAt(i).getVertex(j).writeTo(out);
					out.println();
				}
				out.println("Edges: " + m_paths.elementAt(i).numberOfEdges());
				for(int j=0;j<m_paths.elementAt(i).numberOfEdges();j++) {
					out.print("\t");
					m_paths.elementAt(i).getEdge(j).writeTo(out);
					out.println();
				}
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
			else {
				m_vertexToMove = null;
			}
		}
		else if(e.getButton() == MouseEvent.BUTTON2) {
			m_vertexToMove = null;
			Vertex previousVertex = m_selectedVertex;
			
			m_selectedPoint = e.getPoint();
			selectVertex(e.getPoint(), Path.DEFAULT_SELECTION_RADIUS);
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
			selectVertex(e.getPoint(), Path.DEFAULT_SELECTION_RADIUS);
		}
		else if(e.getButton() == MouseEvent.BUTTON2) {
			m_selectedVertex = null;
			m_lastSelectedVertex = null;
		}
		else if(e.getButton() == MouseEvent.BUTTON1) {
			Vertex previousVertex = null;
			if(m_selectedVertex != null) {
				previousVertex = m_selectedVertex; 
			}
			m_selectedPoint = e.getPoint();
			selectVertex(e.getPoint(), Path.DEFAULT_SELECTION_RADIUS);
			
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
		
		if(m_vertexToMove != null) {
			if(Position.isValid(e.getPoint())) {
				m_vertexToMove.x = e.getX();
				m_vertexToMove.y = e.getY();
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) { }
	
	private void selectVertex(Point p, int r) {
		if(p == null) { return; }
		if(r <= 0) { r = Path.DEFAULT_SELECTION_RADIUS; }
		m_selectedVertex = null;
		for(int i=0;i<m_paths.elementAt(m_activePath).numberOfVertices();i++) {
			Vertex v = m_paths.elementAt(m_activePath).getVertex(i);
			if(Math.sqrt( Math.pow( (m_selectedPoint.x - v.x) , 2) + Math.pow( (m_selectedPoint.y - v.y) , 2) ) <= r) {
				m_selectedVertex = v;
				m_lastSelectedVertex = m_selectedVertex;
				return;
			}
		}
	}
	
	public Path getSelectedPath(Point p) { return getSelectedPath(p, Path.DEFAULT_SELECTION_RADIUS); }
	
	public Path getSelectedPath(Point p, int r) {
		if(p == null) { return null; }
		if(r <= 0) { r = Path.DEFAULT_SELECTION_RADIUS; }
		for(int i=0;i<m_paths.size();i++) {
			for(int j=0;j<m_paths.elementAt(i).numberOfVertices();j++) {
				Vertex v = m_paths.elementAt(i).getVertex(j);
				if(Math.sqrt( Math.pow( (p.x - v.x) , 2) + Math.pow( (p.y - v.y) , 2) ) <= r) {
					return m_paths.elementAt(i);
				}
			}
		}
		return null;
	}
	
	public void clearSelection() {
		m_selectedPoint = null;
		m_selectedVertex = null;
		m_lastSelectedVertex = null;
		m_vertexToMove = null;
	}
	
	public void drawAll(Graphics g) {
		if(g == null) { return; }
		
		for(int i=0;i<m_paths.size();i++) {
			m_paths.elementAt(i).draw(g, SystemManager.settings.getEdgeColour(), SystemManager.settings.getVertexColour());
		}
	}
	
	public void draw(Graphics g) {
		if(g == null || m_activePath < 0 || m_activePath >= m_paths.size()) { return; }
		
		m_paths.elementAt(m_activePath).draw(g, SystemManager.settings.getEdgeColour(), SystemManager.settings.getVertexColour());
		
		if(m_vertexToMove != null) {
			m_vertexToMove.drawSelection(g, SystemManager.settings.getSelectedColour());
		}
		else if(m_lastSelectedVertex != null) {
			m_lastSelectedVertex.drawSelection(g, SystemManager.settings.getSelectedColour());
		}
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
