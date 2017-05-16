package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JOptionPane;

public class User {

	public static void main(String[] args) {
		String username = JOptionPane
				.showInputDialog("Enter your username for loging in: ");
		Socket s;
		try {
			s = new Socket("localhost", 6066);
			
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			out.println(username);
				
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
			System.out.print("You have connected to server at: ");
			String answer = input.readLine();
			
			System.out.println(answer);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
