package EngDictonary;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import Dictonaries.Handler;

class ServThreadsEng extends Thread {
	
	private Map<String, String> engMap = new HashMap<>();

	String line = null;
	BufferedReader is = null;
	PrintWriter os = null;
	Socket s = null;

	public ServThreadsEng(Socket s) {
		this.s = s;
		this.engMap.put("pies", "dog");
		this.engMap.put("samochod", "car");
		this.engMap.put("lody", "ice cream");
		this.engMap.put("slon", "elephant");
		this.engMap.put("szklo", "glass");
		this.engMap.put("kot", "cat");
	}

	public void run() {
		try {
			is = new BufferedReader(new InputStreamReader(s.getInputStream()));
			os = new PrintWriter(s.getOutputStream());

		} catch (IOException e) {
			System.out.println("IO error in server thread");
		}

		try {
			line = is.readLine();
			System.out.println("LINE =  " +line);
			String[] splt = line.split(" ");
			Iterator it = engMap.entrySet().iterator();
			boolean flag = false;
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				if (pair.getKey().equals(splt[1])) {
					Handler h = new Handler(pair.getValue().toString(), Integer.valueOf(splt[2]));
					flag = true;
				}
				it.remove(); // avoids a ConcurrentModificationException
			}
			if(flag == false) {
				Handler h = new Handler("unrecognized word", Integer.valueOf(splt[2]));
			}
			
			//os.println(line);
			os.flush();
			System.out.println("Response to Client  :  " + line);

			//Handler dh = new Handler(line, Integer.valueOf(splt[2]));
			
			
		} catch (IOException e) {

			line = this.getName(); // reused String line for getting thread name
			System.out.println("IO Error/ Client " + line + " terminated abruptly");
		} catch (NullPointerException e) {
			line = this.getName(); // reused String line for getting thread name
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
}
