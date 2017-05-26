package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread {

	private int port;
	private Socket socket;
	private String serverPath;

	public Server(int port, String serverPath) {
		this.port = port;
		this.serverPath = serverPath;
		this.socket = null;
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

					Handshake hs = new Handshake(socket, serverPath);
					hs.start();

					// BufferedReader input = new BufferedReader(new
					// InputStreamReader(socket.getInputStream()));
					// String username = input.readLine();
					// System.out.println("LOGGED: " + username);
					// PrintWriter out = new
					// PrintWriter(socket.getOutputStream(), true);
					// out.println(userLoginTime.format(dateFormat));

					// System.out.println("==");
					// FileSender fs = new FileSender(socket, pathToFiles);
					// System.out.println("==");
					// List<File> filesToDownload = fs.receiveFileList();
					// System.out.println(filesToDownload);
					//
					// for (File fileToDownload : filesToDownload) {
					// long fileSize = fs.receiveFileSize(fileToDownload);
					// fs.fileReceive(fileToDownload);
					// }
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
