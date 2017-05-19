package client;

import javax.swing.JOptionPane;

import connection.ClientConnector;
import utility.Crawler;

public class User {

	public static void main(String[] args) {
		String username = JOptionPane.showInputDialog("Enter your username for loging in: ");

		ClientConnector connector = new ClientConnector(username);
		
		Crawler crawler = new Crawler(args[1]);
		

	}

}
