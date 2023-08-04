/**
 * This thread is passed a socket that it reads from. Whenever it gets input
 * it writes it to the ChatScreen text area using the displayMessage() method.
 */

import java.io.*;
import java.net.*;
import javax.swing.*;

public class ReaderThread implements Runnable
{
	Socket server;
	BufferedReader fromServer;
	ChatScreen screen;
	

	public ReaderThread(Socket server, ChatScreen screen) {
		this.server = server;
		this.screen = screen;
	}

	public void run() {
		try {
			fromServer = new BufferedReader(new InputStreamReader(server.getInputStream()));

			while (true) {
				String message = fromServer.readLine();

				// now display it on the display area
				String[] m = message.split(" ");
				if (m[0].equals("SEND")) {
					String moreWords = "";
					for (int i = 2; i < m.length; i ++) {
					moreWords = moreWords + " " + m[i];
					}
					screen.displayMessage(m[1] + moreWords);  // User name + message
				}
				else if (m[0].equals("JOIN")) {
					screen.displayMessage(m[1] + " has joined");
				}
				else if (m[0].equals("LEAVE")) {
					screen.displayMessage(m[1] + " has left the chat");
				}
			}
		}
		catch (IOException ioe) { System.out.println(ioe); }

	}
}
