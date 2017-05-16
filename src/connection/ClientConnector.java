package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientConnector {
	
	public static Socket getInstance(String username) {
		Socket socket;
		try {
			socket = new Socket("localhost", 6066);

			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.println(username);

			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.print("You have connected to server at: ");
			String answer = input.readLine();

			System.out.println(answer);
			
			return socket;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
