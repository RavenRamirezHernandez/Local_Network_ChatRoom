/**
 * An echo server listening on port 6007.  
 * This server reads from the client
 * and echoes back the result. 
 *
 * This services each request in a separate thread.
 *
 * This conforms to RFC 862 for echo servers.
 *
 * @author - Greg Gagne.
 */

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Vector;
import java.util.concurrent.*;

public class  Server
{
	public static final int DEFAULT_PORT = 5555;

    // construct a thread pool for concurrency	
	private static final Executor exec = Executors.newCachedThreadPool();
	
	public static void main(String[] args) throws IOException {
		ServerSocket sock = null;
		
		try {
			// establish the socket
			sock = new ServerSocket(DEFAULT_PORT);
			Vector<String> messageQueue = new Vector<String>();  // Vector acts like an ArrayList.
			ArrayList<BufferedWriter> allClients = new ArrayList<BufferedWriter>();
			
			Runnable broadcast = new BroadcastThread(allClients, messageQueue);
			exec.execute(broadcast);
			while (true) {
				/**
				 * now listen for connections
				 * and service the connection in a separate thread.
				 */
				Socket client = sock.accept();
				BufferedWriter toClient = new BufferedWriter(new OutputStreamWriter(client.getOutputStream())); // Create OutputStream writer.
				allClients.add(toClient);
				Runnable task = new Connection(client, messageQueue);
				exec.execute(task);
			}
		}
		catch (IOException ioe) { 
			System.err.println(ioe);
			
		}
		
		finally {
			if (sock != null)
				sock.close();
		}
	}
}
