package connection;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

import model.FileEvent;

public class FileSender {

	public final static int FILE_SIZE = 6022386;

	private Socket socket;
	private List<File> filesToSend;
	private String pathWhereToCopyFiles;
	private ObjectInputStream inputStream;
	private ObjectOutputStream outputStream;

	public FileSender(Socket socket, String pathWhereToCopyFiles) {
		this.socket = socket;
		this.filesToSend = new LinkedList<>();
		this.pathWhereToCopyFiles = pathWhereToCopyFiles;
	}

	public FileSender(Socket socket, List<File> filesToSend, String pathWhereToCopyFiles) {
		this.socket = socket;
		this.filesToSend = filesToSend;
		this.pathWhereToCopyFiles = pathWhereToCopyFiles;
	}

	public void sendFileList() {
		try {
			outputStream = new ObjectOutputStream(socket.getOutputStream());
			outputStream.writeObject(filesToSend);
			System.out.println("Files: " + filesToSend + "\nhas been sent to server.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<File> receiveFileList() {
		List<File> fileListToDownload = new LinkedList<File>();
		try {
			inputStream = new ObjectInputStream(socket.getInputStream());
			fileListToDownload = (LinkedList<File>) inputStream.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileListToDownload;

	}

	public void sendFile(File fileToSend) {
		
		FileEvent fileEvent = new FileEvent();
		String fileName = fileToSend.getPath().substring(fileToSend.getPath().lastIndexOf(File.separator) + 1,
				fileToSend.getPath().length());
		String path = fileToSend.getPath().substring(0, fileToSend.getPath().lastIndexOf(File.separator) + 1);
		fileEvent.setDestinationDirectory("C:\\Users\\EMATGRZ\\Desktop\\Folder\\Server\\server1");
		fileEvent.setFilename(fileName);
		fileEvent.setSourceDirectory(fileToSend.getPath());
		File file = new File(fileToSend.getPath());
		System.out.println(fileEvent);
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
		System.out.println(fileEvent);
		// Now writing the FileEvent object to socket
		try {
//			ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
//			outputStream = new ObjectOutputStream(socket.getOutputStream());
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

	public void fileReceive(File receivedFile) throws IOException {
		try {
			FileOutputStream fileOutputStream;
//			ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
			FileEvent fileEvent = (FileEvent) inputStream.readObject();
			if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
				System.out.println("Error occurred ..So exiting");
				System.exit(0);
			}
			String outputFile = fileEvent.getDestinationDirectory() + File.separator + fileEvent.getFilename();
			if (!new File(fileEvent.getDestinationDirectory()).exists()) {
				new File(fileEvent.getDestinationDirectory()).mkdirs();
			}
			receivedFile = new File(outputFile);
			fileOutputStream = new FileOutputStream(receivedFile);
			fileOutputStream.write(fileEvent.getFileData());
			fileOutputStream.flush();
			fileOutputStream.close();
			System.out.println("Output file : " + outputFile + " is successfully saved ");
			Thread.sleep(3000);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendFileSize(File file) {
		try {
			PrintWriter pw = new PrintWriter(socket.getOutputStream());
			pw.println(file.length());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long receiveFileSize(File file) {
		long fileSize = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			fileSize = br.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileSize;
	}

}
