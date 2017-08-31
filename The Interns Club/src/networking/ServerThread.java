package networking;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread {
	private Socket s;
	private BufferedReader br;
	private PrintWriter pw;
	private Server cs;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private int id;
	private String username;

	public ServerThread(Socket s, Server cs, int num) {
		this.cs = cs;
		this.id = num;
		this.s = s;
		try {
			oos = new ObjectOutputStream(s.getOutputStream());
			ois = new ObjectInputStream(s.getInputStream());

			this.start();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	public void sendMessage(Message cm) {
		try {
			if (cm.getType().equals("login") || cm.getType().equals("create")) {
				this.username = cm.getUsername();
			}
			cm.serverID = id;
			oos.writeObject(cm);
			oos.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getID() {
		return id;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Message cm = (Message) ois.readObject();
				cm.serverID = id;
				if (cm.getType().equals("login") || cm.getType().equals("create")) {
					this.username = cm.getUsername();
				}
				cm.setSentUser(username);
				cm.serverID = id;
				cs.sendMessageToAllClients(cm);
			}
		} catch (EOFException e) {
			// donothing
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
				if (pw != null)
					pw.close();
				if (s != null)
					s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
