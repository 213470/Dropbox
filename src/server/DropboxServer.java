package server;

import connection.ConnectionListener;

public class DropboxServer {
	
	private static int PORT_NUMBER = 6066;

	public static void main(String[] args) {
	
		ConnectionListener connection = new ConnectionListener(PORT_NUMBER);
		// Listening for client connected
		connection.start();
		
		
	}

}
