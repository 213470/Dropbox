package client;

import javax.swing.JOptionPane;

import connection.ClientConnector;
import connection.Uploader;
import utility.Crawler;

public class User {

	public static void main(String[] args) {
		String username = JOptionPane.showInputDialog("Enter your username: ");

		ClientConnector connector = new ClientConnector(username);
		
		Crawler crawler = new Crawler(args[0]);
		crawler.mapDirectoriesToList();
		
		crawler.checkForDifference();
		
		Uploader ul = new Uploader(crawler.getDifferenceList().get(0).getPath(), "C:\\Users\\EMATGRZ\\Desktop\\Folder\\Server\\server1");
		
		ul.connect();
		ul.sendFile();
		
		
//		System.out.println("==");
//		FileSender fileSender = new FileSender(connector.getSocket(), crawler.getDifferenceList(), args[0]);
//		System.out.println("==");
//		fileSender.sendFileList();
//		System.out.println("FileList sent.");
//		for(File f : crawler.getDifferenceList()) {
//			fileSender.sendFileSize(f);
//			fileSender.sendFile(f);
//		}
		
	}

}
