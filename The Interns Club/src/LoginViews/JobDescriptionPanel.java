package LoginViews;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class JobDescriptionPanel extends JPanel{
	
	JLabel namethepostiton;
	JLabel jobdescription;
	JTextField jobnamefield;
	JTextArea jobdescriptionarea;
	JScrollPane jobdescriptionpane;
	JButton submit;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;
	JPanel panel5;

	
	public JobDescriptionPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initialization();
	}
	public void initialization(){
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		panel5 = new JPanel();
		
		
		namethepostiton = new JLabel("Name of Position",  SwingConstants.LEFT );

			
		jobdescription = new JLabel("Job Descrioption",  SwingConstants.RIGHT);
		jobdescription.setAlignmentX(Component.RIGHT_ALIGNMENT);
		
		
		jobnamefield = new JTextField();
		jobnamefield.setPreferredSize(new Dimension(400,43));
		jobnamefield.setMaximumSize(new Dimension(400,43));
		jobnamefield.setMinimumSize(new Dimension(400,43));
		jobnamefield.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		jobdescriptionarea = new JTextArea();
		jobdescriptionarea.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		jobdescriptionpane = new JScrollPane(jobdescriptionarea);
		jobdescriptionpane.setPreferredSize(new Dimension(400,200));
		jobdescriptionpane.setMaximumSize(new Dimension(400,200));
		jobdescriptionpane.setMinimumSize(new Dimension(400,200));
		jobdescriptionpane.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		submit = new JButton("Submit");
		submit.setPreferredSize(new Dimension(160,60));
		submit.setMaximumSize(new Dimension(160,60));
		submit.setMinimumSize(new Dimension(160,60));
		submit.setAlignmentX(Component.LEFT_ALIGNMENT);
		createGui();
	}
	
	public void createGui(){
		add(panel1);
		panel1.add(namethepostiton);
		
		
		add(panel2);
		jobnamefield.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel2.add(jobnamefield);
		add(Box.createVerticalStrut(35));
		
		add(panel3);
		jobdescription.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel3.add(jobdescription);
		
		add(panel4);
		jobdescriptionpane.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel4.add(jobdescriptionpane);
		add(Box.createGlue());
		
		
		add(panel5);
		submit.setAlignmentX(Component.LEFT_ALIGNMENT);
		panel5.add(submit);
	}
	
	public void actionlisteners(){
		
	}
}
