package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import networking.Client;
import other.Constants;
import other.Job;
import users.Employer;

public class EmployerPostJob extends JPanel implements Runnable{

	private Job job;
	private Employer employer;
	private JTextField enterJobTitle;
	private JLabel employerCompany;
	private JTextArea enterJobDescription;
	private JTextField enterUniqueJobID;
	private JButton submitJobButton;
	private Client client;
	
	
	public EmployerPostJob(Employer e, Client c){
		this.employer = e;
		this.client = c;
		initializeVar();
		createGUI();
		addListeners();
	}
	
	private void initializeVar(){
		enterJobTitle = new JTextField();
		enterJobTitle.setBackground(Color.white);
		employerCompany = new JLabel();
		employerCompany.setText(employer.getCompany());
		employerCompany.setBackground(Color.white);
		employerCompany.setFont(new Font("Times New Roman", Font.BOLD,18));
		employerCompany.setAlignmentX(LEFT_ALIGNMENT);
		enterUniqueJobID = new JTextField();
		enterUniqueJobID.setBackground(Color.white);
		enterJobDescription = new JTextArea();
		enterJobDescription.setLineWrap(true);
		enterJobDescription.setWrapStyleWord(true);
		submitJobButton = new JButton("Submit Job");
		submitJobButton.setFont(new Font("Times New Roman", Font.BOLD,24));
	}
	
	public void clearEntries(){
		enterJobTitle.setText("");
		enterUniqueJobID.setText("");
		enterJobDescription.setText("");
	}
	
	private void createGUI(){
		setSize(new Dimension(1200,600));
		setVisible(true);
		setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BoxLayout(northPanel,BoxLayout.Y_AXIS));
		northPanel.setBackground(Color.white);
		
		JPanel firstPanel = new JPanel();
		JLabel instructions = new JLabel();
		firstPanel.setBackground(Color.white);
		instructions.setText("Please enter the information required to create a new job.");
		instructions.setBackground(Color.white);
		instructions.setFont(new Font("Times New Roman", Font.BOLD,12));
		instructions.setAlignmentX(CENTER_ALIGNMENT);
		firstPanel.add(instructions);
		
		northPanel.add(firstPanel);
		northPanel.add(Box.createGlue());
		
		JPanel titlePanel = new JPanel();
		titlePanel.setLayout(new BoxLayout(titlePanel,BoxLayout.X_AXIS));
		titlePanel.setBackground(Color.white);
		JLabel title = new JLabel();
		title.setText("Job Title:                     ");
		title.setBackground(Color.white);
		title.setBackground(Color.white);
		title.setFont(new Font("Times New Roman", Font.BOLD,18));
		title.setAlignmentX(LEFT_ALIGNMENT);
		titlePanel.add(Box.createGlue());
		titlePanel.add(title);
		titlePanel.add(Box.createGlue());
		titlePanel.add(enterJobTitle);
		enterJobTitle.setSize(enterJobTitle.getPreferredSize());
		titlePanel.add(Box.createGlue());
		
		northPanel.add(titlePanel);
		northPanel.add(Box.createGlue());
		
		JPanel enterIDPanel = new JPanel();
		enterIDPanel.setLayout(new BoxLayout(enterIDPanel, BoxLayout.X_AXIS));
		enterIDPanel.setBackground(Color.white);
		JLabel enterID = new JLabel();
		enterID.setText("Enter unique Job ID:   ");
		enterID.setFont(new Font("Times New Roman", Font.BOLD,18));
		enterID.setAlignmentX(LEFT_ALIGNMENT);
		enterIDPanel.add(Box.createGlue());
		enterIDPanel.add(enterID);
		enterIDPanel.add(Box.createGlue());
		enterIDPanel.add(enterUniqueJobID);
		enterIDPanel.add(Box.createGlue());
		
		
		//northPanel.add(companyNamePanel);
		northPanel.add(enterIDPanel);
		northPanel.add(Box.createGlue());
		
		add(northPanel,BorderLayout.NORTH);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.white);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		centerPanel.add(Box.createGlue());
		JLabel details = new JLabel();
		details.setText("Job Description: ");
		details.setFont(new Font("Times New Roman", Font.BOLD,18));
		details.setAlignmentX(CENTER_ALIGNMENT);
		centerPanel.add(details, BorderLayout.NORTH);
		centerPanel.add(Box.createGlue());
		centerPanel.add(enterJobDescription);
		centerPanel.add(Box.createGlue());
		
		add(centerPanel,BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.white);
		southPanel.setLayout(new BoxLayout(southPanel,BoxLayout.X_AXIS));
		southPanel.add(Box.createGlue());
		southPanel.add(submitJobButton);
		southPanel.add(Box.createGlue());
		
		add(southPanel,BorderLayout.SOUTH);
		
		
		
		
	}
	
	private void addListeners(){
		submitJobButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				job = new Job();
				String jobTitle = enterJobTitle.getText();
				String username = employer.getUsername();
				String uniqueID = enterUniqueJobID.getText();
				String jobDescription = enterJobDescription.getText();
				int ID;
				
				if(jobTitle.equals("") || uniqueID.equals("") || jobDescription.equals("")){
					JOptionPane.showMessageDialog(null, "One of the required fields is empty.", "An error occured!", JOptionPane.ERROR_MESSAGE, null);
					return;
				}
				
				try{
					ID = Integer.parseInt(uniqueID);
					job.setUniqueJobID(ID, username, jobTitle);
					job.setDescription(jobDescription);
					client.addJob(employer, job);
					while(client.isJobSentSuccessful() == -1){
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					//1 is successful
					if(client.isJobSentSuccessful() == 1){
						JOptionPane.showMessageDialog(null, "Your job is successfully posted.");
						clearEntries();
					}
					
					//0 is fail
					else{
						JOptionPane.showMessageDialog(null, "Job posting failed!");
					}
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null, "Job ID is not in valid format", "An error occured!", JOptionPane.ERROR_MESSAGE, null);
				}				
			}
			
		});
	}

	@Override
	public void run() {
		
		while(true){
			
		}
	
		
	}
	
}
