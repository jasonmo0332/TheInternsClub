package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import networking.Client;
import other.Job;

public class GuestStudentViewJob extends JPanel implements Runnable {
	JScrollPane notificationscroller;
	JPanel mainpanel;
	int numberofnotifiaction;
	Client client;
	Vector<Job> alllocaljobs;
	int counter;
	private Vector<Job> myJobs = new Vector<Job>();

	public GuestStudentViewJob(Client client) {
		alllocaljobs = new Vector<Job>();
		this.client = client;
		initComponents();
		createGUI();
		addListeners();
	}

	public void initComponents() {
		// numberofnotifiaction = 10;
		// GridLayout gridl = new GridLayout(10,1);
		this.removeAll();
		if ((myJobs != null) && (myJobs.size() != 0)) {
			alllocaljobs = myJobs;
			numberofnotifiaction = alllocaljobs.size();
			GridLayout gridl = new GridLayout(numberofnotifiaction, 1);
			// mainpanel = new JPanel(new GridLayout(numberofnotifiaction,1));
			mainpanel = new JPanel(gridl);
			gridl.setVgap(20);
			for (int i = 0; i < numberofnotifiaction; i++) {
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
				JLabel notifmessage = new JLabel("  " + alllocaljobs.get(counter).getJobTitle());
				notifpanel.add(notifmessage, BorderLayout.CENTER);
				notifpanel.setPreferredSize(new Dimension(400, 60));
				mainpanel.add(notifpanel);
			}
			notificationscroller = new JScrollPane(mainpanel);
			notificationscroller.setPreferredSize(new Dimension(430, 430));
			notificationscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		} else if ((myJobs == null) || (myJobs.size() == 0)) {
			GridLayout gridl = new GridLayout(1, 1);
			mainpanel = new JPanel(gridl);
			gridl.setVgap(20);
			numberofnotifiaction = 1;
			for (int i = 0; i < numberofnotifiaction; i++) {
				JPanel notifpanel = new JPanel(new BorderLayout());
				notifpanel.setBackground(Color.white);
				ImageIcon bluenotif = new ImageIcon("images/job.jpg");
				Image dum = bluenotif.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				ImageIcon bluenotif1 = new ImageIcon(dum);
				JLabel notiflabel = new JLabel(bluenotif1);
				notiflabel.setIcon(bluenotif1);
				notiflabel.setPreferredSize(new Dimension(50, 50));
				notifpanel.add(notiflabel, BorderLayout.WEST);
				JLabel notifmessage = new JLabel("  There is no job at this time");
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

	private void checkJobsNotifs() {
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
