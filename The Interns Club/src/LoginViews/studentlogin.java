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

import networking.Client;
import other.Constants;

public class studentlogin extends JFrame implements Runnable {
	private JPanel Tommypanel;
	private Image TommyImage;
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
	private JLabel errorlabel;
	private Connection conn = null;
	private Statement st = null;
	private ResultSet rs = null;
	int accountfound = 0;
	private Client client;

	public studentlogin() throws UnknownHostException, IOException {
		super("Student Login");

		client = new Client(Constants.clientIP, 6789);
		client.start();

		setSize(600, 400);
		setLocation(100, 100);
		setResizable(false);
		initialization();
	}

	public void initialization() {

		Color red = new Color(153, 0, 0);

		BufferedImage img = null;

		try {
			TommyImage = ImageIO.read(getClass().getResource("Tommy.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Tommypanel = new JPanel(new BorderLayout()) {
			@Override
			public void paintComponent(Graphics G) {
				super.paintComponent(G);
				G.drawImage(TommyImage, 0, 0, 250, 400, null);
			}

		};
		Tommypanel.setPreferredSize(new Dimension(250, 400));
		Tommypanel.setBackground(red);

		centerpanel = new JPanel(new BorderLayout());
		centerpanel.setBackground(red);
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
		add(Tommypanel, BorderLayout.WEST);
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
				client.sendMessage("login", Username, Password, "student");
				while (!client.received) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (client.success) {
					StudentShell es = new StudentShell(Username, client);
					Thread t = new Thread(es);
					t.start();
					dispose();
				} else {
					// error
					errorlabel.setText("Wrong username or password");

				}
				client.received = false;
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
				client.sendMessage("create", Username, Password, "student");
				while (client.createResult == -1) {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				switch (client.createResult) {
				case 0: {
					StudentReg es = new StudentReg(Username, Password, client);
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

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
