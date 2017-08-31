package LoginViews;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import other.Constants;
import networking.Client;

public class Professorlogin extends JFrame {
	private JPanel Millerpanel;
	private Image MillerImage;
	private JPanel centerpanel;
	private JPanel loginpanel;
	private JLabel welcomelabel;
	private JPanel mainpanel;
	private JPanel credentials;
	private JLabel username;
	private JLabel password;
	private JTextField usernamet;
	private JPasswordField passwordt;
	private JButton loginb;
	private JButton createb;
	private JPanel usernamep;
	private JPanel usernametp;
	private JPanel passwordp;
	private JPanel passwordtp;
	private JPanel buttonp;
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	int accountfound = 0;
	private JLabel errorlabel;
	private Client client;

	public Professorlogin() throws UnknownHostException, IOException {
		super("Professor Login");

		client = new Client(Constants.clientIP, 6789);
		client.start();

		setSize(600, 400);
		setLocation(100, 100);
		setResizable(false);
		initialization();
	}

	public void initialization() {

		Color gray = new Color(119, 122, 129);

		BufferedImage img = null;

		try {
			MillerImage = ImageIO.read(getClass().getResource("Miller.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Millerpanel = new JPanel(new BorderLayout()) {
			@Override
			public void paintComponent(Graphics G) {
				super.paintComponent(G);
				G.drawImage(MillerImage, 0, 0, 250, 400, null);
			}

		};

		accountfound = 0;

		Millerpanel.setPreferredSize(new Dimension(250, 400));
		Millerpanel.setBackground(gray);

		centerpanel = new JPanel(new BorderLayout());
		centerpanel.setBackground(gray);
		centerpanel.setBorder(new EmptyBorder(15, 15, 0, 0));

		loginpanel = new JPanel(new BorderLayout());
		loginpanel.setBackground(Color.white);
		loginpanel.setBorder(new EmptyBorder(30, 0, 0, 0));

		welcomelabel = new JLabel("Welcome To The Interns Club", SwingConstants.CENTER);
		welcomelabel.setFont(new Font("Times New Roman", Font.PLAIN, 19));

		mainpanel = new JPanel(new BorderLayout());
		mainpanel.setBorder(new EmptyBorder(20, 30, 90, 30));
		mainpanel.setBackground(Color.white);

		credentials = new JPanel();
		credentials.setLayout(new BoxLayout(credentials, BoxLayout.Y_AXIS));
		credentials.setBackground(Color.lightGray);

		username = new JLabel("Username");
		password = new JLabel("Password");
		usernamet = new JTextField();
		passwordt = new JPasswordField();
		loginb = new JButton("Login");
		loginb.setPreferredSize(new Dimension(120, 40));
		createb = new JButton("Create Account");
		createb.setPreferredSize(new Dimension(120, 40));

		usernamep = new JPanel(new BorderLayout());
		usernametp = new JPanel(new BorderLayout());
		passwordp = new JPanel(new BorderLayout());
		passwordtp = new JPanel(new BorderLayout());
		buttonp = new JPanel();

		errorlabel = new JLabel();
		errorlabel.setText("");
		errorlabel.setForeground(Color.red);
		errorlabel.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		errorlabel.setHorizontalAlignment(SwingConstants.CENTER);

		createGui();
		actionlisteners();

	}

	public void createGui() {
		add(Millerpanel, BorderLayout.EAST);
		add(centerpanel, BorderLayout.CENTER);
		centerpanel.add(loginpanel);
		loginpanel.add(welcomelabel, BorderLayout.NORTH);
		loginpanel.add(mainpanel, BorderLayout.CENTER);
		mainpanel.add(credentials, BorderLayout.CENTER);
		mainpanel.add(errorlabel, BorderLayout.SOUTH);
		credentials.add(Box.createVerticalGlue());
		credentials.add(usernamep);
		usernamep.add(username);
		credentials.add(usernametp);
		usernametp.add(usernamet);
		credentials.add(passwordp);
		passwordp.add(password);
		credentials.add(passwordtp);
		passwordtp.add(passwordt);
		credentials.add(buttonp);
		buttonp.add(loginb);
		buttonp.add(createb);
		credentials.add(Box.createVerticalGlue());
		setVisible(true);
	}

	public void actionlisteners() {
		loginb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				errorlabel.setText("");
				String Username = usernamet.getText().trim();
				char[] text = passwordt.getPassword();
				String Password = String.copyValueOf(text);
				client.sendMessage("login", Username, Password, "professor");
				while (!client.received) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (client.success) {
					ProfessorShell ps = new ProfessorShell(Username, client);
					dispose();
				} else {
					// error
					errorlabel.setText("Wrong username or password");

				}
			}
		});
		createb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				errorlabel.setText("");
				String Username = usernamet.getText().trim();
				char[] text = passwordt.getPassword();
				String Password = String.copyValueOf(text);
				client.sendMessage("create", Username, Password, "professor");
				while (client.createResult == -1) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				switch (client.createResult) {
				case 0:{
					ProfessorReg es = new ProfessorReg(Username, Password, client);
					dispose();
				}
					break;
				case 1:
					errorlabel.setText("Password requirements not met");
					break;
				case 2:
					errorlabel.setText("This username already exists");
					break;
				}
				client.createResult = -1;

			}
		});

	}
}
