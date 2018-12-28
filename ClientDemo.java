package tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientDemo {

	private static Scanner sc;
	private static Socket cs;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		try {
			cs = new Socket("localhost", 7800); //Socket class implements client sockets (also called just "sockets"). Usage (hostname,portname)

			DataOutputStream dout = new DataOutputStream(cs.getOutputStream());
			DataInputStream din = new DataInputStream(cs.getInputStream());

			
			int x,ans;
			System.out.println("Enter -1 to stop server.");
			System.out.println("Enter 0 to stop connection.");
			while (true) {
				
				System.out.println("\n\nEnter an integer: ");
				x=sc.nextInt();
				dout.writeInt(x);
				
				
				if (x == -1||x==0) {
					System.out.println("Exiting..");
					break;
				}
				
				ans=din.readInt();
				System.out.println("Received (("+x+")^2): "+ans);

			}
			dout.close();
			din.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
