package connection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

import model.FileEvent;

public class Downloader {
	
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	private ObjectInputStream inputStream = null;
	private FileEvent fileEvent;
	private File dstFile = null;
	private FileOutputStream fileOutputStream = null;
	
	public Downloader(ServerSocket serverSocket){
		this.serverSocket = serverSocket;
	}

	public void doConnect() {
		try {
			serverSocket = new ServerSocket(6067);
			socket = serverSocket.accept();
			inputStream = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadFile() {
		try {
			fileEvent = (FileEvent) inputStream.readObject();
			if (fileEvent.getStatus().equalsIgnoreCase("Error")) {
				System.out.println("Error occurred ..So exiting");
				System.exit(0);
			}
			String outputFile = fileEvent.getDestinationDirectory() + File.separator + fileEvent.getFilename();
			if (!new File(fileEvent.getDestinationDirectory()).exists()) {
				new File(fileEvent.getDestinationDirectory()).mkdirs();
			}
			dstFile = new File(outputFile);
			System.out.println(dstFile);
			fileOutputStream = new FileOutputStream(dstFile);
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
		}finally{
//			try {
////				socket.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
}
