import java.io.*;
import java.awt.Graphics;

/**
 * The Edge class represents a pair of Vertices which form a segmented line.
 * 
 * Last Revised: March 19, 2011
 * 
 * @author Kevin Scroggins
 */
public class Edge {
	
	/** The first Vertex of the Edge. */
	public Vertex a;
	
	/** The second Vertex of the Edge. */
	public Vertex b;
	
	/**
	 * Constructs an Edge based on the two specified Vertices.
	 * 
	 * @param a the first Vertex.
	 * @param b the second Vertex.
	 */
	public Edge(Vertex a, Vertex b) {
		this.a = a;
		this.b = b;
	}
	
	/**
	 * Returns the second Vertex of the Edge.
	 * 
	 * @return the second Vertex of the Edge.
	 */
	public Vertex getPointA() {
		return this.a;
	}
	
	/**
	 * Returns the first Vertex of the Edge.
	 * 
	 * @return the first Vertex of the Edge.
	 */
	public Vertex getPointB() {
		return this.b;
	}
	
	/**
	 * Checks to see if the specified Vertex is a member of the Edge.
	 * 
	 * @param v the Vertex to check.
	 * @return true if the specified Vertex is a member of the Edge.
	 */
	public boolean containsVertex(Vertex v) {
		return this.a == v || this.b == v;
	}
	
	/**
	 * Computes the vertical distance between the two Vertices and returns it.
	 * 
	 * @return the vertical distance between the two Vertices.
	 */
	public double getDeltaX() {
		return this.b.x - this.a.x;
	}

	/**
	 * Computes the horizontal distance between the two Vertices and returns it.
	 * 
	 * @return the horizontal distance between the two Vertices.
	 */
	public double getDeltaY() {
		return this.b.y - this.a.y;
	}
	
	/**
	 * Computes the length of the edge and returns it.
	 * 
	 * @return the length of the edge.
	 */
	public double length() {
		return Math.sqrt(Math.pow(b.x - a.x, 2) + Math.pow(b.y - a.y, 2));
	}
	
	/**
	 * Creates an Edge from the specified String and returns it.
	 * 
	 * Parses the Edge from a String of the form:
	 * "x1, y1; x2, y2" where
	 * x1 and y1 are the x and y coordinates of the first Vertex of the Edge and
	 * x2 and y2 are the x and y coordinates of the second Vertex of the Edge.
	 * 
	 * @param input the String to parse the Edge from.
	 * @return the Edge parsed from the String.
	 */
	public static Edge parseFrom(String input) {
		if(input == null || input.trim().length() == 0) {
			return null;
		}
		
		String data = input.trim();
		
		// locate the separator character representing the delimiter between the two vertex strings
		int separatorIndex = -1;
		boolean firstSeparator = true;
		for(int i=0;i<data.length();i++) {
			if(data.charAt(i) == ',') {
				if(firstSeparator) {
					firstSeparator = false;
				}
				else {
					separatorIndex = i;
					break;
				}
			}
		}
		
		// extract the strings representing each vertex, then parse and store them
		Vertex a = Vertex.parseFrom(data.substring(0, separatorIndex));
		Vertex b = Vertex.parseFrom(data.substring(separatorIndex + 1, data.length()));
		
		return new Edge(a, b);
	}
	
	/**
	 * Writes the Edge to the specified PrintWriter.
	 * 
	 * Outputs the Edge to the form:
	 * "(x1, y1), (x2, y2)" where
	 * x1 and y1 are the x and y coordinates of the first Vertex of the Edge and
	 * x2 and y2 are the x and y coordinates of the second Vertex of the Edge.
	 * 
	 * @param out the PrintWriter to write the Edge to.
	 * @throws IOException if there was an error writing to the output stream.
	 */
	public void writeTo(PrintWriter out) throws IOException {
		a.writeTo(out);
		out.print(", ");
		b.writeTo(out);
	}
	
	/**
	 * Renders the Edge onto the specified Graphics object.
	 * 
	 * @param g the Graphics object to render the Edge onto.
	 */
	public void paintOn(Graphics g) {
		g.drawLine(a.x, a.y, b.x, b.y);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Edge)) {
			return false;
		}
		
		Edge e = (Edge) o;
		
		// check to see that each Vertex in the Edge matches the corresponding Vertex of the other (undirected)
		return this.a == e.a && this.b == e.b ||
			   this.a == e.b && this.b == e.a;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// return a String representation of the Edge in the form (x1, y1) (x2, y2)
		return "(" + this.a.x + ", " + this.a.y + ") (" + this.b.x + ", " + this.b.y + ")";
	}
	
}
