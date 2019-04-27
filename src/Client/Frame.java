package Client;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Frame extends JFrame {
	public static int port = 4445;
	public static int waitingPort = 4446;
	public JTextArea areaInput;
	public JTextArea areaOutput;
	public ServerSocket ss2 = null;

	public Frame() {
		JPanel lay = new JPanel(new FlowLayout());
		this.add(lay);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		JButton confirm = new JButton("Confirm");
		confirm.setSize(10, 20);
		this.areaInput = new JTextArea(10, 20);
		areaInput.setSize(new Dimension(250, 250));
		this.areaOutput = new JTextArea("Output gonna be here");
		areaOutput.setSize(new Dimension(500, 250));
		areaOutput.setEditable(false);
		lay.add(areaOutput);
		lay.add(areaInput);
		lay.add(confirm);

		confirm.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				int port = 0;

					String newLine = new String();
					String[] splt = areaInput.getText().split("\n");
					port = Integer.valueOf(splt[2]);
					for (String s : splt) {
						newLine += s + " ";
					}
					if (!(splt[0].equals("ENG") || splt[0].equals("NOR"))) {
						areaOutput.setText("Fix ID language");
					} else {
						
						try {
							ClientHandler cc = new ClientHandler(newLine);
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					


				System.out.println("Server Listening......");
				try {
					if (ss2 == null)
						ss2 = new ServerSocket(port); // can also use static final PORT_NUM , when defined

				} catch (IOException ee) {
					ee.printStackTrace();
					System.out.println("Server error");

				}

				// while (true) {
				try {
					Socket s = ss2.accept();
					System.out.println("connection Established");
					ClientServerThread st = new ClientServerThread(s, areaOutput);
					st.start();
					System.out.println("ST GET LINE " + st.getLine());
					areaOutput.setText(st.getLine());

				}

				catch (Exception ex) {
					ex.printStackTrace();
					System.out.println("Connection Error");

				}
				// }

			}
			}
		});

		this.setPreferredSize(new Dimension(500, 500));
		this.pack();
		this.setVisible(true);
	}
}
