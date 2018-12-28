package udp;

import java.io.*;
import java.net.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//import tcp.ServerDemo;

//import tcp.ServerDemo;

//import tcp.ServerThreadBody;

//import tcp.ServerDemo;

/*
class UDPServer {
	int port;
	
	DatagramSocket serverSocket  = null;
	ExecutorService pool = null;
	int connCount = 0;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		UDPServer serverObject = new UDPServer(5000);
		serverObject.startServer();
	}
	
	public UDPServer(int port) { // constructor
		this.port = port;
		pool = Executors.newFixedThreadPool(5);
	}

	private void startServer() {
		// TODO Auto-generated method stub
		try {
			 serverSocket = new DatagramSocket(5000); //Java DatagramSocket class represents a connection-less socket for sending and receiving datagram packets. Usage - (Port_No)


			System.out.println("Server booted.");
			System.out.println("Any client can send -1 to stop the server.");
			
			//while (true)
			{
				
				connCount++;
				// ServerThreadBody conn=new ServerThreadBody(cs,connCount,this);
				// System.out.println("\nTotal Number of Clients: " + connCount + " \nCreating
				// thread for this cs...");
				ServerThreadBody runnable = new ServerThreadBody(serverSocket, connCount, this);
				// assigning thread to pool
				pool.execute(runnable);
			}			
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
class ServerThreadBody implements Runnable {
	
	UDPServer server = null;
	DatagramSocket serverSocket  = null;
	//DataOutputStream dout = null;
	//DataInputStream din = null;
	//ObjectOutputStream oout = null;
	//ObjectInputStream oin = null;
	BufferedReader inFromUser=null;
	int id;
	
	byte[] receiveData = new byte[1024];
	byte[] sendData = new byte[1024];
	byte[] buf = new byte[1024];
	
	
	public ServerThreadBody(DatagramSocket serverSocket , int connCount, UDPServer udpServer) {
		// TODO Auto-generated constructor stub
		this.serverSocket = serverSocket;
		this.id = id;
		this.server = server;
		inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("\nConnection " + id + " established with: " + serverSocket);
		System.out.println("Server is Ready.");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			receivePacket.setData(buf);
			try {
				serverSocket.receive(receivePacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sentence = new String(receivePacket.getData());
			System.out.println("\nRECEIVED: " + sentence);

			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
 
			System.out.println("\nREPLY :");
			String reply = null;
			try {
				reply = inFromUser.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sendData = reply.getBytes();
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			try {
				serverSocket.send(sendPacket);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Send Capitalized data back to client
		}
	}
}*/
class UDPServer {
	
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(5000);
		
		// Server Socket Created
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Server is Ready.");

		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		byte[] buf = new byte[1024];
		
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			receivePacket.setData(buf);
			serverSocket.receive(receivePacket);
			String sentence = new String(receivePacket.getData());
			System.out.println("\nRECEIVED: " + sentence);

			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
 
			System.out.println("\nREPLY :");
			String reply=inFromUser.readLine();
			sendData = reply.getBytes();
			
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
			// Send Capitalized data back to client
		}
		
	
}
}