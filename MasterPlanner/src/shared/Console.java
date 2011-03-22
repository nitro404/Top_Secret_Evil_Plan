package shared;

import java.util.Vector; 
/*
 * -clear, writeline/printline, consoleEntry collection {put to list, then append to txtfield}
 */


public class Console {

    private Vector<ConsoleEntry> m_consoleEntries;

    public Console() {
	m_consoleEntries = new Vector<ConsoleEntry>();
    }

    public ConsoleEntry getConsoleEntry(int index) {
	if (index < 0 || index >= m_consoleEntries.size()) {
	    return null;
	}
	return m_consoleEntries.elementAt(index);
    }

    public void clear() {
	m_consoleEntries.clear();
	//if(console_window != null) { console_window.clear(); }	
    }

    public void writeLine(String text) {
	ConsoleEntry e = new ConsoleEntry(text);
	m_consoleEntries.add(e);
	//if(console_window != null) { consol_ewindow.addConsoleEntry(e); }
    }
    
    public int size() {
	return m_consoleEntries.size();
    }
}
