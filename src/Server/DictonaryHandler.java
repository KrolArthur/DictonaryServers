package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class DictonaryHandler {
	public InetAddress address = InetAddress.getLocalHost();
	public Socket s1 = null;
	public String line = null;
	public BufferedReader br = null;
	public BufferedReader is = null;
	public PrintWriter os = null;

	public DictonaryHandler(String line) throws IOException {
		try {
			String[] splt = line.split(" ");
			if(splt[0].equals("ENG"))
				s1 = new Socket(address, 4470);
			else if(splt[0].equals("NOR"))
				s1 = new Socket(address, 4471);
			else
				s1 = new Socket(address, 4444);
			br = new BufferedReader(new InputStreamReader(System.in));
			is = new BufferedReader(new InputStreamReader(s1.getInputStream()));
			os = new PrintWriter(s1.getOutputStream());
			System.out.println("Client Address : " + address);

			this.line = line;

			os.println(line);
			os.flush();

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Socket read Error");
		} finally {

			is.close();
			os.close();
			br.close();
			s1.close();
			System.out.println("Connection Closed");

		}
	}
}
