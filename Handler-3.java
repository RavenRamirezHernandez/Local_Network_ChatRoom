/**
 * Handler class containing the logic for echoing results back
 * to the client. 
 *
 * This conforms to RFC 862 for echo servers.
 *
 * @author Greg Gagne 
 */

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Vector;

import com.sun.jdi.connect.spi.Connection;

public class Handler 
{
	public static final int BUFFER_SIZE = 256;


	public void process(Socket client, Vector<String> messageQueue) throws java.io.IOException {
		byte[] buffer = new byte[BUFFER_SIZE];
		BufferedReader fromClient = null;
		DataOutputStream toClient = null;
		
		try {
			//Create BufferedReaded for reading from client.
			fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
			String line;
			
			//While you are able to read from client.
			while((line = fromClient.readLine()) != null) { 
				System.out.println("Just Read: " + line);
				messageQueue.add(line);
			}
		}
		catch (UnknownHostException u){
			System.out.print(u);
		}
		finally {
			// close streams and socket
			if (fromClient != null)
				fromClient.close();
			if (toClient != null)
				toClient.close();
		}
	}
}
