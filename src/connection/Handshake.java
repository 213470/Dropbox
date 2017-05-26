package connection;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Queue;

import utility.Dispatcher;

public class Handshake extends Thread {

	private Socket socket;
	private String serverPath;
	private Queue queue;

	public Handshake(Socket socket, String serverPath, Queue queue) {
		this.socket = socket;
		this.serverPath = serverPath;
		this.queue = queue;
	}

	public void run() {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("kk:mm:ss dd-MM-yyyy");
		LocalDateTime userLoginTime = LocalDateTime.now();
		BufferedReader input;
		try {
			String username;
			input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			username = input.readLine();
			System.out.println("LOGGED: " + username);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(userLoginTime.format(dateFormat));
			Dispatcher dispatcher = new Dispatcher(serverPath);

			dispatcher.setServerPath(dispatcher.getServerPath() + File.separator + username);

			Downloader dl = new Downloader(dispatcher, queue);
			dl.doConnect();
			dl.enqueue();
			dl.downloadFiles();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
