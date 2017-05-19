package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConnectionListener extends Thread {

	private int port;
	private List<Socket> establishedSockets;

	public ConnectionListener(int port) {
		this.port = port;
		this.establishedSockets = new ArrayList<>();
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
					establishedSockets.add(socket);
					LocalDateTime userLoginTime = LocalDateTime.now();

					try {
						BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
						String username = input.readLine();
						System.out.println("LOGGED: " + username);

						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						out.println(userLoginTime.format(dateFormat));
					} finally {
						socket.close();
					}
				}
			} finally {
				listener.close();
			}
		} catch (IOException e) {
			System.out.println();
			e.printStackTrace();
		}
	}

}
