package client;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.Serializable;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import users.Student;

public class LetterPanel extends JFrame implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -613869781468568060L;
	private JTextArea resume;
	
	public LetterPanel(Student s, String letter){
		super(s.getFirstName() +" " + s.getLastName() + "'s recommendation letter");
		setSize(new Dimension(500,470));
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLayout(new GridLayout(1,1));
		resume = new JTextArea(letter);
		resume.setLineWrap(true);
		resume.setWrapStyleWord(true);
		resume.setEditable(false);
		resume.setVisible(true);
		JScrollPane scrollPane = new JScrollPane (resume,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		add(scrollPane);
		setVisible(true);
	}
	
}



