package connection;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

import model.FileEvent;

public class Uploader {
	private Socket socket = null;
	private ObjectOutputStream outputStream = null;
	private boolean isConnected = false;
	private String source;
	private FileEvent fileEvent = null;
	private String destination;
	
	public Uploader(String source, String destination){
		this.source = source;
		this.destination = destination;		
	}
	
	public void connect() {
		while (!isConnected) {
			try {
				socket = new Socket("localHost", 6067);
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
		String fileName = source.substring(source.lastIndexOf("\\") + 1, source.length());
		String path = source.substring(0, source.lastIndexOf("\\") + 1);
		fileEvent.setDestinationDirectory(destination);
		fileEvent.setFilename(fileName);
		fileEvent.setSourceDirectory(source);
		File file = new File(source);
		if (file.isFile()) {
			try {
				Thread.sleep(5000);
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
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
//			try {
//				socket.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}

	}

}
