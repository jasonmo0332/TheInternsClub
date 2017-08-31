package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import networking.Client;
import networking.Notification;

public class NotificationPanel extends JPanel implements Runnable {
	JScrollPane notificationscroller;
	JPanel mainpanel;
	int numberofnotifiaction;
	Client client;
	Vector<Notification> notifications;

	public NotificationPanel(Client client) {
		notifications = new Vector<Notification>();
		this.client = client;
		initComponents();
		createGUI();
		addListeners();
	}

	public void initComponents() {
		if ((client.getNotifications() != null) && (client.getNotifications().size() != 0)) {
			notifications = client.getNotifications();
			numberofnotifiaction = notifications.size();
			// GridLayout gridl = new GridLayout(10,1);
			GridLayout gridl = new GridLayout(numberofnotifiaction, 1);
			// mainpanel = new JPanel(new GridLayout(numberofnotifiaction,1));
			mainpanel = new JPanel(gridl);
			gridl.setVgap(20);
			for (int i = 0; i < numberofnotifiaction; i++) {
				JPanel notifpanel = new JPanel(new BorderLayout());
				notifpanel.setBackground(Color.white);
				ImageIcon bluenotif = new ImageIcon("images/bluenotification.jpg");
				Image dum = bluenotif.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				ImageIcon bluenotif1 = new ImageIcon(dum);
				JLabel notiflabel = new JLabel(bluenotif1);
				notiflabel.setIcon(bluenotif1);
				notiflabel.setPreferredSize(new Dimension(50, 50));
				notifpanel.add(notiflabel, BorderLayout.WEST);
				
				JLabel notifmessage = new JLabel("  " + notifications.get(i).getMessage());
				notifpanel.add(notifmessage, BorderLayout.CENTER);
				notifpanel.setPreferredSize(new Dimension(420, 60));
				mainpanel.add(notifpanel);
				
			}
			notificationscroller = new JScrollPane(mainpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			notificationscroller.setPreferredSize(new Dimension(430, 430));
			notificationscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		} else if ((client.getAllJobs() == null)
				|| (client.getNotifications() != null) && (client.getNotifications().size() == 0)) {
			GridLayout gridl = new GridLayout(1, 1);
			mainpanel = new JPanel(gridl);
			gridl.setVgap(20);
			numberofnotifiaction = 1;
			for (int i = 0; i < numberofnotifiaction; i++) {
				JPanel notifpanel = new JPanel(new BorderLayout());
				notifpanel.setBackground(Color.white);
				ImageIcon bluenotif = new ImageIcon("images/bluenotification.jpg");
				Image dum = bluenotif.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
				ImageIcon bluenotif1 = new ImageIcon(dum);
				JLabel notiflabel = new JLabel(bluenotif1);
				notiflabel.setIcon(bluenotif1);
				notiflabel.setPreferredSize(new Dimension(50, 50));
				notifpanel.add(notiflabel, BorderLayout.WEST);
				JLabel notifmessage = new JLabel("There are no notifications at this time!");
				notifpanel.add(notifmessage, BorderLayout.CENTER);
				notifpanel.setPreferredSize(new Dimension(420, 60));
				mainpanel.add(notifpanel);
			}
			notificationscroller = new JScrollPane(mainpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			notificationscroller.setPreferredSize(new Dimension(430, 430));
			notificationscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		} else {
			notificationscroller = new JScrollPane(mainpanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			notificationscroller.setPreferredSize(new Dimension(430, 430));
			notificationscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		}
	}

	public void createGUI() {
		add(notificationscroller, BorderLayout.CENTER);
	}

	public void addListeners() {

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {

			client.checkForUpdates();
			boolean read = false;
			if (client.getNotifications() != null) {
				Vector<Notification> notifs = client.getNotifications();
				for (Notification n : notifs) {
					if (!n.isHasRead()) {
						read = true;
						break;
					}
				}

				if (read) {
					this.removeAll();
					initComponents();
					createGUI();
					addListeners();
					this.revalidate();
					this.repaint();
				}
			}
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}
}