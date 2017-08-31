package client;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import networking.Client;
import other.Job;
import users.Student;

public class jobListPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4900999543053797197L;
	
	private int numOfJobs;
	private JPanel jobButtonBox;
	private Stack<JButton> jobs;
	private Vector<jobDetailsPanel> jdp;
	private Vector<Job> allJobs;
	private Client client;
	private Vector<Vector<Student>> studentLists;
	private Vector<Vector<String>> recLetters;
	
	public jobListPanel(Vector<Job> j, Vector<Vector<Student>> s,Vector<Vector<String>> letters, Client c){
		this.allJobs = j;
		this.studentLists = s;
		this.client = c;
		this.recLetters = letters;
		if( j == null){
			this.numOfJobs = 0;
		}
		else{
			this.numOfJobs = j.size();
		}
		
		//System.out.println("num of jobs is " + numOfJobs);
		initializeVar();
		createGUI();
		addEvents();
	}
	
	private void showJobDetails(jobDetailsPanel toReplace){
		this.removeAll();
		this.add(toReplace);
		this.validate();
		this.repaint();
	}
	
	public void showJobList(){
		this.removeAll();
		this.add(jobButtonBox);
		this.validate();
		this.repaint();
	}
	
	private void initializeVar(){
		if(numOfJobs !=0){
			jobButtonBox = new JPanel();
			jobButtonBox.setBackground(Color.white);
			jobButtonBox.setLayout(new BoxLayout(jobButtonBox,BoxLayout.Y_AXIS));
			//jobButtonBox.setPreferredSize(new Dimension(500,470));
			//jobButtonBox.setMaximumSize(new Dimension(500,470));
			jdp = new Vector<jobDetailsPanel>();
			jobs = new Stack<JButton>();
			for(int i=0;i<numOfJobs;i++){
				//System.out.println("Printing student name: " + studentLists.get(i).get(0).getUsername());
				jobDetailsPanel detailsPanel = new jobDetailsPanel(this,allJobs.get(i),studentLists.get(i),recLetters.get(i),client);
				jdp.add(detailsPanel);
				JButton toAdd = new JButton();
				toAdd.setBackground(Color.white);
				toAdd.setSize(new Dimension(500,100));
				toAdd.setPreferredSize(new Dimension(500,100));
				toAdd.setMinimumSize(new Dimension(500,100));
				toAdd.setMaximumSize(new Dimension(500,100));
				toAdd.setText(allJobs.get(i).getJobTitle());
				toAdd.setFont(new Font("Times New Roman", Font.BOLD,16));
				jobs.addElement(toAdd);
			}
		}
		
		else{
			
		}
	}
	
	private void createGUI(){
		setSize(new Dimension(500,470));
		setMaximumSize(new Dimension(500, 450));
		setMinimumSize(new Dimension(500, 450));
		setBackground(Color.WHITE);
		setVisible(true);
		setLayout(new GridLayout(1,1));
		if(numOfJobs != 0){
			for(int i=0;i<numOfJobs;i++){
				jobButtonBox.add(jobs.get(i));
			}
			JScrollPane scrollPane = new JScrollPane(jobButtonBox,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
			add(scrollPane);
		}
	}
	
	private void addEvents(){
		if(numOfJobs != 0){
			for(int i=0;i<numOfJobs;i++){
				int index =i;
				jobs.get(index).addActionListener(new ActionListener(){

					@Override
					public void actionPerformed(ActionEvent e) {
						if(jdp.get(index).hasApplyingStudents()){
							showJobDetails(jdp.get(index));
						}
						
						else{
							JOptionPane.showMessageDialog(null, "No students have applied to this job yet.");
						}
						
					}
					
				});
			}
		}
	}
	
	/*public static void main (String[] args){
		JFrame test = new JFrame();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setSize(800, 600);
		test.add(new jobListPanel(5));
		test.setVisible(true);
	}*/
	
}
