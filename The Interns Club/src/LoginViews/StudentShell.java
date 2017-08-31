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

import client.NotificationPanel;
import client.StudentProfile;
import client.StudentViewJob;
import client.UploadResume;
import networking.Client;
import networking.Notification;
import users.Student;

public class StudentShell extends JFrame implements Runnable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JPanel main;
	JToolBar Studentshell;
	JButton homeButton;
	JButton uploadButton;
	JButton viewButton;
	JButton logoutButton;
	JButton notification;
	BufferedImage homeImage;
	StudentProfile studentProfile;
	JFrame thisframe;
	UploadResume uploadresume;
	NotificationPanel notifpanel;
	StudentViewJob studentviewjob;
	String username;
	private Client client;
	Vector<Notification> notifications;
	ImageIcon notifimage;
	BufferedImage testimage = null;

	public StudentShell(String username, Client client) {
		super("The Interns Club");
		notifications = new Vector<Notification>();
		setSize(500, 500);
		setLocation(100, 100);
		this.username = username;
		this.client = client;
		setResizable(false);
		initialization();
	}

	public void initialization() {
		thisframe = this;
		main = new JPanel();
		Student student = client.getStudent();
		studentProfile = new StudentProfile(username, client, student);
		uploadresume = new UploadResume(username, client);
		notifpanel = new NotificationPanel(client);
		Thread t = new Thread(notifpanel);
		t.start();

		studentviewjob = new StudentViewJob(client, username, student);
		Thread t2 = new Thread(studentviewjob);
		t2.start();

		main.add(studentProfile);
		main.setBackground(Color.white);
		CardLayout cl = new CardLayout();
		main.setLayout(cl);
		main.add(studentProfile, "first");
		main.add(uploadresume, "second");
		main.add(notifpanel, "third");
		main.add(studentviewjob, "forth");

		try {
			testimage = ImageIO.read(this.getClass().getResource("shadowblue.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Studentshell = new JToolBar() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(testimage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		Studentshell.setOpaque(false);

		Studentshell.setFloatable(false);
		ImageIcon homeimage = new ImageIcon(this.getClass().getResource("home.png"));
		Image dum = homeimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		homeimage = new ImageIcon(dum);
		homeButton = new JButton(homeimage);
		homeButton.setOpaque(false);
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
		ImageIcon uploadimage = new ImageIcon(this.getClass().getResource("upload.png"));
		Image dum1 = uploadimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		uploadimage = new ImageIcon(dum1);
		uploadButton = new JButton(uploadimage);
		uploadButton.setOpaque(false);
		uploadButton.setContentAreaFilled(false);
		uploadButton.setBorderPainted(false);
		uploadButton.setPreferredSize(new Dimension(50, 30));
		uploadButton.setToolTipText("Upload Resume");
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(main, "second");
			}
		});
		ImageIcon viewimage = new ImageIcon(this.getClass().getResource("view.png"));
		Image dum2 = viewimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		viewimage = new ImageIcon(dum2);
		viewButton = new JButton(viewimage);
		viewButton.setOpaque(false);
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
		logoutButton.setPreferredSize(new Dimension(50, 30));
		logoutButton.setOpaque(false);
		logoutButton.setContentAreaFilled(false);
		logoutButton.setBorderPainted(false);
		logoutButton.setToolTipText("Log out");
		logoutButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// client.close();
				thisframe.dispose();
				// ClientGui clientgui = new ClientGui();
			}
		});

		notifimage = new ImageIcon(this.getClass().getResource("notif.png"));
		Image dum4 = notifimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		notifimage = new ImageIcon(dum4);
		notification = new JButton(notifimage);
		notification.setContentAreaFilled(false);
		notification.setBorderPainted(false);
		notification.setPreferredSize(new Dimension(50, 30));
		notification.setToolTipText("Notification");
		notification.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				client.checkedNotifications();
				notification.setIcon(notifimage);
				cl.show(main, "third");
			}
		});
		CreateGui();
	}

	public void CreateGui() {
		Studentshell.add(homeButton);
		// Studentshell.add(Box.createHorizontalStrut(10));
		Studentshell.add(uploadButton);
		// Studentshell.add(Box.createHorizontalStrut(10));
		Studentshell.add(viewButton);
		// Studentshell.add(Box.createHorizontalStrut(10));
		Studentshell.add(notification);
		Studentshell.add(Box.createGlue());
		Studentshell.add(logoutButton);
		add(Studentshell, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		setVisible(true);
	}

	public void actionlisteners() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			if (client == null) {
				continue;
			}
			client.checkForUpdates();
			boolean read = false;
			if (client.getNotifications() != null) {
				Vector<Notification> notifs = client.getNotifications();
				for (Notification n : notifs) {
					if (!n.isHasRead()) {
						read = true;
						break;
					}
				}

				if (read) {
					ImageIcon uploadimage = new ImageIcon(this.getClass().getResource("rednotification.png"));
					Image dum1 = uploadimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
					uploadimage = new ImageIcon(dum1);
					notification.setIcon(uploadimage);
				}
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}
