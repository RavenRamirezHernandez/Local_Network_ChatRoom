import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class BroadcastThread implements Runnable
{
	private ArrayList<BufferedWriter> allClients;
	private Vector<String> messageQueue;
	public BroadcastThread(ArrayList<BufferedWriter> allClients, Vector<String> messageQueue) {
		//instance data
		this.allClients = allClients;
		this.messageQueue = messageQueue;
	}
    public void run() {
    	String message;
        while (true) {
            // sleep for 1/10th of a second
            try { Thread.sleep(100); } catch (InterruptedException ignore) { }
            /**
             * check if there are any messages in the Vector. If so, remove them
             * and broadcast the messages to the chat room.
             */
            while (messageQueue.isEmpty() == false) {  
            	// For first message you receive...
            	message = messageQueue.get(0);
            	//Then delete from message after read, so we know when new messages arrive.
            	messageQueue.remove(0);
            	// for each client
            	for (BufferedWriter client : allClients) {  // For each loop.
            		// write the message to the clients
            		try {
						client.write(message + "\n");
	                	//client.flush 
	            		client.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
            	}
        	}         
        }
    }
} 
