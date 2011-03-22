package path;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class PathSystem {
	
	public Vector<Path> m_paths;
	
	public PathSystem() {
		m_paths = new Vector<Path>();
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
	
	// add a path
	public boolean addPath(Path p) {
		if(p == null) { return false; }
		m_paths.add(p);
		return true;
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
			System.out.println("ERROR: Error encountered reading from path data file.");
			return null;
		}
		
		return newPathSystem;
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
