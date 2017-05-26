package client;

import java.io.File;

import javax.swing.JOptionPane;

import connection.ClientConnector;
import model.UserInfo;
import utility.Crawler;
import utility.Synchronizer;

public class User {

	public static void main(String[] args) {
		String username = JOptionPane.showInputDialog("Enter your username: ");

		UserInfo user = new UserInfo(username, args[0]);

		ClientConnector connector = new ClientConnector(username);
		
		Crawler crawler = new Crawler(args[0] + File.separator + username);

		Synchronizer sync = new Synchronizer(crawler, user);
		sync.start();
//
//		while (true) {
//			sync.getCrawler().getDifferenceList();
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}

		
	}

}
