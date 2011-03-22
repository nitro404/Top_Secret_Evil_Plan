package settings;

public class SettingsManager {
	
	private VariableSystem m_settings;
	
	private String m_pathDataFileName;
	private int m_frameRate; 
	
	final public static String defaultSettingsFileName = "planner.ini";
	final public static String defaultPathDataFileName = "paths.ini";
	final public static int defaultFrameRate = 2;
	
	public SettingsManager() {
		m_settings = new VariableSystem();
		m_pathDataFileName = defaultPathDataFileName;
		m_frameRate = defaultFrameRate;
	}
	
	public String getPathDataFile() { return m_pathDataFileName; }
	
	public int getFrameRate() { return m_frameRate; }
	
	public void setPathDataFile(String fileName) {
		if(fileName == null) { return; }
		m_pathDataFileName = fileName;
	}
	
	public void setFrameRate(int frameRate) {
		if(frameRate >= 1 && frameRate <= 30) {
			m_frameRate = frameRate;
		}
	}
	
	public boolean load() { return loadFrom(defaultSettingsFileName); }
	
	public boolean save() { return saveTo(defaultSettingsFileName); }
	
	public boolean loadFrom(String fileName) {
		VariableSystem variables = VariableSystem.readFrom(fileName);
		if(variables == null) {
			System.out.println("ERROR: Unable to load settings file: \"" + fileName + "\".");
			return false;
		}
		
		m_settings = variables;
		
		// create local variables instantiated with data parsed from the variable system
		setPathDataFile(m_settings.getValue("Path File", "Data Files"));
		try { setFrameRate(Integer.parseInt(m_settings.getValue("Framerate", "Settings"))); } catch(NumberFormatException e) { }
		
		return true;
	}
	
	public boolean saveTo(String fileName) {
		// update the variable system with the new settings values
		m_settings.setValue("Path File", m_pathDataFileName, "Data Files");
		m_settings.setValue("Framerate", m_frameRate, "Settings");
		
		// group the settings by categories
		m_settings.sort();
		
		return m_settings.writeTo(fileName);
	}
	
}
