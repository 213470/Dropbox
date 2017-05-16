package client;

import javax.swing.JOptionPane;

import connection.ClientConnector;
import utility.HomeCrawler;

public class User {

	public static void main(String[] args) {
		String username = JOptionPane.showInputDialog("Enter your username for loging in: ");

		ClientConnector.getInstance(username);
		HomeCrawler crawler = new HomeCrawler(args[1]);

	}

}
