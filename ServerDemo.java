package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerDemo {
	int port;
	ServerSocket server = null;
	Socket client = null;
	ExecutorService pool = null;
	int connCount = 0;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerDemo serverObject = new ServerDemo(7800);
		serverObject.startServer();
	}

	public ServerDemo(int port) { // constructor
		this.port = port;
		pool = Executors.newFixedThreadPool(5);
	}

	public void startServer() {
		try {
			// creating one server socket
			server = new ServerSocket(5000); //ServerSocket class attempts to create a server socket bound to the specified port. An exception occurs if the port is already bound by another application. Usage = (port_no))


			System.out.println("Server booted.");
			System.out.println("Any client can send -1 to stop the server.");
			// for accepting multiple clients
			while (true) {
				client = server.accept(); //Accept function waits for an incoming client. This method blocks until either a client connects to the server on the specified port or the socket times out, assuming that the time-out value has been set using the setSoTimeout() method. Otherwise, this method blocks indefinitely.


				connCount++;
				// ServerThreadBody conn=new ServerThreadBody(client,connCount,this);
				// System.out.println("\nTotal Number of Clients: " + connCount + " \nCreating
				// thread for this client...");
				ServerThreadBody runnable = new ServerThreadBody(client, connCount, this);
				// assigning thread to pool
				pool.execute(runnable);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

class ServerThreadBody implements Runnable {

	ServerDemo server = null;
	Socket client = null;
	DataOutputStream dout = null;
	DataInputStream din = null;
	ObjectOutputStream oout = null;
	ObjectInputStream oin = null;
	int id;

	ServerThreadBody(Socket client, int id, ServerDemo server) {
		// TODO Auto-generated constructor stub
		this.client = client;
		this.id = id;
		this.server = server;
		System.out.println("Connection " + id + " established with: " + client);
		try {
			dout = new DataOutputStream(client.getOutputStream());
			din = new DataInputStream(client.getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void run() {
		int x = 0;

		try {

			// oout = new ObjectOutputStream(client.getOutputStream());
			// oin = new ObjectInputStream(client.getInputStream());

			while (true) {
				x = din.readInt();
				System.out.println("Received '" + x + "' from Connection " + id + ".");

				if (x == -1) {
					System.out.println("Exiting..");
					//dout.writeInt(-1);
					break;
				}
				if (x == 0)
					break;

				dout.writeInt(x * x);
			}
			System.out.println( "Connection " + id + " closed." );
			dout.close();
			din.close();
			client.close();
			
			if(x==-1) {
			System.out.println( "Server cleaning up." );
			System.exit(0);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
