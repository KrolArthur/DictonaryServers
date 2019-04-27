package Client;

	import java.io.BufferedReader;
	import java.io.IOException;
	import java.io.InputStreamReader;
	import java.io.PrintWriter;
	import java.net.Socket;

import javax.swing.JTextArea;

	public class ClientServerThread extends Thread {

		public String line = null;
		public BufferedReader is = null;
		public PrintWriter os = null;
		public Socket s = null;
		public JTextArea area;

		public ClientServerThread(Socket s, JTextArea area) {
			this.s = s;
			this.area = area;
		}

		public void run() {
			try {
				is = new BufferedReader(new InputStreamReader(s.getInputStream()));
				this.line = is.readLine();
				System.out.println("Savinig to \"line\"" + this.line);
				area.setText(this.line);
				
			} catch (IOException e) {

				line = this.getName();
				System.out.println("IO Error/ Client " + line + " terminated abruptly");
			} catch (NullPointerException e) {
				line = this.getName(); 
				System.out.println("Client " + line + " Closed");
			}

			finally {
				try {
					System.out.println("Connection Closing..");
					if (is != null) {
						is.close();
						System.out.println(" Socket Input Stream Closed");
					}

					if (os != null) {
						os.close();
						System.out.println("Socket Out Closed");
					}
					if (s != null) {
						s.close();
						System.out.println("Socket Closed");
					}

				} catch (IOException ie) {
					System.out.println("Socket Close Error");
				}
			} // end finally
		}
		public String getLine() {
			return this.line;
		}
	}
