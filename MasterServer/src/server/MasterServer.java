package server;

public class MasterServer {
	
	final public static int DEFAULT_PORT = 25500;
	final public static long QUEUE_INTERVAL = 50;
	final public static long CONNECTION_LISTEN_INTERVAL = 75;
	final public static long TIMEOUT_INTERVAL = 100;
	final public static long PING_INTERVAL = 5000;
	final public static long CONNECTION_TIMEOUT = 10000;
	
	public static void main(String[] args) {
		ServerWindow server = new ServerWindow();
		
		if(args.length == 0) {
			server.initialize();
		}
		else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("-port")) {
				int port = -1;
				try { port = Integer.parseInt(args[1]); }
				catch(NumberFormatException e) { port = DEFAULT_PORT; }
				server.initialize(port);
			}
			else {
				server.initialize();
			}
		}
	}
	
}
