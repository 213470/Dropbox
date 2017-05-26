package connection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Queue;

import model.FileEvent;
import utility.Dispatcher;
import utility.Transfer;

public class Downloader extends Transfer{

	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream inputStream = null;
	private FileEvent fileEvent = null;
	private File dstFile = null;
	private FileOutputStream fileOutputStream = null;
	private List<FileEvent> fileEvents = null;
	private Dispatcher dispatcher = null;
	private Queue<FileEvent> queue;

	public Downloader(Dispatcher dispatcher, Queue<FileEvent> queue) {
		this.dispatcher = dispatcher;
		this.queue = queue;
	}

	public void doConnect() {
		try {
			serverSocket = new ServerSocket(incrementPort());
			socket = serverSocket.accept();
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void enqueue() {
		try {
			fileEvents = (List<FileEvent>) inputStream.readObject();
			for (FileEvent fe : fileEvents) {
				queue.add(fe);
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void downloadFiles() {
		try {
			FileEvent fe;
			// fileEvent = inputStream.readObject();
			do {
				fe = queue.poll();
				if (fe != null) {
					if (fe.getStatus().equalsIgnoreCase("Error")) {
						System.out.println("Error occurred ..So exiting");
						System.exit(0);
					}
					String outputFile = dispatcher.getServerPath() + File.separator + fe.getFilename();
					dstFile = new File(outputFile);
					File f = new File(dispatcher.getServerPath() + File.separator);
					if (!f.exists()) {
						f.mkdir();
						System.out.println("Path created.");
					}
					fileOutputStream = new FileOutputStream(dstFile);
					fileOutputStream.write(fe.getFileData());
					fileOutputStream.flush();
					fileOutputStream.close();
					System.out.println("Output file : " + outputFile + " is successfully saved ");
					Thread.sleep(3000);
				}
			} while (fe != null);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			 try {
			 if (socket == null) {
			 serverSocket.close();
			 } else {
			 socket.close();
			 }
			 } catch (IOException e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
			 }
		}
	}
}
