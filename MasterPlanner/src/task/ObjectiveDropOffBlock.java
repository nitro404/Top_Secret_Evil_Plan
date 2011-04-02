package task;

import java.util.StringTokenizer;
import java.io.PrintWriter;

public class ObjectiveDropOffBlock extends Objective {
	
	private byte m_dropOffLocationID;
	
	public ObjectiveDropOffBlock(byte dropOffLocationID) {
		super();
		m_objectiveType = ObjectiveType.DropOffBlock;
		m_dropOffLocationID = dropOffLocationID;
	}
	
	public byte getDropOffLocationID() { return m_dropOffLocationID; }
	
	public void setDropOffLocationID(byte dropOffLocationID) { m_dropOffLocationID = dropOffLocationID; }
	
	public void execute() {
		
	}

	public static ObjectiveDropOffBlock parseFrom(String data) {
		if(data == null) { return null; }
		StringTokenizer st = new StringTokenizer(data.trim(), " ", false);
		if(st.countTokens() < 6) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Drop")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Off")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Block")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("at")) { return null; }
		if(!st.nextToken().equalsIgnoreCase("Location")) { return null; }
		byte dropOffLocationID = -1;
		try { dropOffLocationID = Byte.parseByte(st.nextToken()); }
		catch(NumberFormatException e) { return null; }
		if(dropOffLocationID < 0) { return null; }
		return new ObjectiveDropOffBlock(dropOffLocationID);
	}
	
	public boolean writeTo(PrintWriter out) {
		if(out == null) { return false; }
		out.print("Drop Off Block at Location " + m_dropOffLocationID);
		return true;
	}
	
	public String toString() {
		return "Drop Off Block at Location " + m_dropOffLocationID;
	}

}
