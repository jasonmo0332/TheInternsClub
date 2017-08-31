package client;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import networking.Client;
import other.Job;
import users.Employer;
import users.Professor;
import users.Student;

/*
 * A GUI window for professors to recommend students for a particular job posting.
 * Called when the professor hits apply in the previous GUI window.
 * grabs information from the database via the client
 * */
 
public class ProfessorRecommendPanel extends JFrame implements Runnable{
	
	private JLabel namethepostiton, jobdescription, selectStudent;
	private JTextField jobnamefield;
	private JTextArea jobdescriptionarea;
	private JScrollPane jobdescriptionpane;
	private JButton recommendWithLetter;
	private JComboBox studentList;
	private JPanel panel1, panel2, panel3, panel4, panel5, panel6, mainpanel;
	private String jobtitle = "";
	private String details = "";
	private String recLetterParsed;
	private String username;
	private Vector<Student> listOfStudents1;
	private Vector<String> comboBoxList;
	private JFileChooser textFileChooser;
	private Client client;
	private Job job;
	private FileReader fileReader;
	private BufferedReader bufferedReader;
	private Professor professor;
	private Employer employer;
	
	
	public ProfessorRecommendPanel(Job job, Client client, String username, Professor professor) {
		super("The Interns Club");
		setSize(500, 500);
		setLocation(200, 200);
		//grabs the passed in parameters
		this.job = job;
		this.client = client;
		this.username = username;
		this.professor = professor;
		//Clears the set of students because otherwise would grab all students
		client.setAllStudents(null);
		comboBoxList = new Vector<>();
		studentList = new JComboBox<>();
		//grabs the students based on the unique job that they applied for
		client.getStudentByJob(job.getUniqueJobID());
		//provides the updates for grabing hte list of students
		while(client.getAllStudents() == null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.listOfStudents1 = client.getAllStudents();
		//If professor clicks recommend for a job with no students applied to it, it disposes this window.
		if(listOfStudents1.size() == 0){
			JOptionPane.showMessageDialog(null, "No Students has applied!");
			dispose();
			return;
		}
		//fills the combo box with the students based on first and last name
		for (Student s : client.getAllStudents()) {
			comboBoxList.add(s.getFirstName() + " " + s.getLastName());
		}
		
		//Fills the job title and details
		jobtitle = (job.getJobTitle());
		details = (job.getDescription());
		employer = client.getEmployer();
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		initialization();
		createGui();
		actionlisteners();
	}
	
	public void initialization(){
		
		//top down listing of the panels
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		panel6 = new JPanel();
		selectStudent = new JLabel("Please select a student from the combo box to recommend ");
		mainpanel = new JPanel();
		mainpanel.setLayout(new BoxLayout(mainpanel, BoxLayout.Y_AXIS));
		
		
		namethepostiton = new JLabel("Name of Position",  SwingConstants.LEFT );

			
		jobdescription = new JLabel("Job Description",  SwingConstants.RIGHT);
		jobdescription.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		
		jobnamefield = new JTextField(jobtitle);
		jobnamefield.setPreferredSize(new Dimension(400,43));
		jobnamefield.setMaximumSize(new Dimension(400,43));
		jobnamefield.setMinimumSize(new Dimension(400,43));
		jobnamefield.setAlignmentX(Component.CENTER_ALIGNMENT);
		jobnamefield.setEditable(false);
		
		jobdescriptionarea = new JTextArea(details);
		jobdescriptionarea.setAlignmentX(Component.CENTER_ALIGNMENT);
		jobdescriptionarea.setEditable(false);
		
		jobdescriptionpane = new JScrollPane(jobdescriptionarea);
		jobdescriptionpane.setPreferredSize(new Dimension(400,200));
		jobdescriptionpane.setMaximumSize(new Dimension(400,200));
		jobdescriptionpane.setMinimumSize(new Dimension(400,200));
		jobdescriptionpane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		recommendWithLetter = new JButton("Recommend with Letter");
		recommendWithLetter.setPreferredSize(new Dimension(160,60));
		recommendWithLetter.setMaximumSize(new Dimension(160,60));
		recommendWithLetter.setMinimumSize(new Dimension(160,60));
		recommendWithLetter.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		// if no students applied, make a blank combo box
		if(comboBoxList == null) {
			studentList = new JComboBox();
			
		}
		else {
			studentList = new JComboBox(comboBoxList);
			studentList.insertItemAt("Please Select a student", 0);
			studentList.setSelectedIndex(0);
		}
		
		studentList.setAlignmentX(Component.LEFT_ALIGNMENT);

		
		textFileChooser = new JFileChooser();
		textFileChooser.setPreferredSize(new Dimension(400, 500));
		File workingDirectory = new File(System.getProperty("user.dir"));
		textFileChooser.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter textFilter = new FileNameExtensionFilter("Image files",
				ImageIO.getReaderFileSuffixes());
		textFileChooser.setFileFilter(textFilter);
	}
	
	public void createGui(){
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
		
		mainpanel.add(panel6);
		selectStudent.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel6.add(selectStudent);
		
		mainpanel.add(panel5);
		panel5.add(studentList);
		studentList.setAlignmentX(Component.LEFT_ALIGNMENT);
		recommendWithLetter.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel5.add(recommendWithLetter);
		this.setVisible(true);
	}
	
	public void actionlisteners(){
		
		recommendWithLetter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
					//simple file chooser to choose a "recommendation letter"
					JFileChooser filechooser = new JFileChooser();
					filechooser.setSize(350, 450);
					filechooser.setLocation(350, 150);
					FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt Files", "txt");
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
						recLetterParsed = "";
	
						try {
							fileReader = new FileReader(selectedFile);
							bufferedReader = new BufferedReader(fileReader);
							String templine = "";
							while (((templine = bufferedReader.readLine()) != null)) {
								//fills in the parsed recommendation letter
								recLetterParsed += (templine + "\n");
							}
	
						} catch (FileNotFoundException ex) {
							System.out.println("Unable to open the file");
						} catch (IOException ex) {
							System.out.println("Error reading the file");
						} finally {
							if (bufferedReader != null) {
								try {
									bufferedReader.close();
								} catch (IOException ioe) {
								}
							}
							if (fileReader != null) {
								try {
									fileReader.close();
								} catch (IOException ioe) {
								}
							}
						}
					} else if (status == JFileChooser.CANCEL_OPTION) {
	
					}
					if (status == JFileChooser.APPROVE_OPTION) {
						int selectedStudent = studentList.getSelectedIndex();
						JOptionPane.showMessageDialog(null, "Recommendation letter sent!");
						//Sends the recommendation letter to the client, which will save it into the database
						client.sendRecLetter(listOfStudents1.get(selectedStudent-1).getUsername(), recLetterParsed, job.getUniqueJobID());
					}
				}
			//send notifications to employer that student username has been recommended with the letter
		});
		
		studentList.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
			
		});
	}

	@Override
	//runs thread to constantly check for updates specifically if new students apply to a job
	public void run() {
		client.checkForUpdates();
	}
}
