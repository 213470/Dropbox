import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.FileEvent;

public class Client {
	private Socket socket = null;
	private ObjectOutputStream outputStream = null;
	private boolean isConnected = false;
	private String sourceFilePath = "C:\\Users\\EMATGRZ\\Desktop\\Folder\\Matts\\different.txt";
	private FileEvent fileEvent = null;
	private String destinationPath = "C:\\Users\\EMATGRZ\\Desktop\\Folder\\Server\\server1";

	public Client() {

	}

	/**
	 * Connect with server code running in local host or in any other host
	 */
	public void connect() {
		while (!isConnected) {
			try {
				socket = new Socket("localHost", 4445);
				outputStream = new ObjectOutputStream(socket.getOutputStream());
				isConnected = true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sending FileEvent object.
	 */
	public void sendFile() {
		fileEvent = new FileEvent();
		String fileName = sourceFilePath.substring(sourceFilePath.lastIndexOf("\\") + 1, sourceFilePath.length());
		String path = sourceFilePath.substring(0, sourceFilePath.lastIndexOf("\\") + 1);
		fileEvent.setDestinationDirectory(destinationPath);
		fileEvent.setFilename(fileName);
		fileEvent.setSourceDirectory(sourceFilePath);
		File file = new File(sourceFilePath);
		if (file.isFile()) {
			try {
				DataInputStream diStream = new DataInputStream(new FileInputStream(file));
				long len = (int) file.length();
				byte[] fileBytes = new byte[(int) len];
				int read = 0;
				int numRead = 0;
				while (read < fileBytes.length
						&& (numRead = diStream.read(fileBytes, read, fileBytes.length - read)) >= 0) {
					read = read + numRead;
				}
				fileEvent.setFileSize(len);
				fileEvent.setFileData(fileBytes);
				fileEvent.setStatus("Success");
			} catch (Exception e) {
				e.printStackTrace();
				fileEvent.setStatus("Error");
			}
		} else {
			System.out.println("path specified is not pointing to a file");
			fileEvent.setStatus("Error");
		}
		// Now writing the FileEvent object to socket
		try {
			outputStream.writeObject(fileEvent);
			System.out.println("Done...Going to exit");
			Thread.sleep(3000);
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		Client client = new Client();
		client.connect();
		client.sendFile();
	}
}