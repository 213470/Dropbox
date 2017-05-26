package server;

import connection.Server;

public class DropboxServer {
	
	private static int PORT_NUMBER = 6066;

	public static void main(String[] args) {
		
//		new Instanciation(args[0]);
		
		Server connection = new Server(PORT_NUMBER, args[0]);
		// Listening for client connected
		connection.start();
	}

}
