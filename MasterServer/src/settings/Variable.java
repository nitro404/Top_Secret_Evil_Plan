package settings;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * The Variable class represents a binding between an id and a value.
 * 
 * Last Revised: Last Revised: March 19, 2011
 * 
 * @author Kevin Scroggins
 */
public class Variable {
	
	/**
	 * The id associated with the current Variable.
	 */
	private int m_category = NO_CATEGORY;
	
	/**
	 * The id associated with the current Variable.
	 */
	private String m_id;
	
	/**
	 * The value assigned to the current Variable.
	 */
	private String m_value;
	
	/**
	 * The character used to separate the id from the value.
	 */
	final public static char SEPARATOR_CHAR = ':';
	
	/**
	 * Value assigned to the category of a variable if it doesn't belong to a category.
	 */
	final public static int NO_CATEGORY = -1;
	
	/**
	 * Constructs an empty Variable object. 
	 */
	public Variable() {
		this("", "", NO_CATEGORY);
	}
	
	/**
	 * Constructs a Variable object instantiated with the specified id and value. 
	 * 
	 * @param id the id to associate with the current Variable.
	 * @param value the value to assign to the current Variable.
	 */
	public Variable(String id, String value) {
		this(id, value, NO_CATEGORY);
	}
	
	/**
	 * Constructs a Variable object instantiated with the specified id and value. 
	 * 
	 * @param id the id to associate with the current Variable.
	 * @param value the value to assign to the current Variable.
	 * @param category the category id to assign to the current Variable.
	 */
	public Variable(String id, String value, int category) {
		m_id = (id == null) ? "" : id.trim();
		m_value = (value == null) ? "" : value.trim();
		m_category = (category < -1) ? NO_CATEGORY : category;
	}
	
	/**
	 * Returns the category id assigned to the current Variable.
	 * 
	 * @return the category id assigned to the current Variable.
	 */
	public int getCategory() {
		return m_category;
	}
	
	/**
	 * Returns id associated with the current Variable.
	 * 
	 * @return id associated with the current Variable.
	 */
	public String getID() {
		return m_id;
	}
	
	/**
	 * Returns the value assigned to the current Variable.
	 * 
	 * @return the value assigned to the current Variable.
	 */
	public String getValue() {
		return m_value;
	}
	
	/**
	 * Changes the category associated with the current variable.
	 */
	public void setCategory(int category) {
		m_category = (category < -1) ? NO_CATEGORY : category;
	}
	
	/**
	 * Sets the id of the current Variable to the new, specified id.
	 * 
	 * @param id the new id to associate with the current Variable. 
	 */
	public void setID(String id) {
		m_id = (id == null) ? "" : id.trim();
	}
	
	/**
	 * Sets the value of the current Variable to the new, specified value.
	 * 
	 * @param value the new value to assign to the current Variable.
	 */
	public void setValue(String value) {
		m_value = (value == null) ? "" : value.trim();
	}
	
	/**
	 * Removes any category associated with the current variable.
	 */
	public void removeCategory() {
		m_category = NO_CATEGORY;
	}
	
	/**
	 * Creates a Variable from a specified String and returns it.
	 * 
	 * Parses the Variable from a String in the form:
	 * "ID: Value" where
	 * ID is the id to be associated with the Variable,
	 * Value is the data to be assigned to the value of the Variable and
	 * : is the separator character.
	 * 
	 * @param data the data to be parsed into a Variable.
	 * @return the Variable parsed from the data String.
	 */
	public static Variable parseFrom(String data) {
		if(data == null) { return null; }
		String temp = data.trim();
		if(temp.length() < 1) { return null; }
		
		// find the index of the separator character, if it exists
		int separatorIndex = temp.indexOf(SEPARATOR_CHAR);
		if(separatorIndex == -1) { return null; }
		
		// return a new Variable instantiated with the id and data parsed from the string 
		String id = temp.substring(0, separatorIndex);
		String value = temp.substring(separatorIndex + 1, temp.length());
		
		return new Variable(id, value, NO_CATEGORY);
	}
	
	/**
	 * Writes a Variable to the specified PrintWriter.
	 * 
	 * Outputs the Variable to the form:
	 * "ID: Value" where
	 * ID is the id associated with the current Variable,
	 * Value is the data assigned to the current Variable and
	 * : is the separator character.
	 * 
	 * @param out the output stream to write the Variable to.
	 * @throws IOException if there was an error writing to the output stream.
	 */
	public void writeTo(PrintWriter out) throws IOException {
		if(out == null) { return; }
		
		out.println(m_id + SEPARATOR_CHAR + " " + m_value);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		if(o == null || !(o instanceof Variable)) {
			return false;
		}
		
		Variable v = (Variable) o;
		
		// return true if the id of each Variable matches
		return m_id.equalsIgnoreCase(v.m_id);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		// return a String representation of the Variable with the id and value separated by the separator character (default is colon)
		return m_id + SEPARATOR_CHAR + " " + m_value;
	}
	
	
}
