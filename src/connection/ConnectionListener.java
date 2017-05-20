package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ConnectionListener extends Thread {

	private int port;
	private Map<String, Socket> establishedSockets;

	public ConnectionListener(int port) {
		this.port = port;
		this.establishedSockets = new HashMap<>();
	}

	@Override
	public void run() {
		ServerSocket listener;
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("kk:mm:ss dd-MM-yyyy");
		try {
			listener = new ServerSocket(port);
			System.out.println("Server stared!\nListeninig on port: " + port);
			try {
				while (true) {
					Socket socket = listener.accept();

					LocalDateTime userLoginTime = LocalDateTime.now();

					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					String username = input.readLine();
					System.out.println("LOGGED: " + username);
					establishedSockets.put(username, socket);
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println(userLoginTime.format(dateFormat));

				}
			} finally {
				for(Socket s : establishedSockets.values()) {
					s.close();
				}
				listener.close();
			}
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		}
	}

	public int getPort() {
		return port;
	}

	public Map<String, Socket> getEstablishedSockets() {
		return establishedSockets;
	}
	
	public Socket getUserSocket(String username) {
		return establishedSockets.get(username);
	}

}
