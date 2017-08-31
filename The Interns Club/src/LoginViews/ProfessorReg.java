package LoginViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import networking.Client;
import users.Professor;

public class ProfessorReg extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;
	private JLabel email, fname, lname, school, profilePicture, phone, professor, rq1, rq2, rq3, rq4, rq5, requiredKey,
			iFName, shortBioLabel;
	private JTextField schoolField, emailField, fNameField, lNameField, phoneField, profName;
	private JButton upload1, finish;
	private JPanel mainPanel, gridPanel, flow0, flow1, flow2, flow3, flow4, flow5, flow6, flow7, flow8;
	private JScrollPane jsp;
	private JScrollPane mainScroll;
	private JTextArea shortBio;
	private JFileChooser imageFileChooser;
	private String username, password, filePath;

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	JFrame thisframe;
	private Client client;
	private File file;
	BufferedImage testimage = null;

	public ProfessorReg(String username, String password, Client client) {
		super("Professor Registration");
		setSize(600, 600);
		setLocation(500, 500);
		setBackground(Color.WHITE);
		thisframe = this;
		this.username = username;
		this.client = client;
		this.password = password;
		initializeVariables();
		createGUI();
		addListeners();

		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public void initializeVariables() {
		email = createLabel("Email (@.edu)       ");
		fname = createLabel("First Name             ");
		lname = createLabel("Last Name              ");
		phone = createLabel("Phone number         ");
		school = createLabel("School Affiliation   ");
		profilePicture = createLabel("Profile Picture                ");
		phone = createLabel("Office Phone          ");
		iFName = createLabel("");
		shortBioLabel = createLabel("Bio                ");
		requiredKey = new JLabel("* = Required field");
		rq1 = new JLabel(" *", SwingConstants.RIGHT);
		rq2 = new JLabel(" *", SwingConstants.RIGHT);
		rq3 = new JLabel(" *", SwingConstants.RIGHT);
		rq4 = new JLabel(" *", SwingConstants.RIGHT);
		rq5 = new JLabel(" *", SwingConstants.RIGHT);

		try {
			testimage = ImageIO.read(this.getClass().getResource("shadowblue.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mainPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(testimage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		mainPanel.setBackground(Color.WHITE);
		gridPanel = new JPanel(new GridLayout(9, 1));
		gridPanel.setOpaque(false);
		gridPanel.setBackground(Color.WHITE);
		flow0 = new JPanel();
		flow0.setOpaque(false);
		flow0.setLayout(new FlowLayout(FlowLayout.RIGHT));
		flow0.setBackground(Color.WHITE);
		flow1 = new JPanel();
		flow1.setOpaque(false);
		flow1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow1.setBackground(Color.WHITE);
		flow2 = new JPanel();
		flow2.setOpaque(false);
		flow2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow2.setBackground(Color.WHITE);
		flow3 = new JPanel();
		flow3.setOpaque(false);
		flow3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow3.setBackground(Color.WHITE);
		flow4 = new JPanel();
		flow4.setOpaque(false);
		flow4.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow4.setBackground(Color.WHITE);
		flow5 = new JPanel();
		flow5.setOpaque(false);
		flow5.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow5.setBackground(Color.WHITE);
		flow6 = new JPanel();
		flow6.setOpaque(false);
		flow6.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow6.setBackground(Color.WHITE);
		flow7 = new JPanel();
		flow7.setOpaque(false);
		flow7.setLayout(new FlowLayout());
		flow7.setBackground(Color.WHITE);
		flow8 = new JPanel();
		flow8.setOpaque(false);
		flow8.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow8.setBackground(Color.WHITE);

		shortBio = new JTextArea(3, 20);
		shortBio.setWrapStyleWord(true);
		shortBio.setLineWrap(true);
		jsp = new JScrollPane(shortBio);
		upload1 = createButton("Upload");
		finish = createButton("Finish");
		finish.setEnabled(false);

		emailField = new JTextField(SwingConstants.CENTER);
		emailField.setPreferredSize(new Dimension(150, 20));
		fNameField = new JTextField(SwingConstants.CENTER);
		fNameField.setPreferredSize(new Dimension(150, 20));
		lNameField = new JTextField(SwingConstants.CENTER);
		lNameField.setPreferredSize(new Dimension(150, 20));
		schoolField = new JTextField(SwingConstants.CENTER);
		schoolField.setPreferredSize(new Dimension(150, 20));
		phoneField = new JTextField(SwingConstants.CENTER);
		phoneField.setPreferredSize(new Dimension(150, 20));
		profName = new JTextField(SwingConstants.CENTER);
		profName.setPreferredSize(new Dimension(150, 20));

		// change filter to images
		imageFileChooser = new JFileChooser();
		imageFileChooser.setPreferredSize(new Dimension(400, 500));
		File workingDirectory = new File(System.getProperty("user.dir"));
		imageFileChooser.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files",
				ImageIO.getReaderFileSuffixes());
		imageFileChooser.setFileFilter(imageFilter);
	}

	public void createGUI() {

		add(mainPanel);

		mainScroll = new JScrollPane(mainPanel);
		add(mainScroll);

		flow0.add(requiredKey);
		gridPanel.add(flow0);

		flow1.add(school);
		flow1.add(schoolField);
		flow1.add(rq3);
		gridPanel.add(flow1);

		flow2.add(email);
		flow2.add(emailField);
		flow2.add(rq2);
		gridPanel.add(flow2);

		flow3.add(fname);
		flow3.add(fNameField);
		flow3.add(rq4);
		gridPanel.add(flow3);

		flow8.add(lname);
		flow8.add(lNameField);
		flow8.add(rq5);
		gridPanel.add(flow8);

		flow4.add(profilePicture);
		flow4.add(upload1);
		flow4.add(iFName);
		gridPanel.add(flow4);

		flow5.add(phone);
		flow5.add(phoneField);
		gridPanel.add(flow5);

		flow6.add(shortBioLabel);
		flow6.add(jsp);
		gridPanel.add(flow6);

		flow7.add(finish);
		gridPanel.add(flow7);

		mainPanel.add(gridPanel);

	}

	public void addListeners() {
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Professor temp = new Professor(client.username);
				temp.setFirstName(fNameField.getText());
				temp.setLastName(lNameField.getText());
				temp.setBio(shortBio.getText());
				temp.setEmail(emailField.getText());
				temp.setSchool(schoolField.getText());
				temp.setPhone(phoneField.getText());
				temp.setProfilePic("asdf");

				if (file != null) {
					temp.setImage(new ImageIcon(file.getPath()));
				} else {
					temp.setImage(new ImageIcon("images/noimg.jpg"));
				}

				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new Date();

				temp.setJoinDate(dateFormat.format(date));
				client.setProfessor(temp);
				client.sendMessage(temp, client.username, "updateProfessor");

				thisframe.dispose();
				ProfessorShell es = new ProfessorShell(username, client);
			}

		});
		// image
		upload1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int value = imageFileChooser.showOpenDialog(ProfessorReg.this);
				if (value == JFileChooser.APPROVE_OPTION) {
					File file1 = imageFileChooser.getSelectedFile();
					file = file1;
					iFName.setText(file1.getName());
				} else if (value == JFileChooser.CANCEL_OPTION) {
					System.out.println("you closed without selecting file");
				}
			}

		});

		schoolField.getDocument().addDocumentListener(new isBlank());

		emailField.getDocument().addDocumentListener(new isBlank());

		fNameField.getDocument().addDocumentListener(new isBlank());

		lNameField.getDocument().addDocumentListener(new isBlank());

	}

	private class isBlank implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			finish.setEnabled(!isFieldsEmpty());
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			finish.setEnabled(!isFieldsEmpty());
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			finish.setEnabled(!isFieldsEmpty());
		}
	}

	public boolean isFieldsEmpty() {
		if (schoolField.getText().trim().equals("") == true || emailField.getText().trim().equals("") == true
				|| emailField.getText().contains(".edu") == false || fNameField.getText().trim().equals("") == true
				|| lNameField.getText().trim().equals("") == true) {
			return true;
		} else {
			return false;
		}
	}

	public JButton createButton(String x) {
		JButton tempButton = new JButton(x);
		Font font = new Font("Verdana", Font.BOLD, 12);
		tempButton.setFont(font);
		return tempButton;
	}

	public JLabel createLabel(String x) {
		JLabel tempLabel = new JLabel(x, SwingConstants.LEFT);
		Font font = new Font("Verdana", Font.BOLD, 12);
		tempLabel.setFont(font);
		return tempLabel;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}
}
