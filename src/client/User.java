package client;

import java.io.File;

import javax.swing.JOptionPane;

import connection.ClientConnector;
import connection.Uploader;
import utility.Crawler;

public class User {

	public static void main(String[] args) {
		String username = JOptionPane.showInputDialog("Enter your username: ");

		ClientConnector connector = new ClientConnector(username);

		Crawler crawler = new Crawler(args[0] + File.separator + username);
		crawler.mapDirectoriesToList();

		crawler.checkForDifference();

		Uploader ul = new Uploader(crawler.getDifferenceList());

		ul.connect();
		ul.sendFile();
		
		crawler.prepareJSON(username);

		// System.out.println("==");
		// FileSender fileSender = new FileSender(connector.getSocket(),
		// crawler.getDifferenceList(), args[0]);
		// System.out.println("==");
		// fileSender.sendFileList();
		// System.out.println("FileList sent.");
		// for(File f : crawler.getDifferenceList()) {
		// fileSender.sendFileSize(f);
		// fileSender.sendFile(f);
		// }

	}

}
