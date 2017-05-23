package client;

import java.io.File;

import javax.swing.JOptionPane;

import connection.ClientConnector;
import connection.FileSender;
import utility.Crawler;

public class User {

	public static void main(String[] args) {
		String username = JOptionPane.showInputDialog("Enter your username: ");

		ClientConnector connector = new ClientConnector(username);
		
		Crawler crawler = new Crawler(args[0]);
		crawler.mapDirectoriesToList();
		crawler.checkForDifference();
		
		FileSender fileSender = new FileSender(connector.getSocket(), crawler.getDifferenceList(), args[0]);
		fileSender.sendFileList();
		System.out.println("FileList sent.");
		for(File f : crawler.getDifferenceList()) {
			fileSender.sendFile(f);
		}
		
	}

}
