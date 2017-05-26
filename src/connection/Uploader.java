package connection;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import model.FileEvent;
import utility.Transfer;

public class Uploader extends Transfer{
	private Socket socket = null;
	private ObjectOutputStream outputStream = null;
	private boolean isConnected = false;
	private FileEvent fileEvent = null;
	private List<File> transferFiles = new LinkedList<File>();
	private List<FileEvent> fileEvents = new LinkedList<FileEvent>();

	public Uploader(List<File> transferFiles) {
		this.transferFiles = transferFiles;
	}

	public void connect() {
		while (!isConnected) {
			try {
				socket = new Socket("localHost", incrementPort());
				System.out.println("Established socket connection on: " + getPORT());
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
	public void sendFiles() {
		try {
			for (File f : transferFiles) {
				fileEvent = new FileEvent();
				String fileName = f.getPath().substring(f.getPath().lastIndexOf(File.separator) + 1, f.getPath().length());
				String path = f.getPath().substring(0, f.getPath().lastIndexOf(File.separator) + 1);
//				fileEvent.setDestinationDirectory(destination);
				fileEvent.setFilename(fileName);
				fileEvent.setSourceDirectory(f.getPath());
				File file = new File(f.getPath());
				if (file.isFile()) {
					try {
						Thread.sleep(1000);
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
				fileEvents.add(fileEvent);
			}
			// Now writing the FileEvent object to socket
			try {
				outputStream.writeObject(fileEvents);
				System.out.println("Sending files: ");
				System.out.println();

				Thread.sleep(3000);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			// try {
			// socket.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
		}

	}

}
