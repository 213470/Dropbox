package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import model.FileEvent;

public class Server extends Thread {

	private int port;
	private Socket socket;
	private String serverPath;
	private Queue<FileEvent> queue;

	public Server(int port, String serverPath) {
		this.port = port;
		this.serverPath = serverPath;
		this.socket = null;
		this.queue = Collections.asLifoQueue(new LinkedList<FileEvent>());
	}

	@Override
	public void run() {
		ServerSocket listener;

		try {
			listener = new ServerSocket(port);
			System.out.println("Server stared!\nListeninig on port: " + port);
			try {
				while (true) {
					socket = listener.accept();

					Handshake hs = new Handshake(socket, serverPath, queue);
					hs.start();

				}
			} finally {
				socket.close();
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

}
