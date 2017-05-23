package connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ServerConnector extends Thread {

	private int port;
	private String pathToFiles;

	public ServerConnector(int port, String pathToFiles) {
		this.port = port;
		this.pathToFiles = pathToFiles;
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
					PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
					out.println(userLoginTime.format(dateFormat));

					FileSender fs = new FileSender(socket, pathToFiles);

					List<File> filesToDownload = fs.receiveFileList();
					System.out.println(filesToDownload);

					for (File fileToDownload : filesToDownload) {
						fs.fileReceive(fileToDownload);
					}
					socket.close();
				}
			} finally {
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
