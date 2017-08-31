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
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import client.GuestStudentViewJob;
import networking.Client;

public class GuestStudentShell extends JFrame {
	JPanel main;
	JToolBar Studentshell;
	JButton viewButton;
	JButton logoutButton;
	JFrame thisframe;
	GuestStudentViewJob gueststudentviewjob;
	private Client client;
	BufferedImage testimage = null;

	public GuestStudentShell() throws UnknownHostException, IOException {
		super("The Interns Club");
		client = new Client("localhost", 6789);
		client.start();

		setSize(500, 500);
		setLocation(100, 100);
		// setResizable(false);
		initialization();
	}

	public void initialization() {
		thisframe = this;
		main = new JPanel();

		gueststudentviewjob = new GuestStudentViewJob(client);
		Thread t1 = new Thread(gueststudentviewjob);
		t1.start();

		main.setBackground(Color.white);
		CardLayout cl = new CardLayout();
		main.setLayout(cl);
		main.add(gueststudentviewjob);
		main.add(gueststudentviewjob, "forth");
		Studentshell = new JToolBar();
		// Empshell.setBackground(Color.white);
		Studentshell.setFloatable(false);
		Studentshell.setOpaque(false);
		try {
			testimage = ImageIO.read(this.getClass().getResource("shadowblue.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Studentshell = new JToolBar(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(testimage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		
		Studentshell.setOpaque(false);
		ImageIcon viewimage = new ImageIcon(this.getClass().getResource("view.png"));
		Image dum2 = viewimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		viewimage = new ImageIcon(dum2);
		viewButton = new JButton(viewimage);
		viewButton.setContentAreaFilled(false);
		viewButton.setBorderPainted(false);
		viewButton.setPreferredSize(new Dimension(50, 30));
		viewButton.setToolTipText("View Jobs");
		viewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(main, "forth");
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
				thisframe.dispose();
				//ClientGui clientgui = new ClientGui();
			}
		});

		CreateGui();
	}

	public void CreateGui() {

		Studentshell.add(viewButton);

		Studentshell.add(Box.createGlue());
		Studentshell.add(logoutButton);
		add(Studentshell, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		setVisible(true);
	}

	public void actionlisteners() {

	}
}
