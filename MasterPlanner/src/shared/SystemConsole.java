package shared;

import java.util.Vector; 

public class SystemConsole {

    private Vector<SystemConsoleEntry> m_consoleEntries;

    public SystemConsole() {
    	m_consoleEntries = new Vector<SystemConsoleEntry>();
    }
    
    public int size() {
    	return m_consoleEntries.size();
    }
    
    public SystemConsoleEntry getConsoleEntry(int index) {
		if(index < 0 || index >= m_consoleEntries.size()) {
		    return null;
		}
		return m_consoleEntries.elementAt(index);
    }
    
    public void writeLine(String text) {
		m_consoleEntries.add(new SystemConsoleEntry(text));
    }
    
    public void clear() {
    	m_consoleEntries.clear();
    }
    
}
