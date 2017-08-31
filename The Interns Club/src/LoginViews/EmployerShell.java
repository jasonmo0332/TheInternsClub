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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import client.EmployerPostJob;
import client.EmployerProfile;
import client.jobListPanel;
import networking.Client;
import other.Job;
import users.Employer;
import users.Student;

public class EmployerShell extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2583350049798285947L;
	JPanel main;
	JToolBar Empshell;
	JButton homeButton;
	JButton uploadButton;
	JButton viewButton;
	JButton logoutButton;
	BufferedImage homeImage;
	private EmployerPostJob postJobPanel;
	private jobListPanel viewJobPanel;
	EmployerProfile profilePanel;
	JFrame thisframe;
	BufferedImage testimage = null;

	private String username;
	private Client client;
	private Employer employer;
	private Vector<Job> myJobs;
	private Vector<Integer> distinctJobID;
	private Vector<Job> distinctJobs;
	private Vector<Vector<Student>> studentLists;
	private Vector<Vector<String>> studentRecLetters;
	private CardLayout cl;
	private boolean jobListInUse;

	public EmployerShell(String Username, Client c, Employer e, Vector<Job> j) {
		super("The Interns Club");
		this.username = Username;
		this.client = c;
		this.employer = e;
		this.myJobs = j;
		jobListInUse = false;
		setSize(500, 500);
		setLocation(100, 100);
		setResizable(false);
		initialization();
		actionlisteners();
		Thread t = new Thread(this);
		t.start();
		Thread t2 = new Thread(postJobPanel);
		t2.start();
	}

	@Override
	public void run() {

		try {
			while (true) {
				if (!jobListInUse) {
					viewButton.setEnabled(false);
					// first need to update the jobs
					cl.removeLayoutComponent(viewJobPanel);
					client.clearJobs();
					client.sendMessage("getJobsByUser", username);
					while (!client.hasJobs()) {
						Thread.sleep(1000);
					}

					this.myJobs = client.getAllJobs();
					distinctJobID = new Vector<Integer>();
					distinctJobs = new Vector<Job>();
					// studentLists = new Vector<Vector<Student>>();
					// code for distinct jobs and student lists
					for (Job job : myJobs) {
						
						int currentJobID = job.getUniqueJobID();
						if (!distinctJobID.contains(currentJobID)) {
							//System.out.println("Adding job title: " + (job.getJobTitle()));
							distinctJobID.add(currentJobID);
							distinctJobs.add(job);
						}
					}
					int numOfJobs = distinctJobs.size();
					studentLists = new Vector<Vector<Student>>(numOfJobs);
					Vector<Vector<String>> names = new Vector<Vector<String>>(numOfJobs);
					studentRecLetters = new Vector<Vector<String>>(numOfJobs);
					for (int i = 0; i < numOfJobs; i++) {
						studentLists.add(new Vector<Student>());
						names.add(new Vector<String>());
						studentRecLetters.add(new Vector<String>());
					}

					// names should contain a vector of names for each job
					for (Job job : myJobs) {
						String appliedStudentName = job.getStudUsernames();
						String currentRecLetter = job.getRecLetters();
						if (appliedStudentName != null && !appliedStudentName.equals("null")
								&& !appliedStudentName.equals("")) {
							int uniqueID = job.getUniqueJobID();
							// System.out.println("Applied student name: " +
							// appliedStudentName);
							// System.out.println("Unique Job ID: " + uniqueID);
							for (int i = 0; i < distinctJobs.size(); i++) {
								if (distinctJobID.get(i) == uniqueID) {
									names.get(i).add(appliedStudentName);
									if(currentRecLetter == null || currentRecLetter.equals("null") || currentRecLetter.equals("")){
										studentRecLetters.get(i).add("null");
									}
									
									else{
										studentRecLetters.get(i).add(currentRecLetter);
									}
								}
							}
						}
					}

					// add the students to the list of students
					for (int i = 0; i < numOfJobs; i++) {
						for (String username : names.get(i)) {
							// System.out.println("Getting student with username
							// " + username);
							client.sendMessage("getStudent", username);
							while (client.getStudentSuccess() == -1) {
								Thread.sleep(1000);
							}

							Student toAdd = client.getStudent();
							if (toAdd != null) {
								// System.out.println("Adding student username:
								// " + toAdd.getUsername());
								studentLists.get(i).add(toAdd);
							} else {
								// System.out.println("Server failed to get
								// student with username: " + username);
							}

						}
					}

					viewJobPanel = new jobListPanel(distinctJobs, studentLists, studentRecLetters, client);
					main.add(viewJobPanel, "third");
					viewButton.setEnabled(true);
					Thread.sleep(5000);
				} else {
					Thread.sleep(5000);
				}
			}

		} catch (InterruptedException e) {

		}
	}

	public void initialization() {
		postJobPanel = new EmployerPostJob(employer, client);
		profilePanel = new EmployerProfile(client, employer);

		// info is not complete yet, set to null, set button to not enable
		viewJobPanel = new jobListPanel(null, null, null, client);
		thisframe = this;
		main = new JPanel();
		cl = new CardLayout();
		main.setLayout(cl);
		main.setBackground(Color.white);
		main.add(profilePanel, "first");
		main.add(postJobPanel, "second");
		main.add(viewJobPanel, "third");
		Empshell = new JToolBar();
		
		try {
			testimage = ImageIO.read(this.getClass().getResource("shadowblue.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Empshell = new JToolBar(){
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(testimage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		Empshell.setOpaque(false);
		// Empshell.setBackground(Color.white);
		Empshell.setFloatable(false);
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
				postJobPanel.clearEntries();
				jobListInUse = false;
			}
		});

		ImageIcon uploadimage = new ImageIcon(this.getClass().getResource("upload.png"));
		Image dum1 = uploadimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		uploadimage = new ImageIcon(dum1);
		uploadButton = new JButton(uploadimage);
		uploadButton.setPreferredSize(new Dimension(50, 30));
		uploadButton.setContentAreaFilled(false);
		uploadButton.setBorderPainted(false);
		uploadButton.setToolTipText("Upload Job");
		uploadButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				cl.show(main, "second");
				postJobPanel.clearEntries();
				jobListInUse = false;
			}
		});
		ImageIcon viewimage = new ImageIcon(this.getClass().getResource("view.png"));
		Image dum2 = viewimage.getImage().getScaledInstance(50, 30, java.awt.Image.SCALE_SMOOTH);
		viewimage = new ImageIcon(dum2);
		viewButton = new JButton(viewimage);
		viewButton.setPreferredSize(new Dimension(50, 30));
		viewButton.setContentAreaFilled(false);
		viewButton.setBorderPainted(false);
		viewButton.setToolTipText("View Jobs");
		viewButton.setEnabled(false);
		viewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (myJobs == null || myJobs.size() == 0) {
					JOptionPane.showMessageDialog(null,
							"You haven't created any jobs yet so you can't view your jobs yet!");
				} else {
					cl.show(main, "third");
					jobListInUse = true;
				}
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
				// client.close();
				// ClientGui clientgui = new ClientGui();
			}
		});
		CreateGui();
	}

	public void CreateGui() {
		Empshell.add(homeButton);
		// Empshell.add(Box.createHorizontalStrut(10));
		Empshell.add(uploadButton);
		// Empshell.add(Box.createHorizontalStrut(10));
		Empshell.add(viewButton);
		Empshell.add(Box.createGlue());
		Empshell.add(logoutButton);
		// main.add(jobpanel);
		// main.add(profilePanel);
		add(Empshell, BorderLayout.NORTH);
		add(main, BorderLayout.CENTER);
		setVisible(true);
	}

	public void actionlisteners() {

	}

}
