package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;

import networking.Client;
import users.Student;

public class StudentProfile extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton save, edit, newImage;
	private JLabel profilePicture, bioLabel, joinDate, university, major, GPA, firstnamelabel, lastnamelabel, email;
	private JTextField firstnametext, lastnametext, joinDatetext, universitytext, majortext, GPAtext, emailtext;
	private JTextArea bio;
	private JPanel bottomPanel, editPanel;
	String username;
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	String imagepath;
	Border original;
	String absoluteimagepath;
	Client client;
	JPanel topPanel;
	Student student;

	public StudentProfile(String username, Client client, Student student) {
		this.username = username;
		this.student = student;
		this.client = client;
		initialization();
		createGUI2();
		addListeners();
	}

	/*
	 * public StudentProfile() { initComponents(); createGUI(); addListeners();
	 * }
	 */

	public void initialization() {

		joinDatetext = new JTextField("");

		universitytext = new JTextField("");

		majortext = new JTextField("");

		GPAtext = new JTextField("");

		email = new JLabel("Email: ");
		firstnamelabel = new JLabel("First Name: ");
		lastnamelabel = new JLabel("Last Name: ");

		emailtext = new JTextField(student.getEmail());

		original = emailtext.getBorder();
		joinDatetext.setBorder(null);
		universitytext.setBorder(null);
		majortext.setBorder(null);
		GPAtext.setBorder(null);
		emailtext.setBorder(null);

		save = new JButton("Save");
		edit = new JButton("Edit Profile");

		newImage = new JButton("Upload New Picture");

		bioLabel = new JLabel("Bio: ");
		bioLabel.setAlignmentX(LEFT_ALIGNMENT);

		firstnametext = new JTextField(student.getFirstName());

		firstnametext.setEditable(false);

		firstnametext.setBorder(null);

		lastnametext = new JTextField(student.getLastName());

		lastnametext.setEditable(false);

		lastnametext.setBorder(null);

		joinDate = new JLabel("Join Date: ");
		joinDatetext.setText(student.getJoinDate());
		joinDate.setAlignmentX(LEFT_ALIGNMENT);
		joinDate.setHorizontalAlignment(SwingConstants.LEFT);

		university = new JLabel("University: ");
		universitytext.setText(student.getSchool());
		university.setAlignmentX(LEFT_ALIGNMENT);
		university.setHorizontalAlignment(SwingConstants.LEFT);

		major = new JLabel("Major: ");
		majortext.setText(student.getMajor());
		major.setAlignmentX(LEFT_ALIGNMENT);
		major.setHorizontalAlignment(SwingConstants.LEFT);
		major.setAlignmentX(Component.LEFT_ALIGNMENT);

		GPA = new JLabel("GPA: ");
		GPAtext.setText(student.getGPA());
		GPA.setAlignmentX(LEFT_ALIGNMENT);
		GPA.setHorizontalAlignment(SwingConstants.LEFT);

		bio = new JTextArea(student.getBio());
		bio.setPreferredSize(new Dimension(500, 200));
		bio.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		bio.setEditable(false);
		bio.setWrapStyleWord(true);
		bio.setLineWrap(true);

		joinDatetext.setEditable(false);
		universitytext.setEditable(false);
		majortext.setEditable(false);
		GPAtext.setEditable(false);
		emailtext.setEditable(false);
	}

	public void createGUI2() {
		setSize(500, 470);
		setMaximumSize(new Dimension(500, 450));
		setMinimumSize(new Dimension(500, 450));
		setBackground(Color.WHITE);

		JPanel mainArea = new JPanel();
		mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
		mainArea.setBackground(Color.white);

		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		if (student.getImage() != null) {
			ImageIcon noImg = student.getImage();
			Image dum1 = noImg.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
			noImg = new ImageIcon(dum1);
			profilePicture = new JLabel(noImg);
			profilePicture.setPreferredSize(new Dimension(150, 150));
		}

		else {
			ImageIcon noImg = new ImageIcon("images/noimg.jpg");
			Image dum1 = noImg.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
			noImg = new ImageIcon(dum1);
			profilePicture = new JLabel(noImg);
			profilePicture.setPreferredSize(new Dimension(150, 150));
		}

		JPanel nameDate = new JPanel();

		nameDate.setLayout(new GridLayout(7, 1));
		nameDate.setBackground(Color.white);

		JPanel p1 = new JPanel();
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p1.setBackground(Color.white);

		JPanel p2 = new JPanel();
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		p2.setBackground(Color.white);

		JPanel p3 = new JPanel();
		p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		p3.setBackground(Color.white);

		JPanel p4 = new JPanel();
		p4.setLayout(new BoxLayout(p4, BoxLayout.X_AXIS));
		p4.setBackground(Color.white);

		JPanel p5 = new JPanel();
		p5.setLayout(new BoxLayout(p5, BoxLayout.X_AXIS));
		p5.setBackground(Color.white);

		JPanel p6 = new JPanel();
		p6.setLayout(new BoxLayout(p6, BoxLayout.X_AXIS));
		p6.setBackground(Color.white);

		JPanel p7 = new JPanel();
		p7.setLayout(new BoxLayout(p7, BoxLayout.X_AXIS));
		p7.setBackground(Color.white);

		nameDate.add(p1);

		nameDate.add(p2);

		nameDate.add(p3);

		nameDate.add(p4);

		nameDate.add(p5);

		nameDate.add(p6);

		nameDate.add(p7);

		p1.add(firstnamelabel);
		p1.add(Box.createHorizontalGlue());
		p1.add(firstnametext);

		p2.add(lastnamelabel);
		p2.add(Box.createHorizontalGlue());
		p2.add(lastnametext);

		p3.add(email);
		p3.add(Box.createHorizontalGlue());
		p3.add(emailtext);

		p4.add(university);
		p4.add(Box.createHorizontalGlue());
		p4.add(universitytext);

		p5.add(major);
		p5.add(Box.createHorizontalGlue());
		p5.add(majortext);

		p6.add(GPA);
		p6.add(Box.createHorizontalGlue());
		p6.add(GPAtext);

		p7.add(joinDate);
		p7.add(Box.createHorizontalGlue());
		p7.add(joinDatetext);

		nameDate.setMaximumSize(new Dimension(300, 150));
		nameDate.setAlignmentX(LEFT_ALIGNMENT);

		topPanel.add(Box.createHorizontalStrut(40));
		topPanel.add(profilePicture);
		topPanel.add(Box.createHorizontalStrut(10));

		topPanel.add(nameDate);

		editPanel = new JPanel();
		editPanel.setBackground(Color.white);
		editPanel.add(edit);

		topPanel.add(editPanel);

		topPanel.setBackground(Color.WHITE);

		mainArea.add(topPanel);
		mainArea.setBackground(Color.white);

		JPanel bioLabelPanel = new JPanel();
		bioLabelPanel.setPreferredSize(new Dimension(500, 20));
		bioLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 463));
		bioLabelPanel.add(bioLabel);
		bioLabelPanel.setBackground(Color.WHITE);

		mainArea.add(bioLabelPanel);

		JPanel bioPanel = new JPanel();
		bioPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		bioPanel.setBackground(Color.WHITE);
		bioPanel.add(bio);

		mainArea.add(bioPanel);
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		bottomPanel.setPreferredSize(new Dimension(500, 20));
		bottomPanel.add(save);
		bottomPanel.add(newImage);
		bottomPanel.setBackground(Color.white);
		bottomPanel.setVisible(false);

		mainArea.add(bottomPanel);
		add(mainArea);
	}

	/*
	 * public void initComponents() { save = new JButton("Save"); edit = new
	 * JButton("Edit Profile");
	 * 
	 * newImage = new JButton("Upload New Picture");
	 * 
	 * bioLabel = new JLabel("Bio: "); bioLabel.setAlignmentX(LEFT_ALIGNMENT);
	 * 
	 * name = new JTextField("Student Name");
	 * 
	 * name.setEditable(false);
	 * 
	 * joinDate = new JLabel("Join Date: 1/1/2001");
	 * joinDate.setAlignmentX(LEFT_ALIGNMENT);
	 * joinDate.setHorizontalAlignment(SwingConstants.LEFT);
	 * 
	 * university = new JLabel("University: USC");
	 * university.setAlignmentX(LEFT_ALIGNMENT);
	 * university.setHorizontalAlignment(SwingConstants.LEFT);
	 * 
	 * major = new JLabel("Major: Computer Science");
	 * major.setAlignmentX(LEFT_ALIGNMENT);
	 * major.setHorizontalAlignment(SwingConstants.LEFT);
	 * major.setAlignmentX(Component.LEFT_ALIGNMENT);
	 * 
	 * GPA = new JLabel("GPA: 4.0"); GPA.setAlignmentX(LEFT_ALIGNMENT);
	 * GPA.setHorizontalAlignment(SwingConstants.LEFT);
	 * 
	 * bio = new JTextArea("Bio Goes Here"); bio.setPreferredSize(new
	 * Dimension(500, 200)); bio.setBorder(BorderFactory.createEmptyBorder(5, 5,
	 * 5, 5)); bio.setEditable(false); bio.setWrapStyleWord(true);
	 * bio.setLineWrap(true); }
	 * 
	 * public void createGUI() { setSize(500, 470); setMaximumSize(new
	 * Dimension(500, 450)); setMinimumSize(new Dimension(500, 450));
	 * setBackground(Color.WHITE);
	 * 
	 * JPanel mainArea = new JPanel(); mainArea.setLayout(new
	 * BoxLayout(mainArea, BoxLayout.Y_AXIS));
	 * mainArea.setBackground(Color.white);
	 * 
	 * JPanel topPanel = new JPanel(); topPanel.setLayout(new
	 * BoxLayout(topPanel, BoxLayout.X_AXIS));
	 * 
	 * ImageIcon noImg = new ImageIcon("images/noimg.jpg"); Image dum1 =
	 * noImg.getImage().getScaledInstance(150, 150,
	 * java.awt.Image.SCALE_SMOOTH); noImg = new ImageIcon(dum1); profilePicture
	 * = new JLabel(noImg); profilePicture.setPreferredSize(new Dimension(150,
	 * 150));
	 * 
	 * JPanel nameDate = new JPanel();
	 * 
	 * nameDate.setLayout(new GridLayout(5, 1));
	 * nameDate.setBackground(Color.white); nameDate.add(name);
	 * nameDate.add(university); nameDate.add(major); nameDate.add(GPA);
	 * nameDate.add(joinDate); nameDate.setMaximumSize(new Dimension(300, 150));
	 * nameDate.setAlignmentX(LEFT_ALIGNMENT);
	 * 
	 * topPanel.add(Box.createHorizontalStrut(40));
	 * topPanel.add(profilePicture);
	 * topPanel.add(Box.createHorizontalStrut(10));
	 * 
	 * topPanel.add(nameDate);
	 * 
	 * editPanel = new JPanel(); editPanel.setBackground(Color.white);
	 * editPanel.add(edit);
	 * 
	 * topPanel.add(editPanel);
	 * 
	 * topPanel.setBackground(Color.WHITE);
	 * 
	 * mainArea.add(topPanel); mainArea.setBackground(Color.white);
	 * 
	 * JPanel bioLabelPanel = new JPanel(); bioLabelPanel.setPreferredSize(new
	 * Dimension(500, 20));
	 * bioLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 463));
	 * bioLabelPanel.add(bioLabel); bioLabelPanel.setBackground(Color.WHITE);
	 * 
	 * mainArea.add(bioLabelPanel);
	 * 
	 * JPanel bioPanel = new JPanel();
	 * bioPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
	 * bioPanel.setBackground(Color.WHITE); bioPanel.add(bio);
	 * 
	 * mainArea.add(bioPanel);
	 * 
	 * bottomPanel = new JPanel(); bottomPanel.setLayout(new GridLayout(1, 2));
	 * bottomPanel.setPreferredSize(new Dimension(500, 20));
	 * bottomPanel.add(save); bottomPanel.add(newImage);
	 * bottomPanel.setBackground(Color.white); bottomPanel.setVisible(false);
	 * 
	 * mainArea.add(bottomPanel); add(mainArea);
	 * 
	 * }
	 */

	public void addListeners() {
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				editPanel.setVisible(false);
				bio.setEditable(true);
				firstnametext.setEditable(true);
				joinDatetext.setEditable(false);
				universitytext.setEditable(true);
				majortext.setEditable(true);
				GPAtext.setEditable(true);
				emailtext.setEditable(true);
				bottomPanel.setVisible(true);
				// joinDatetext.setBorder(original);
				// universitytext.setBorder(original);
				// majortext.setBorder(original);
				// GPAtext.setBorder(original);
				// emailtext.setBorder(original);
				// firstnametext.setBorder(original);
				// lastnametext.setBorder(original);
				lastnametext.setEditable(true);
			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				editPanel.setVisible(true);
				bio.setEditable(false);
				firstnametext.setEditable(false);
				bottomPanel.setVisible(false);
				joinDatetext.setEditable(false);
				universitytext.setEditable(false);
				majortext.setEditable(false);
				GPAtext.setEditable(false);
				emailtext.setEditable(false);
				joinDatetext.setBorder(null);
				universitytext.setBorder(null);
				majortext.setBorder(null);
				GPAtext.setBorder(null);
				emailtext.setBorder(null);
				firstnametext.setBorder(null);
				lastnametext.setBorder(null);
				lastnametext.setEditable(false);
				Student temp = new Student(client.username);
				temp.setFirstName(firstnametext.getText());
				temp.setLastName(lastnametext.getText());
				temp.setSchool(universitytext.getText());
				temp.setEmail(emailtext.getText());
				temp.setBio(bio.getText());
				temp.setProfilePic(absoluteimagepath);
				temp.setImage((ImageIcon) profilePicture.getIcon());
				temp.setJoinDate(student.getJoinDate());
				temp.setGPA(GPAtext.getText());
				temp.setMajor(majortext.getText());

				client.sendMessage(temp, client.username, "updateStudent");

			}
		});

		newImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser filechooser = new JFileChooser();
				filechooser.setSize(350, 450);
				filechooser.setLocation(350, 150);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".JPG Files", "JPG");
				filechooser.setFileFilter(filter);
				filechooser.setAcceptAllFileFilterUsed(false);

				filechooser.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						filechooser.setSize(350, 450);
						filechooser.setLocation(350, 150);

					}
				});

				int status = filechooser.showOpenDialog(null);

				if (status == JFileChooser.APPROVE_OPTION) {
					File selectedFile = filechooser.getSelectedFile();
					absoluteimagepath = selectedFile.getAbsolutePath();
					System.out.println(absoluteimagepath);
					if ((imagepath != null) && (imagepath.trim().length() != 0)) {
						ImageIcon noImg = new ImageIcon(absoluteimagepath);
						Image dum1 = noImg.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
						noImg = new ImageIcon(dum1);
						profilePicture.setIcon(noImg);
						profilePicture.setPreferredSize(new Dimension(150, 150));
						profilePicture.removeAll();
						profilePicture.revalidate();
						profilePicture.repaint();
					}

					else {
						ImageIcon noImg = new ImageIcon(absoluteimagepath);
						Image dum1 = noImg.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
						noImg = new ImageIcon(dum1);
						profilePicture.setIcon(noImg);
						profilePicture.setPreferredSize(new Dimension(150, 150));
						profilePicture.removeAll();
						profilePicture.revalidate();
						profilePicture.repaint();
					}
				} else if (status == JFileChooser.CANCEL_OPTION) {

				}
			}
		});
	}
}