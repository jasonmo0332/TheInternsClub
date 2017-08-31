package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import networking.Client;
import users.Professor;
import users.Student;

public class ProfessorProfile extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton save, edit, newImage;
	private JLabel profilePicture, bioLabel, joinDate, university, email, officePhone, fName, lName;
	private JTextField fNameField, lNameField, universityField, emailField, officePhoneField;
	private JTextArea bio;
	private JPanel bottomPanel, editPanel;
	private Professor professor;
	private String imagePath = null;
	Client client;
	String username;
	String absoluteImagePath;
	String imagepath;
	public ProfessorProfile(String username, Professor professor, Client client) {
		this.professor = professor;
		this.username = username;
		this.client = client;
		initComponents();
		createGUI();
		addListeners();
	}

	public void initComponents() {
		save = new JButton("Save");
		edit = new JButton("Edit Profile");

		newImage = new JButton("Upload New Picture");

		bioLabel = new JLabel("Bio: ");
		bioLabel.setAlignmentX(LEFT_ALIGNMENT);

		fName = new JLabel("First Name: ");
		fName.setAlignmentX(LEFT_ALIGNMENT);
		fName.setHorizontalAlignment(SwingConstants.LEFT);
		lName = new JLabel("Last Name: ");
		lName.setAlignmentX(LEFT_ALIGNMENT);
		lName.setHorizontalAlignment(SwingConstants.LEFT);
		fNameField = new JTextField();
		// name.setPreferredSize(new Dimension(150, 40));
		// name.setMinimumSize(new Dimension(150, 40));
		// name.setMaximumSize(new Dimension(150, 40));
		fNameField.setBorder(null);
		fNameField.setEditable(false);
		fNameField.setText(professor.getFirstName());
		lNameField = new JTextField();
		lNameField.setBorder(null);
		lNameField.setEditable(false);
		lNameField.setText(professor.getLastName());

		joinDate = new JLabel("Join Date: " + professor.getJoinDate());
		joinDate.setAlignmentX(LEFT_ALIGNMENT);
		joinDate.setHorizontalAlignment(SwingConstants.LEFT);

		
		university = new JLabel("University: ");
		university.setAlignmentX(LEFT_ALIGNMENT);
		university.setHorizontalAlignment(SwingConstants.LEFT);
		universityField = new JTextField(professor.getSchool());
		universityField.setBorder(null);
		universityField.setEditable(false);

		email = new JLabel("Email: ");
		email.setAlignmentX(LEFT_ALIGNMENT);
		email.setHorizontalAlignment(SwingConstants.LEFT);
		emailField = new JTextField(professor.getEmail());
		emailField.setBorder(null);
		emailField.setEditable(false);

		bio = new JTextArea();
		bio.setPreferredSize(new Dimension(500, 200));
		bio.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		bio.setEditable(false);
		bio.setWrapStyleWord(true);
		bio.setLineWrap(true);
		bio.setText(professor.getBio());

		officePhone = new JLabel("Phone Number: ");
		officePhone.setAlignmentX(LEFT_ALIGNMENT);
		officePhone.setHorizontalAlignment(SwingConstants.LEFT);
		officePhoneField = new JTextField(professor.getPhone());
		officePhoneField.setBorder(null);
		officePhoneField.setEditable(false);
		
	}

	public void createGUI() {

		setSize(500, 500);
		setMaximumSize(new Dimension(500, 450));
		setMinimumSize(new Dimension(500, 450));
		setBackground(Color.WHITE);

		JPanel mainArea = new JPanel();
		mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
		mainArea.setBackground(Color.white);

		JPanel topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		if (professor.getImage() != null) {
			profilePicture = new JLabel(professor.getImage());
			ImageIcon noImg = professor.getImage();
			Image dum1 = noImg.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
			noImg = new ImageIcon(dum1);
			profilePicture = new JLabel(noImg);
			profilePicture.setPreferredSize(new Dimension(150, 150));
		} else {
			imagePath = "images/noImg.jpg";
			ImageIcon noImg = new ImageIcon(imagePath);
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

		p1.add(fName);
		p1.add(Box.createHorizontalGlue());
		p1.add(fNameField);
		p2.add(lName);
		p2.add(Box.createHorizontalGlue());
		p2.add(lNameField);
		p3.add(university);
		p3.add(Box.createHorizontalGlue());
		p3.add(universityField);
		p4.add(email);
		p4.add(Box.createHorizontalGlue());
		p4.add(emailField);
		p5.add(officePhone);
		p5.add(Box.createHorizontalGlue());
		p5.add(officePhoneField);
		p6.add(joinDate);
		

		nameDate.setMaximumSize(new Dimension(300, 150));
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

	public void addListeners() {
		edit.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				editPanel.setVisible(false);
				bio.setEditable(true);
				fNameField.setEditable(true);
				lNameField.setEditable(true);
				emailField.setEditable(true);
				officePhoneField.setEditable(true);
				universityField.setEditable(true);
				bottomPanel.setVisible(true);
			}
		});

		save.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent evt) {
				editPanel.setVisible(true);
				bio.setEditable(false);
				fNameField.setEditable(false);
				lNameField.setEditable(false);
				emailField.setEditable(false);
				officePhoneField.setEditable(false);
				universityField.setEditable(false);
				bottomPanel.setVisible(false);
				Professor temp = new Professor(client.username);
				temp.setFirstName(fNameField.getText());
				temp.setLastName(lNameField.getText());
				temp.setSchool(universityField.getText());
				temp.setEmail(emailField.getText());
				temp.setPhone(officePhoneField.getText());
				temp.setBio(bio.getText());
				temp.setProfilePic(absoluteImagePath);
				temp.setImage((ImageIcon) profilePicture.getIcon());
				temp.setJoinDate(professor.getJoinDate());

				client.sendMessage(temp, client.username, "updateProfessor");

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
					absoluteImagePath = selectedFile.getAbsolutePath();
					if ((imagepath != null) && (imagepath.trim().length() != 0)) {
						ImageIcon noImg = new ImageIcon(absoluteImagePath);
						Image dum1 = noImg.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
						noImg = new ImageIcon(dum1);
						profilePicture.setIcon(noImg);
						profilePicture.setPreferredSize(new Dimension(150, 150));
						profilePicture.removeAll();
						profilePicture.revalidate();
						profilePicture.repaint();
					}

					else {
						ImageIcon noImg = new ImageIcon(absoluteImagePath);
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
