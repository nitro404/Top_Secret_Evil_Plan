package server;

public class MasterServer {
	
	public static void main(String[] args) {
		ServerWindow server = new ServerWindow();
		
		if(args.length == 0) {
			server.initialize();
		}
		else if(args.length == 2) {
			if(args[0].equalsIgnoreCase("-port")) {
				int port = -1;
				try { port = Integer.parseInt(args[1]); }
				catch(NumberFormatException e) { port = Server.DEFAULT_PORT; }
				server.initialize(port);
			}
			else {
				server.initialize();
			}
		}
	}
	
}
