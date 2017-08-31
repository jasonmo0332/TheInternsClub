package client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import networking.Client;
import other.Job;
import users.Student;

public class StudentJobView extends JFrame {
	JLabel namethepostiton;
	JLabel jobdescription;
	JTextField jobnamefield;
	JTextArea jobdescriptionarea;
	JScrollPane jobdescriptionpane;
	JButton apply;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	JPanel panel5;
	JPanel mainpanel;
	String jobtitle = "";
	String details = "";
	Job thisjob;
	Client client;
	String username;
	Student student;
	JFrame thisframe;

	public StudentJobView(Job job, Client client, String username, Student student) {
		super("The Interns Club");
		this.thisjob = job;
		this.username = username;
		thisframe = this;
		this.client = client;
		this.student = student;
		setSize(500, 500);
		setLocation(200, 200);
		this.jobtitle = job.getJobTitle();
		this.details = job.getDescription();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		initialization();
		createGui();
		actionlisteners();
	}

	/*
	 * public StudentJobView(){ super("The Interns Club"); setSize(500, 500);
	 * setLocation(200, 200); this.setDefaultCloseOperation(HIDE_ON_CLOSE);
	 * initialization(); createGui(); actionlisteners(); }
	 */
	public void initialization() {
		jobtitle = thisjob.getJobTitle();
		details = thisjob.getDescription();

		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		mainpanel = new JPanel();
		mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));

		namethepostiton = new JLabel("Name of Position", SwingConstants.LEFT);

		jobdescription = new JLabel("Job Descrioption", SwingConstants.RIGHT);
		jobdescription.setAlignmentX(Component.RIGHT_ALIGNMENT);

		jobnamefield = new JTextField(jobtitle);
		jobnamefield.setEditable(false);
		jobnamefield.setPreferredSize(new Dimension(400, 43));
		jobnamefield.setMaximumSize(new Dimension(400, 43));
		jobnamefield.setMinimumSize(new Dimension(400, 43));
		jobnamefield.setAlignmentX(Component.CENTER_ALIGNMENT);

		jobdescriptionarea = new JTextArea(details);
		jobdescriptionarea.setEditable(false);
		jobdescriptionarea.setAlignmentX(Component.CENTER_ALIGNMENT);

		jobdescriptionpane = new JScrollPane(jobdescriptionarea);
		jobdescriptionpane.setPreferredSize(new Dimension(400, 200));
		jobdescriptionpane.setMaximumSize(new Dimension(400, 200));
		jobdescriptionpane.setMinimumSize(new Dimension(400, 200));
		jobdescriptionpane.setAlignmentX(Component.CENTER_ALIGNMENT);
		apply = new JButton("Apply");

		if (student.getResume().trim().length() == 0) {
			apply.setEnabled(false);
			JFrame errorframe = new JFrame();
			JOptionPane.showMessageDialog(errorframe, "Sorry you need to have a resume to be able to apply!", "error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			apply.setEnabled(true);
		}

		apply.setPreferredSize(new Dimension(160, 60));
		apply.setMaximumSize(new Dimension(160, 60));
		apply.setMinimumSize(new Dimension(160, 60));
		apply.setAlignmentX(Component.LEFT_ALIGNMENT);
	}

	public void createGui() {
		add(mainpanel);

		mainpanel.add(panel1);
		panel1.add(namethepostiton);

		mainpanel.add(panel2);
		jobnamefield.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel2.add(jobnamefield);
		mainpanel.add(Box.createVerticalStrut(35));

		mainpanel.add(panel3);
		jobdescription.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel3.add(jobdescription);

		mainpanel.add(panel4);
		jobdescriptionpane.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel4.add(jobdescriptionpane);
		mainpanel.add(Box.createGlue());

		mainpanel.add(panel5);
		apply.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel5.add(apply);
		this.setVisible(true);
	}

	public void actionlisteners() {
		apply.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				client.sendMessage("apply", thisjob, username);
				thisframe.dispose();
			}

		});
	}
}
