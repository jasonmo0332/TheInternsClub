package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import networking.Client;
import other.Job;
import users.Student;

public class jobDetailsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6833485226695180133L;
	private Job currentJob;
	private String jobTitle, jobDetails;
	private Vector<Student> listOfAppliedStudent;
	private Vector<String> listOfAppliedStudentUsername;
	private Vector<String> studentLetters;
	private Client client;
	private jobListPanel jlp;
	private JComboBox<String> appliedStudents;
	private JTextArea studentBio,jobDescription;
	private JButton backButton, downloadButton, acceptButton, rejectButton, letterButton;;
	private JLabel  studentEmail, studentPhone;
	private ImageIcon studentImage;
	private JPanel leftPanel, rightPanel;
	private JLabel imagePane;
	private JLabel fullName;

	public jobDetailsPanel(jobListPanel jlp, Job job, Vector<Student> s, Vector<String> letters, Client c) {
		this.currentJob = job;
		this.listOfAppliedStudent = s;
		this.listOfAppliedStudentUsername = new Vector<String>(s.size());
		this.studentLetters = letters;
		for (Student student : s) {
			listOfAppliedStudentUsername.add(student.getUsername());
		}
		this.jlp = jlp;
		this.client = c;
		initializeVar();
		createGUI();
		addEvents();
	}

	public boolean hasApplyingStudents() {
		return listOfAppliedStudent.size() != 0;
	}

	private void initializeVar() {
		jobTitle = currentJob.getJobTitle();
		jobDetails = currentJob.getDescription();

		appliedStudents = new JComboBox<String>();
		appliedStudents.setBackground(Color.WHITE);
		appliedStudents.addItem("--Please Select A Student--");
		for (int i = 0; i < listOfAppliedStudent.size(); i++) {
			String s = listOfAppliedStudentUsername.get(i);
			appliedStudents.addItem(s);

		}
		jobDescription = new JTextArea();
		jobDescription.setLineWrap(true);
		jobDescription.setWrapStyleWord(true);
		jobDescription.setText(jobTitle + ": " + jobDetails);
		jobDescription.setEditable(false);
		jobDescription.setFont(new Font("Times New Roman", Font.BOLD, 16));

		studentBio = new JTextArea();
		studentBio.setLineWrap(true);
		studentBio.setWrapStyleWord(true);
		studentBio.setBackground(Color.white);
		studentEmail = new JLabel();
		studentPhone = new JLabel();
		imagePane = new JLabel();

		backButton = new JButton("Back to Job List");
		downloadButton = new JButton("Download Resume");
		letterButton = new JButton("Download Letter ");
		// downloadButton.setVisible(false);
		acceptButton = new JButton("Accept");
		// acceptButton.setVisible(false);
		rejectButton = new JButton("Reject");
		// rejectButton.setVisible(false);
		if (!currentJob.isAvailable()) {
			downloadButton.setEnabled(false);
			acceptButton.setEnabled(false);
			rejectButton.setEnabled(false);
			letterButton.setEnabled(false);
		}
	}

	private void createGUI() {
		setSize(new Dimension(500, 470));
		setMaximumSize(new Dimension(500, 450));
		setMinimumSize(new Dimension(500, 450));
		setBackground(Color.WHITE);
		setVisible(true);
		setLayout(new GridLayout(1, 2));
		leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JPanel backButtonBox = new JPanel();
		backButtonBox.setBackground(Color.WHITE);
		backButtonBox.setLayout(new GridLayout(1, 1));
		backButtonBox.add(backButton);
		leftPanel.add(backButtonBox);
		leftPanel.add(Box.createGlue());
		JPanel comboBoxPanel = new JPanel();
		comboBoxPanel.setBackground(Color.white);
		comboBoxPanel.add(appliedStudents);
		leftPanel.add(comboBoxPanel);
		leftPanel.add(Box.createGlue());
		leftPanel.add(jobDescription);
		leftPanel.add(Box.createGlue());

		rightPanel = new JPanel();
		rightPanel.setBackground(Color.WHITE);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		// JLabel imagePane = new JLabel(new
		// ImageIcon(studentImage.getScaledInstance(200, 200,
		// Image.SCALE_DEFAULT)));
		rightPanel.add(imagePane);

		JPanel namePanel = new JPanel();
		namePanel.setBackground(Color.white);
		namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.X_AXIS));
		namePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel nameLabel = new JLabel("Student's full name: ");
		nameLabel.setBackground(Color.WHITE);
		fullName = new JLabel();
		namePanel.add(nameLabel);
		namePanel.add(fullName);
		rightPanel.add(namePanel);
		
		JPanel temp = new JPanel();
		temp.setBackground(Color.white);
		temp.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel bio = new JLabel("Bio: ");
		temp.add(bio);
		
		JPanel bioPanel = new JPanel();
		bioPanel.setBackground(Color.WHITE);
		bioPanel.setLayout(new BoxLayout(bioPanel, BoxLayout.X_AXIS));
		bioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		bio.setAlignmentX(Component.LEFT_ALIGNMENT);
		bio.setBackground(Color.WHITE);
		//bioPanel.add(bio);
		JScrollPane bioScrollPane = new JScrollPane(studentBio);
		bioPanel.add(bioScrollPane);
		//rightPanel.add(bioPanel);

		rightPanel.add(Box.createGlue());

		JPanel emailPanel = new JPanel();
		emailPanel.setBackground(Color.WHITE);
		emailPanel.setLayout(new BoxLayout(emailPanel, BoxLayout.X_AXIS));
		emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel email = new JLabel("Email: ");
		email.setBackground(Color.WHITE);
		emailPanel.add(email);
		emailPanel.add(studentEmail);
		rightPanel.add(emailPanel);

		rightPanel.add(Box.createGlue());

		JPanel phonePanel = new JPanel();
		phonePanel.setBackground(Color.WHITE);
		phonePanel.setLayout(new BoxLayout(phonePanel, BoxLayout.X_AXIS));
		phonePanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		JLabel phone = new JLabel("GPA: ");
		phone.setBackground(Color.WHITE);
		phonePanel.add(phone);
		phonePanel.add(studentPhone);
		rightPanel.add(phonePanel);

		rightPanel.add(Box.createGlue());
		rightPanel.add(temp);
		rightPanel.add(bioPanel);

		JPanel buttonBox1 = new JPanel();
		buttonBox1.setBackground(Color.WHITE);
		buttonBox1.setLayout(new GridLayout(1, 2));
		buttonBox1.add(acceptButton);
		// buttonBox1.add(Box.createGlue());
		// buttonBox.add(downloadButton);
		// buttonBox.add(Box.createGlue());
		buttonBox1.add(rejectButton);

		JPanel buttonBox2 = new JPanel();
		buttonBox2.setBackground(Color.WHITE);
		buttonBox2.setLayout(new BoxLayout(buttonBox2, BoxLayout.X_AXIS));
		buttonBox2.add(Box.createGlue());
		buttonBox2.add(downloadButton);
		buttonBox2.add(Box.createGlue());
		rightPanel.add(buttonBox2);

		JPanel buttonBox3 = new JPanel();
		buttonBox3.setBackground(Color.WHITE);
		buttonBox3.setLayout(new BoxLayout(buttonBox3, BoxLayout.X_AXIS));
		buttonBox3.add(Box.createGlue());
		buttonBox3.add(letterButton);
		buttonBox3.add(Box.createGlue());
		rightPanel.add(buttonBox3);
		rightPanel.add(buttonBox1);

		add(leftPanel);
		add(rightPanel);
		leftPanel.setVisible(true);
		rightPanel.setVisible(false);

	}

	private void addEvents() {
		appliedStudents.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String username = (String) appliedStudents.getSelectedItem();
				if (username.equals("--Please Select A Student--")) {
					rightPanel.setVisible(false);
				}

				else {
					Student selectedStudent = null;
					for (Student s : listOfAppliedStudent) {
						if (s.getUsername().equals(username)) {
							selectedStudent = s;
						}
					}

					if (selectedStudent == null) {
					} else {
						studentBio.setText(selectedStudent.getBio());
						studentEmail.setText(selectedStudent.getEmail());
						studentPhone.setText(selectedStudent.getGPA());
						studentImage = selectedStudent.getImage();
						imagePane = new JLabel();
						imagePane.setIcon(studentImage);
						fullName.setText(selectedStudent.getFirstName() + " " + selectedStudent.getLastName());
						rightPanel.revalidate();
						rightPanel.repaint();
						rightPanel.setVisible(true);
					}
				}
			}

		});

		downloadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String username = (String) appliedStudents.getSelectedItem();
				Student selectedStudent = null;
				for (Student s : listOfAppliedStudent) {
					if (s.getUsername().equals(username)) {
						selectedStudent = s;
					}
				}

				if (selectedStudent == null) {
				} else {
					new ResumePanel(selectedStudent).setVisible(true);
				}

			}

		});

		letterButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				String username = (String) appliedStudents.getSelectedItem();
				int index = -1;
				for (int i = 0; i < listOfAppliedStudentUsername.size(); i++) {
					if (listOfAppliedStudentUsername.get(i).equals(username)) {
						index = i;
					}
				}
				String letter = studentLetters.get(index);
				Student selectedStudent = null;
				for (Student s : listOfAppliedStudent) {
					if (s.getUsername().equals(username)) {
						selectedStudent = s;
					}
				}

				if (selectedStudent == null) {
					// System.out.println("Failed to select student.");
				} else {
					if (letter == null || letter.equals("null") || letter.equals("")) {
						JOptionPane.showMessageDialog(null, "The student doesn't have a recommendation letter.");
					} else {
						new LetterPanel(selectedStudent, letter).setVisible(true);
					}
				}

			}

		});

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rightPanel.setVisible(false);
				appliedStudents.setSelectedIndex(0);
				jlp.showJobList();

			}

		});

		acceptButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String selectedStudentUsername = (String) appliedStudents.getSelectedItem();
				client.sendAcceptedStudent(currentJob, selectedStudentUsername, listOfAppliedStudent);
				acceptButton.setEnabled(false);
				rejectButton.setEnabled(false);
				downloadButton.setEnabled(false);
				letterButton.setEnabled(false);
			}

		});

		rejectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				rightPanel.setVisible(false);
				String selectedStudentUsername = (String) appliedStudents.getSelectedItem();
				client.sendMessage("rejectStudent", currentJob, selectedStudentUsername);
				appliedStudents.removeItem(selectedStudentUsername);

			}

		});
	}
}
