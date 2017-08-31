package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import networking.Client;
import other.Job;
import users.Professor;

public class ProfessorViewJob extends JPanel implements Runnable {
	JScrollPane notificationscroller;
	JPanel mainpanel;
	int numberofnotification;
	private Client client;
	private Vector<Job> jobs;
	private Vector<Job> myJobs = new Vector<Job>();
	Map<JButton, Job> jobvsbutton = new HashMap<JButton, Job>();
	Vector<Job> alllocaljobs;
	int counter;
	String username;
	Professor professor;

	public ProfessorViewJob(Client client, String username, Professor professor) {
		initComponents();
		createGUI();
		addListeners();

		alllocaljobs = new Vector<Job>();
		client.sendMessage("getJobs", "null");
		while (client.getAllJobs() == null) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.out.println("e :" + e.getMessage());
			}
		}
		jobs = client.getAllJobs();

		this.client = client;
		this.professor = professor;
		this.username = username;
	}

	public void initComponents() {
		// numberofnotification = 10;
		// GridLayout gridl = new GridLayout(10,1);
		this.removeAll();
		if ((myJobs != null) && (myJobs.size() != 0)) {
			alllocaljobs = myJobs;
			numberofnotification = alllocaljobs.size();
			GridLayout gridl = new GridLayout(numberofnotification, 1);
			mainpanel = new JPanel(gridl);
			gridl.setVgap(20);
			// for(Job j : jobs){
			for (int i = 0; i < numberofnotification; i++) {
				counter = i;
				JPanel notifpanel = new JPanel(new BorderLayout());
				notifpanel.setBackground(Color.white);
				ImageIcon bluenotif = new ImageIcon("images/job.jpg");
				Image dum = bluenotif.getImage().getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
				ImageIcon bluenotif1 = new ImageIcon(dum);
				JLabel notiflabel = new JLabel(bluenotif1);
				notiflabel.setIcon(bluenotif1);
				notiflabel.setPreferredSize(new Dimension(50, 50));
				notifpanel.add(notiflabel, BorderLayout.WEST);
				// JLabel notifmessage = new JLabel(j.getJobTitle());
				JLabel notifmessage = new JLabel(" " + alllocaljobs.get(counter).getJobTitle());
				notifpanel.add(notifmessage, BorderLayout.CENTER);
				notifpanel.setPreferredSize(new Dimension(400, 60));
				JButton recommend = new JButton("Recommend");
				recommend.setPreferredSize(new Dimension(90, 90));
				jobvsbutton.put(recommend, alllocaljobs.get(counter));
				recommend.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						// ProfessorRecommendPanel prp = new
						// ProfessorRecommendPanel(j.getJobTitle(),
						// j.getDescription(), client, j.getUniqueJobID());
						JButton tempbtn = (JButton) e.getSource();
						Job tempjob = jobvsbutton.get(tempbtn);
						ProfessorRecommendPanel prp = new ProfessorRecommendPanel(tempjob, client, username, professor);
					}
				});
				notifpanel.add(recommend, BorderLayout.EAST);
				mainpanel.add(notifpanel);
			}
			notificationscroller = new JScrollPane(mainpanel);
			notificationscroller.setPreferredSize(new Dimension(430, 430));
			notificationscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		} else if ((myJobs == null) || (myJobs.size() == 0)) {
			GridLayout gridl = new GridLayout(1, 1);
			mainpanel = new JPanel(gridl);
			gridl.setVgap(20);
			numberofnotification = 1;
			for (int i = 0; i < numberofnotification; i++) {
				JPanel notifpanel = new JPanel(new BorderLayout());
				notifpanel.setBackground(Color.white);
				ImageIcon bluenotif = new ImageIcon("images/job.jpg");
				Image dum = bluenotif.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				ImageIcon bluenotif1 = new ImageIcon(dum);
				JLabel notiflabel = new JLabel(bluenotif1);
				notiflabel.setIcon(bluenotif1);
				notiflabel.setPreferredSize(new Dimension(50, 50));
				notifpanel.add(notiflabel, BorderLayout.WEST);
				JLabel notifmessage = new JLabel("  There are no jobs at this time!");
				notifpanel.add(notifmessage, BorderLayout.CENTER);
				notifpanel.setPreferredSize(new Dimension(420, 60));
				mainpanel.add(notifpanel);
			}
			notificationscroller = new JScrollPane(mainpanel);
			notificationscroller.setPreferredSize(new Dimension(430, 430));
			notificationscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}
		this.validate();
		this.repaint();
	}

	public void createGUI() {
		add(notificationscroller, BorderLayout.CENTER);
	}

	public void addListeners() {

	}

	private synchronized void checkJobsNotifs() {
		int alljobsarethesame = 0;

		if (client.getAllJobs() != null) {
			myJobs.clear();

			HashMap hm = new HashMap(client.getAllJobs().size());
			for (Job j : client.getAllJobs()) {
				if (hm.containsKey(j.getUniqueJobID())) {
					continue;
				} else {
					myJobs.add(j);
					hm.put(j.getUniqueJobID(), j);
				}
			}
			for (Job n : myJobs) {
				for (Job n2 : alllocaljobs) {
					if (n.getUniqueJobID() == n2.getUniqueJobID()) {
						alljobsarethesame = 0;
					} else {
						alljobsarethesame = 1;
					}
				}
			}

			this.remove(0);
			initComponents();
			createGUI();
			addListeners();
			this.revalidate();
			this.repaint();
		}
		client.clearJobs();
	}

	@Override
	public void run() {
		while (true) {
			client.checkForUpdates();
			checkJobsNotifs();

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}

		}
	}
}
