package Dictonaries;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Dictonaries {
	private Map<String, String> engMap = new HashMap<>();
	private Map<String, String> norMap = new HashMap<>();
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	public Dictonaries() {
		this.engMap.put("pies", "dog");
		this.engMap.put("samochod", "car");
		this.engMap.put("lody", "ice cream");
		this.engMap.put("slon", "elephant");
		this.engMap.put("szklo", "glass");
		this.engMap.put("kot", "cat");

		this.norMap.put("kot", "katt");
		this.norMap.put("pies", "hund");
		this.norMap.put("samochod", "bil");
		this.norMap.put("szklo", "glass");
		this.norMap.put("slon", "elefant");
	}

	public void start(int port) throws IOException {
		serverSocket = new ServerSocket(port);
		clientSocket = serverSocket.accept();
		System.out.println("Server is listening");
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		String readL = in.readLine();
		String[] splt = readL.split(" ");
		if (splt[0].equals("ENG")) {
			handleWord(this.engMap, splt);
		} else if (splt[0].equals("NOR")) {
			handleWord(this.norMap,splt);
		} else {
			;
		}
		serverSocket.close();
		clientSocket.close();
	}

	public void handleWord(Map map, String[] splt) throws NumberFormatException, IOException {
		Iterator it = map.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (pair.getKey().equals(splt[1])) {
				Handler h = new Handler(pair.getValue().toString(), Integer.valueOf(splt[2]));
			}
			it.remove(); // avoids a ConcurrentModificationException
		}
		Handler h = new Handler("unrecognised word", Integer.valueOf(splt[2]));
	}

	public void stop() throws IOException {
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}

	public static void main(String[] args) throws IOException {
		Dictonaries server = new Dictonaries();
		server.start(4470);
	}
}
