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
import sun.misc.Queue;
import users.Student;

public class StudentViewJob extends JPanel implements Runnable {
	JScrollPane notificationscroller;
	JPanel mainpanel;
	int numberofjobs;
	Client client;
	Vector<Job> alllocaljobs;
	int counter;
	String username;
	Student student;
	private Vector<Job> myJobs = new Vector<Job>();
	Map<JButton, Job> jobvsbutton = new HashMap<JButton, Job>();
	Queue<Job> myjobsqueue = new Queue<Job>();

	public StudentViewJob(Client client, String username, Student student) {
		this.client = client;
		this.username = username;
		this.student = student;
		alllocaljobs = new Vector<Job>();
		initComponents();
		createGUI();
		addListeners();

	}

	public void initComponents() {

		this.removeAll();
		if ((myJobs != null) && (myJobs.size() != 0)) {
			alllocaljobs = myJobs;
			numberofjobs = alllocaljobs.size();
			GridLayout gridl = new GridLayout(numberofjobs, 1);
			// mainpanel = new JPanel(new GridLayout(numberofnotifiaction,1));
			mainpanel = new JPanel(gridl);
			gridl.setVgap(20);
			for (int i = 0; i < numberofjobs; i++) {
				counter = i;
				myjobsqueue.enqueue(myJobs.elementAt(i));
				JPanel notifpanel = new JPanel(new BorderLayout());
				notifpanel.setBackground(Color.white);
				ImageIcon bluenotif = new ImageIcon("images/job.jpg");
				Image dum = bluenotif.getImage().getScaledInstance(90, 90, java.awt.Image.SCALE_SMOOTH);
				ImageIcon bluenotif1 = new ImageIcon(dum);
				JLabel notiflabel = new JLabel(bluenotif1);
				notiflabel.setIcon(bluenotif1);
				notiflabel.setPreferredSize(new Dimension(50, 50));
				notifpanel.add(notiflabel, BorderLayout.WEST);
				JLabel notifmessage = new JLabel(" " + alllocaljobs.get(counter).getJobTitle());
				notifpanel.add(notifmessage, BorderLayout.CENTER);
				notifpanel.setPreferredSize(new Dimension(400, 60));
				notifpanel.setMaximumSize(new Dimension(400, 60));
				JButton applybutton = new JButton("Apply");
				jobvsbutton.put(applybutton, alllocaljobs.get(counter));
				applybutton.setPreferredSize(new Dimension(65, 90));
				applybutton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JButton tempbtn = (JButton) e.getSource();
						Job tempjob = jobvsbutton.get(tempbtn);
						if (client.getResume() != null) {
							student.setResume(client.getResume());
						}
						StudentJobView studentjobview = new StudentJobView(tempjob, client, username, student);
					}
				});
				notifpanel.add(applybutton, BorderLayout.EAST);
				mainpanel.add(notifpanel);
			}
			notificationscroller = new JScrollPane(mainpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			notificationscroller.setPreferredSize(new Dimension(430, 430));
			notificationscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		} else if ((myJobs == null) || (myJobs.size() == 0)) {
			GridLayout gridl = new GridLayout(1, 1);
			mainpanel = new JPanel(gridl);
			gridl.setVgap(20);
			numberofjobs = 1;
			for (int i = 0; i < numberofjobs; i++) {
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
			notificationscroller = new JScrollPane(mainpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
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
		Vector<Integer> IDs = new Vector<Integer>();
		if (client.getAllJobs() != null) {
			myJobs.clear();

			for (Job j : client.getAllJobs()) {
				if (!IDs.contains(j.getUniqueJobID())) {

					myJobs.add(j);
					IDs.add(j.getUniqueJobID());
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
