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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import networking.Client;
import users.Student;

public class StudentReg extends JFrame {
	private static final long serialVersionUID = 1L;
	private JLabel email, fname, lname, school, profilePicture, resume, phone, professor, rq1, rq2, rq3, rq4, rq5, GPA,
			major, requiredKey, iFName1, iFName2, bio;
	private JTextField emailField, fNameField, lNameField, schoolField, phoneField, profName, GPATF, majorTF;
	private JButton upload1, upload2, finish;
	private JPanel mainPanel, gridPanel, flow0, flow1, flow2, flow3, flow4, flow5, flow6, flow7, flow8, flow9, flow10,
			flow11, flow12;
	private String username, password;
	private JFileChooser imageFileChooser;
	private JFileChooser textFileChooser;
	private JScrollPane jsp;
	private JTextArea jtaBio;
	private JScrollPane mainScroll;
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	JFrame thisframe;
	private Client client;
	transient FileReader fileReader = null;
	private String resumeText = "";
	transient BufferedReader bufferedReader = null;
	BufferedImage testimage = null;

	public StudentReg(String username, String password, Client client) {
		super("Student Registration");
		setSize(600, 750);
		this.client = client;
		setLocation(500, 500);
		setBackground(Color.WHITE);
		this.username = username;
		this.password = password;
		thisframe = this;
		initializeVariables();
		createGUI();
		addListeners();
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public void initializeVariables() {
		email = createLabel("Email (@.edu)         ");
		fname = createLabel("First Name               ");
		lname = createLabel("Last Name                ");
		phone = createLabel("Phone number   ");
		school = createLabel("School Affiliation     ");
		profilePicture = createLabel("Profile Picture                    ");
		resume = createLabel("Resume                               ");
		phone = createLabel("Phone Number         ");
		iFName1 = createLabel("");
		iFName2 = createLabel("");
		requiredKey = createLabel("* = Required field");
		GPA = createLabel("GPA                           ");
		major = createLabel("Major                         ");
		bio = createLabel("Bio               ");

		rq1 = createLabel(" *");
		rq2 = createLabel(" *");
		rq3 = createLabel(" *");
		rq4 = createLabel(" *");
		rq5 = createLabel(" *");

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
		gridPanel = new JPanel(new GridLayout(12, 1));
		gridPanel.setOpaque(false);
		// gridPanel.setBackground(Color.WHITE);

		flow0 = new JPanel();
		flow0.setOpaque(false);
		flow0.setLayout(new FlowLayout(FlowLayout.RIGHT));
		flow0.setBackground(Color.WHITE);
		flow1 = new JPanel();
		flow1.setOpaque(false);
		flow1.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow1.setBackground(Color.WHITE);
		flow2 = new JPanel();
		flow2.setOpaque(false);
		flow2.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow2.setBackground(Color.WHITE);
		flow3 = new JPanel();
		flow3.setOpaque(false);
		flow3.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow3.setBackground(Color.WHITE);
		flow4 = new JPanel();
		flow4.setOpaque(false);
		flow4.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow4.setBackground(Color.WHITE);
		flow5 = new JPanel();
		flow5.setOpaque(false);
		flow5.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow5.setBackground(Color.WHITE);
		flow6 = new JPanel();
		flow6.setOpaque(false);
		flow6.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow6.setBackground(Color.WHITE);
		flow7 = new JPanel();
		flow7.setOpaque(false);
		flow7.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow7.setBackground(Color.WHITE);
		flow8 = new JPanel();
		flow8.setOpaque(false);
		flow8.setLayout(new FlowLayout());
		flow8.setBackground(Color.WHITE);
		flow9 = new JPanel();
		flow9.setOpaque(false);
		flow9.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow9.setBackground(Color.WHITE);
		flow10 = new JPanel();
		flow10.setOpaque(false);
		flow10.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow10.setBackground(Color.WHITE);
		flow11 = new JPanel();
		flow11.setOpaque(false);
		flow11.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow11.setBackground(Color.WHITE);
		flow12 = new JPanel();
		flow12.setOpaque(false);
		flow12.setLayout(new FlowLayout(FlowLayout.LEFT));
		flow12.setBackground(Color.WHITE);

		upload1 = createButton("Upload");
		upload2 = createButton("Upload");
		finish = createButton("Finish");
		finish.setEnabled(false);
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(150, 20));
		fNameField = new JTextField();
		fNameField.setPreferredSize(new Dimension(150, 20));
		lNameField = new JTextField();
		lNameField.setPreferredSize(new Dimension(150, 20));
		schoolField = new JTextField();
		schoolField.setPreferredSize(new Dimension(150, 20));
		phoneField = new JTextField();
		phoneField.setPreferredSize(new Dimension(150, 20));
		profName = new JTextField();
		profName.setPreferredSize(new Dimension(150, 20));
		GPATF = new JTextField();
		GPATF.setPreferredSize(new Dimension(150, 20));
		majorTF = new JTextField();
		majorTF.setPreferredSize(new Dimension(150, 20));

		jtaBio = new JTextArea(3, 20);
		jtaBio.setWrapStyleWord(true);
		jtaBio.setLineWrap(true);
		jsp = new JScrollPane(jtaBio);

		imageFileChooser = new JFileChooser();
		textFileChooser = new JFileChooser();
		// for IMAGEs
		imageFileChooser.setPreferredSize(new Dimension(400, 500));
		File workingDirectory = new File(System.getProperty("user.dir"));
		imageFileChooser.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files",
				ImageIO.getReaderFileSuffixes());
		imageFileChooser.setFileFilter(imageFilter);

		textFileChooser.setPreferredSize(new Dimension(400, 500));
		textFileChooser.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter textFilter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
		textFileChooser.setFileFilter(textFilter);
	}

	public void createGUI() {
		mainScroll = new JScrollPane(mainPanel);
		add(mainScroll);

		flow0.add(requiredKey);
		gridPanel.add(flow0);

		flow1.add(email);
		flow1.add(emailField);
		flow1.add(rq1);
		gridPanel.add(flow1);

		flow2.add(fname);
		flow2.add(fNameField);
		flow2.add(rq5);
		gridPanel.add(flow2);

		flow12.add(lname);
		flow12.add(lNameField);
		flow12.add(rq4);
		gridPanel.add(flow12);

		flow3.add(school);
		flow3.add(schoolField);
		flow3.add(rq3);
		gridPanel.add(flow3);

		flow4.add(profilePicture);
		flow4.add(upload1);
		flow4.add(iFName1);
		gridPanel.add(flow4);

		flow5.add(resume);
		flow5.add(upload2);
		flow5.add(iFName2);
		gridPanel.add(flow5);

		flow6.add(phone);
		flow6.add(phoneField);
		gridPanel.add(flow6);

		flow9.add(major);
		flow9.add(majorTF);
		gridPanel.add(flow9);

		flow10.add(GPA);
		flow10.add(GPATF);
		gridPanel.add(flow10);

		flow11.add(bio);
		flow11.add(jsp);
		gridPanel.add(flow11);

		flow8.add(finish);
		gridPanel.add(flow8);

		mainPanel.add(gridPanel);

	}

	public void addListeners() {
		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Student temp = new Student(client.username);
				temp.setFirstName(fNameField.getText());
				temp.setLastName(lNameField.getText());
				temp.setSchool(schoolField.getText());
				temp.setEmail(emailField.getText());
				temp.setBio(jtaBio.getText());
				temp.setMajor(majorTF.getText());
				temp.setGPA(GPATF.getText());

				if (imageFileChooser.getSelectedFile() != null
						&& imageFileChooser.getSelectedFile().getAbsolutePath() != null) {
					temp.setProfilePic(imageFileChooser.getSelectedFile().getAbsolutePath());
					temp.setImage(new ImageIcon(imageFileChooser.getSelectedFile().getPath()));
				} else {
					temp.setImage(new ImageIcon("images/noImg.jpg"));
				}

				if (resumeText != null && !resumeText.trim().equals("")) {
					temp.setResume(resumeText);
				}

				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new Date();
				temp.setJoinDate(dateFormat.format(date));

				client.setStudent(temp);

				client.sendMessage(temp, client.username, "updateStudent");

				StudentShell es = new StudentShell(username, client);
				Thread t = new Thread(es);
				t.start();

				thisframe.dispose();
			}

		});
		// image
		upload1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int value = imageFileChooser.showOpenDialog(StudentReg.this);
				if (value == JFileChooser.APPROVE_OPTION) {
					File file1 = imageFileChooser.getSelectedFile();
					iFName1.setText(file1.getName());
				} else if (value == JFileChooser.CANCEL_OPTION) {
					System.out.println("you closed with out selecting file");
				}
			}

		});
		// resume
		upload2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int value = textFileChooser.showOpenDialog(null);
				resumeText = "";
				if (value == JFileChooser.APPROVE_OPTION) {
					File selectedFile = textFileChooser.getSelectedFile();
					iFName2.setText(selectedFile.getName());
					try {
						fileReader = new FileReader(selectedFile);
						bufferedReader = new BufferedReader(fileReader);
						String templine = "";
						while (((templine = bufferedReader.readLine()) != null)) {
							resumeText += (templine + "\n");
						}

					} catch (FileNotFoundException ex) {
						System.out.println("Unable to open the file");
					} catch (IOException ex) {
						System.out.println("Error reading the file");
					} finally {
						if (bufferedReader != null) {
							try {
								bufferedReader.close();
							} catch (IOException ioe) {
							}
						}
						if (fileReader != null) {
							try {
								fileReader.close();
							} catch (IOException ioe) {
							}
						}
					}
				} else if (value == JFileChooser.CANCEL_OPTION) {

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
		JLabel tempLabel = new JLabel(x);
		Font font = new Font("Verdana", Font.BOLD, 12);
		tempLabel.setFont(font);
		return tempLabel;
	}

}
