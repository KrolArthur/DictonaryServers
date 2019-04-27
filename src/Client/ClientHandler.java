package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientHandler {
	public InetAddress address = InetAddress.getLocalHost();
	public Socket s1 = null;
	public String line = null;
	public PrintWriter os = null;

	public ClientHandler(String str) throws IOException {
		try {
			s1 = new Socket(address, 4445); // You can use static final constant PORT_NUM
			os = new PrintWriter(s1.getOutputStream());

			System.out.println("Client Address : " + address);
			System.out.println("Connected");

			line = str;

			os.println(line);
			os.flush();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Socket read Error");
		} finally {

			os.close();
			s1.close();
			System.out.println("Connection Closed");

		}
	}
}
