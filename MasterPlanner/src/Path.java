import java.awt.Point;
import java.io.*;

public class Path extends Graph {
	
	private String m_name;
	
	final public static int DEFAULT_SELECTION_RADIUS = 6;
	
	public Path() {
		this(null);
	}
	
	public Path(String name) {
		super();
		m_name = (name == null) ? "" : name;
	}
	
	public String getName() {
		return m_name;
	}
	
	public void setName(String name) {
		if(name == null) { return; }
		m_name = name;
	}
	
	public int getSelectedVertexIndex(Point p) { return getSelectedVertexIndex(p, DEFAULT_SELECTION_RADIUS); }
	
	public int getSelectedVertexIndex(Point p, int r) {
		if(p == null) { return -1; }
		if(r <= 0) { r = DEFAULT_SELECTION_RADIUS; }
		for(int i=0;i<vertices.size();i++) {
			Vertex v = vertices.elementAt(i);
			if(Math.sqrt( Math.pow( (p.x - v.x) , 2) + Math.pow( (p.y - v.y) , 2) ) <= r) {
				return i;
			}
		}
		return -1;
	}
	
	// parse a path from a file
	public static Path readFrom(String fileName) {
		if(fileName == null) { return null; }
		
		BufferedReader in;
		Path p = null;
		
		try {
			in = new BufferedReader(new FileReader(fileName));
			
			// parse the path from the buffer
			p = parseFrom(in);
			
			in.close();
		}
		catch(IOException e) {
			System.out.println("ERROR: Unable to open path data file: \"" + fileName + "\".");
			return null;
		}
		
		return p;
	}
	
	// parse a path from an input buffer
	public static Path parseFrom(BufferedReader in) {
		if(in == null) { return null; }
		
		String input, data;
		Path newPath = new Path();
		
		try {
			while((input = in.readLine()) != null) {
				data = input.trim();
				
				if(data.length() == 0) {
					continue;
				}
				
				// parse the path's name
				if(data.charAt(0) == '[' && data.charAt(data.length() - 1) == ']') {
					newPath.setName(data.substring(1, data.length() - 1));
				}
				// parse an edge and store it in the path
				else {
					Edge e = Edge.parseFrom(data);
					if(e != null) {
						newPath.addEdge(e);
					}
				}
			}
		}
		catch(IOException e) {
			System.out.println("ERROR: Error encountered reading from path data file.");
			return null;
		}
		
		return newPath;
	}
	
	public String toString() {
		return (m_name.length() == 0) ? "" : m_name + ": " + super.toString();
	}
	
}
