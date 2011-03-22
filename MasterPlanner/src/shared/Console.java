package shared;

import java.util.Vector; 

public class Console {

    private Vector<ConsoleEntry> m_consoleEntries;

    public Console() {
    	m_consoleEntries = new Vector<ConsoleEntry>();
    }
    
    public int size() {
    	return m_consoleEntries.size();
    }
    
    public ConsoleEntry getConsoleEntry(int index) {
		if(index < 0 || index >= m_consoleEntries.size()) {
		    return null;
		}
		return m_consoleEntries.elementAt(index);
    }
    
    public void writeLine(String text) {
		m_consoleEntries.add(new ConsoleEntry(text));
    }
    
    public void clear() {
    	m_consoleEntries.clear();
    }
    
}
