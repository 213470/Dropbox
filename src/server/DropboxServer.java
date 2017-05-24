package server;

import connection.ServerConnector;
import utility.Dispatcher;

public class DropboxServer {
	
	private static int PORT_NUMBER = 6066;

	public static void main(String[] args) {
		
		new Instanciation(args[0]);
		
		ServerConnector connection = new ServerConnector(PORT_NUMBER, new Dispatcher(args[0]));
		// Listening for client connected
		connection.start();
	}

}
