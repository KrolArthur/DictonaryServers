package Dictonaries;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Handler {
	private Map<String, String> engMap = new HashMap<>();
	public InetAddress address = InetAddress.getLocalHost();
	public Socket s1 = null;
	public String line = null;
	public BufferedReader br = null;
	public BufferedReader is = null;
	public PrintWriter os = null;

	public Handler(String line,int port) throws IOException {
		this.engMap.put("pies", "dog");
		this.engMap.put("samochod", "car");
		this.engMap.put("lody", "ice cream");
		this.engMap.put("slon", "elephant");
		this.engMap.put("szklo", "glass");
		this.engMap.put("kot", "cat");
		try {
			s1 = new Socket(address, port); // You can use static final constant PORT_NUM
			br = new BufferedReader(new InputStreamReader(System.in));
			is = new BufferedReader(new InputStreamReader(s1.getInputStream()));
			os = new PrintWriter(s1.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
			System.err.print("IO Exception");
		}

		System.out.println("Client Address : " + address);

		try {
			this.line = line;
			os.println(line);
			os.flush();

		} finally {

			is.close();
			os.close();
			br.close();
			s1.close();
			System.out.println("Connection Closed");

		}
	}
}
