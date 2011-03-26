package server;

import settings.*;
import shared.*;

public class SystemManager {
	
	public static SettingsManager settings;
	public static SystemConsole console;
	public static ServerWindow serverWindow;
	public static Server server;
	
	public static void initialize() {
		initialize(-1);
	}
	
	public static void initialize(int port) {
		settings = new SettingsManager();
		settings.load();
		
		console = new SystemConsole();
		
		server = new Server();
		serverWindow = new ServerWindow();
		
		server.initialize(port);
		serverWindow.initialize();
	}
	
}
