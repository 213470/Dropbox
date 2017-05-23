package connection;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class FileSender {

	public final static int FILE_SIZE = 6022386;

	private Socket socket;
	private List<File> filesToSend;
	private String pathWhereToCopyFiles;

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
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.writeObject(filesToSend);
			System.out.println("Files: " + filesToSend + "\nhas been sent to server.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<File> receiveFileList() {
		List<File> fileListToDownload = new LinkedList<File>();
		try {
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			os.flush();
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			fileListToDownload = (LinkedList<File>) is.readObject();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return fileListToDownload;

	}

	public void sendFile(File fileToSend) {
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		OutputStream os = null;
		File myFile = fileToSend;
		byte[] mybytearray = new byte[(int) myFile.length()];
		try {
			fis = new FileInputStream(myFile);
			bis = new BufferedInputStream(fis);
			bis.read(mybytearray, 0, mybytearray.length);
			os = socket.getOutputStream();
			System.out.println("Sending " + fileToSend.getPath() + "(" + mybytearray.length + " bytes)");
			os.write(mybytearray, 0, mybytearray.length);
			os.flush();
		} catch (FileNotFoundException e) {
			System.out.println("File not found exception.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IO Exception");
			e.printStackTrace();
		}

		System.out.println("Done.");
	}

	public void fileReceive(File receivedFile) throws IOException {
		int bytesRead;
		int current = 0;
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;

		try {
			System.out.println("Connecting...");

			// receive file
			byte[] mybytearray = new byte[0];
			InputStream is;

			is = socket.getInputStream();

			fos = new FileOutputStream(receivedFile);
			bos = new BufferedOutputStream(fos);
			bytesRead = is.read(mybytearray, 0, mybytearray.length);
			current = bytesRead;
			System.out.println("Receiving file: " + receivedFile);
			do {
				bytesRead = is.read(mybytearray, current, (mybytearray.length - current));
				if (bytesRead >= 0)
					current += bytesRead;
			} while (bytesRead > -1);

			bos.write(mybytearray, 0, current);
			bos.flush();
			System.out.println("File " + receivedFile + " downloaded (" + current + " bytes read)");
		} finally {
			if (fos != null)
				fos.close();
			if (bos != null)
				bos.close();
		}
	}

}
