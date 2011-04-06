import java.util.Vector; 

public class SystemConsole {

    private Vector<SystemConsoleEntry> m_consoleEntries;
    private Updatable m_target;
	
    public SystemConsole() {
    	m_consoleEntries = new Vector<SystemConsoleEntry>();
    }
    
    public void setTarget(Updatable target) {
    	m_target = target;
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
		while(m_consoleEntries.size() > SystemManager.settings.getMaxConsoleHistory()) {
			m_consoleEntries.remove(0);
		}
		if(m_target != null) { m_target.update(); }
    }
    
    public void clear() {
    	m_consoleEntries.clear();
    	if(m_target != null) { m_target.update(); }
    }
    
    public String toString() {
    	String data = "";
		for(int i=0;i<m_consoleEntries.size();i++) {
			data += m_consoleEntries.elementAt(i).getText() + "\n";
		}
		return data;
    }
    
}
