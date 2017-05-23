package server;

import connection.ServerConnector;

public class DropboxServer {
	
	private static int PORT_NUMBER = 6066;

	public static void main(String[] args) {
		
		new Instanciation(args[0]);
		
		ServerConnector connection = new ServerConnector(PORT_NUMBER, args[0]);
		// Listening for client connected
		connection.start();
	}

}
