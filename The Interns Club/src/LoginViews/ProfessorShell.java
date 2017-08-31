package LoginViews;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import client.ProfessorProfile;
import client.ProfessorViewJob;
import client.StudentViewJob;
import networking.Client;
import networking.Notification;
import users.Professor;

public class ProfessorShell extends JFrame implements Runnable {
	JPanel main;
	JToolBar Profshell;
	JButton homeButton;
	JButton viewButton;
	JButton logoutButton;
	BufferedImage homeImage;
	private String username;
	JFrame thisframe;
	ProfessorViewJob professorviewjob;
	BufferedImage testimage = null;

	private Client client;
	ProfessorProfile professorProfile;

	public ProfessorShell(String username, Client client) {
		super("The Interns Club");
		this.client = client;
		this.username = username;
		setSize(500, 500);
		setLocation(100, 100);
		setResizable(false);
		initialization();
	}

	public void initialization() {
		main = new JPanel();
		Professor professor = client.getProfessor();
		professorProfile = new ProfessorProfile(username, professor, client);
		professorviewjob = new ProfessorViewJob(client, username,  professor);
		Thread t2 = new Thread(professorviewjob);
		t2.start();
		thisframe = this;
		main.setBackground(Color.white);
		main.add(professorProfile);
		CardLayout cl = new CardLayout();
		main.setLayout(cl);
		main.add(professorProfile, "first");
		main.add(professorviewjob, "second");
		Profshell = new JToolBar();
		
		try {
			testimage = ImageIO.read(this.getClass().getResource("shadowblue.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Profshell = new JToolBar(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(testimage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		
		Profshell.setOpaque(false);
		// Empshell.setBackground(Color.white);
		Profshell.setFloatable(false);
		ImageIcon homeimage = new ImageIcon(this.getClass().getResource("home.png"));
		Image dum = homeimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		homeimage = new ImageIcon(dum);
		homeButton = new JButton(homeimage);
		homeButton.setContentAreaFilled(false);
		homeButton.setBorderPainted(false);
		homeButton.setPreferredSize(new Dimension(50, 30));
		homeButton.setToolTipText("Home");
		homeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(main, "first");
			}
		});
		ImageIcon viewimage = new ImageIcon(this.getClass().getResource("view.png"));
		Image dum2 = viewimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		viewimage = new ImageIcon(dum2);
		viewButton = new JButton(viewimage);
		viewButton.setContentAreaFilled(false);
		viewButton.setBorderPainted(false);
		viewButton.setPreferredSize(new Dimension(50, 30));
		viewButton.setToolTipText("Recommend Students");
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(main, "second");
			}
		});
		ImageIcon logoutimage = new ImageIcon(this.getClass().getResource("logout.png"));
		Image dum3 = logoutimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		logoutimage = new ImageIcon(dum3);
		logoutButton = new JButton(logoutimage);
		logoutButton.setContentAreaFilled(false);
		logoutButton.setBorderPainted(false);
		logoutButton.setPreferredSize(new Dimension(50, 30));
		logoutButton.setToolTipText("Log out");
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				//client.close();
				thisframe.dispose();
				// ClientGui clientgui = new ClientGui();
			}
		});
		CreateGui();
	}

	public void CreateGui() {
		Profshell.add(homeButton);
		//Profshell.add(Box.createHorizontalStrut(10));
		Profshell.add(viewButton);
		Profshell.add(Box.createGlue());
		Profshell.add(logoutButton);
		add(Profshell, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		setVisible(true);
	}

	public void actionlisteners() {

	}

	@Override
	public void run() {
		while (true) {
			if (client == null) {
				continue;
			}
			client.sendMessage("getjobs", "null");
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		
	}
}
