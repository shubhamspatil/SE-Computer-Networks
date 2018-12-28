

import java.lang.System;
import java.net.*;
import java.io.*;

public class SelectiveRepeatClient
{
	static Socket connection;
	public static void main(String a[]) throws SocketException
	{
		try
		{
			int v[] = new int[8];
			//int g[] = new int[8];
			int n = 0;
			InetAddress addr = InetAddress.getByName("Localhost");
			System.out.println(addr);
			connection = new Socket(addr, 8011);
			DataOutputStream out = new DataOutputStream(connection.getOutputStream());
			DataInputStream in = new DataInputStream(connection.getInputStream());
			int p = in.read();
			System.out.println("No of frame is:" + p);
			for (int i = 0; i < p; i++)
			{
				v[i] = in.read();
				System.out.println(v[i]);
				//g[i] = v[i];
			}
//v[1] = -1;
			v[5] = -1;
//v[6] = -1;
//v[7] = -1;
			for (int i = 0; i < p; i++)
			{
				System.out.println("Received frame is: " + v[i]);
			}
			
//int temp = 99;
//out.write((byte)temp);
			for (int i = 0; i < p; i++)
			{
//System.out.println("Sending.....");
				if (v[i] == -1)
				{
					System.out.println("Request to retransmit from packet no "+ (i+1) + " again!!");
					n = i;
					out.write(n);
//out.writeByte(n);
					//out.flush();
System.out.println();
v[n] = in.read();
System.out.println("Received frame is: " + v[n]);
			
				}
				/*System.out.println();
				v[n] = in.read();
				System.out.println("Received frame is: " + v[n]);
				System.out.println("quiting");*/
			}
System.out.println("quiting");
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
}

