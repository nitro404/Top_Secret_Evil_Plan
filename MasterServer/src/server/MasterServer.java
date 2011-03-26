package server;

public class MasterServer {
	
	
	public static void main(String[] args) {
		int port = -1;
		
		if(args.length == 2) {
			if(args[0].equalsIgnoreCase("-port")) {
				try { port = Integer.parseInt(args[1]); }
				catch(NumberFormatException e) { }
			}
		}
		
		SystemManager.initialize(port);
	}
	
}
