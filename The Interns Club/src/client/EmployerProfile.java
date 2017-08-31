package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import networking.Client;
import users.Employer;

public class EmployerProfile extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton save, edit, newImage;
	private JLabel logo, bioLabel, joinDate, emailContact, phoneContact, nameLabel, posLabel;
	private JTextField name, position, phone, email;
	private JTextArea bio;
	private JPanel bottomPanel, editPanel, topPanel;
	private Client client;
	private Employer employer;
	private String absoluteimagepath;

	public EmployerProfile(Client client, Employer employer) {
		this.client = client;
		this.employer = employer;
		initComponents();
		createGUI();
		addListeners();
	}

	public void initComponents() {
		save = new JButton("Save");
		edit = new JButton("Edit Profile");

		newImage = new JButton("Upload New Logo");

		bioLabel = new JLabel("Bio: ");
		bioLabel.setAlignmentX(LEFT_ALIGNMENT);

		emailContact = new JLabel("Email: ");
		emailContact.setAlignmentX(LEFT_ALIGNMENT);

		email = new JTextField(employer.getEmail());
		email.setBorder(null);
		email.setBackground(Color.WHITE);
		email.setEditable(false);

		phoneContact = new JLabel("Phone: ");
		phoneContact.setAlignmentX(LEFT_ALIGNMENT);

		phone = new JTextField(employer.getPhone());
		phone.setBackground(Color.WHITE);
		phone.setBorder(null);
		phone.setEditable(false);

		nameLabel = new JLabel("Company: ");
		nameLabel.setAlignmentX(LEFT_ALIGNMENT);

		posLabel = new JLabel("Position: ");
		posLabel.setAlignmentX(LEFT_ALIGNMENT);

		name = new JTextField(employer.getCompany());
		name.setBackground(Color.WHITE);
		name.setBorder(null);
		name.setEditable(false);

		position = new JTextField(employer.getPosition());
		position.setBackground(Color.WHITE);
		position.setBorder(null);
		position.setEditable(false);

		joinDate = new JLabel("Join Date: " + employer.getJoinDate());
		joinDate.setBackground(Color.WHITE);
		joinDate.setBorder(null);
		joinDate.setAlignmentX(LEFT_ALIGNMENT);
		joinDate.setHorizontalAlignment(SwingConstants.LEFT);

		bio = new JTextArea(" " + employer.getDesc());
		//bio.setBackground(Color.WHITE);
		bio.setPreferredSize(new Dimension(500, 200));
		//bio.setBorder(null);
		bio.setEditable(false);
		bio.setWrapStyleWord(true);
		bio.setLineWrap(true);
	}

	public void createGUI() {
		setSize(500, 470);
		setMaximumSize(new Dimension(500, 450));
		setMinimumSize(new Dimension(500, 450));
		setBackground(Color.WHITE);

		JPanel mainArea = new JPanel();
		mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
		mainArea.setBackground(Color.white);

		topPanel = new JPanel();
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

		ImageIcon noImg;
		if (employer.getLogo() != null) {
			noImg = employer.getLogo();
		} else {
			noImg = new ImageIcon("images/noImg.jpg");
		}
		Image dum1 = noImg.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
		noImg = new ImageIcon(dum1);
		logo = new JLabel(noImg);
		logo.setPreferredSize(new Dimension(150, 150));

		JPanel nameDate = new JPanel();
		nameDate.setLayout(new GridLayout(5, 1));
		nameDate.setBackground(Color.white);
		nameDate.add(name, BorderLayout.NORTH);

		JPanel p1 = new JPanel(new BorderLayout());
		p1.setBackground(Color.white);

		JPanel p2 = new JPanel(new BorderLayout());
		p2.setBackground(Color.white);

		JPanel p3 = new JPanel(new BorderLayout());
		p3.setBackground(Color.white);

		JPanel p4 = new JPanel(new BorderLayout());
		p4.setBackground(Color.white);
		
		JPanel p5 = new JPanel(new BorderLayout());
		p5.setBackground(Color.WHITE);

		p1.add(nameLabel, BorderLayout.WEST);
		p1.add(name, BorderLayout.CENTER);

		p2.add(posLabel, BorderLayout.WEST);
		p2.add(position, BorderLayout.CENTER);

		p3.add(emailContact, BorderLayout.WEST);
		p3.add(email, BorderLayout.CENTER);

		p4.add(phoneContact, BorderLayout.WEST);
		p4.add(phone, BorderLayout.CENTER);
		
		p5.add(joinDate, BorderLayout.WEST);

		nameDate.add(p1);
		nameDate.add(p2);
		nameDate.add(p3);
		nameDate.add(p4);
		nameDate.add(p5);

		
		nameDate.setAlignmentX(LEFT_ALIGNMENT);

		topPanel.add(Box.createHorizontalStrut(40));
		topPanel.add(logo);
		topPanel.add(Box.createHorizontalStrut(10));

		topPanel.add(nameDate);

		editPanel = new JPanel();
		editPanel.setBackground(Color.white);
		editPanel.add(edit);

		topPanel.add(editPanel);

		topPanel.setBackground(Color.WHITE);

		mainArea.add(topPanel);
		mainArea.setBackground(Color.white);

		JPanel bioLabelPanel = new JPanel();
		bioLabelPanel.setPreferredSize(new Dimension(500, 20));
		bioLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 463));
		bioLabelPanel.add(bioLabel);
		bioLabelPanel.setBackground(Color.WHITE);

		mainArea.add(bioLabelPanel);

		JPanel bioPanel = new JPanel();
		bioPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		bioPanel.setBackground(Color.WHITE);
		bioPanel.add(bio);

		mainArea.add(bioPanel);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		bottomPanel.setPreferredSize(new Dimension(500, 20));
		bottomPanel.add(save);
		bottomPanel.add(newImage);
		bottomPanel.setBackground(Color.white);
		bottomPanel.setVisible(false);

		mainArea.add(bottomPanel);
		add(mainArea);

		/*
		 * nameDate.add(contact); nameDate.add(joinDate, BorderLayout.SOUTH);
		 * nameDate.setMaximumSize(new Dimension(300, 150));
		 * topPanel.add(Box.createHorizontalStrut(40)); topPanel.add(logo);
		 * topPanel.add(Box.createHorizontalStrut(10));
		 * 
		 * topPanel.add(nameDate);
		 * 
		 * editPanel = new JPanel(); editPanel.setBackground(Color.white);
		 * editPanel.add(edit);
		 * 
		 * topPanel.add(editPanel);
		 * 
		 * topPanel.setBackground(Color.WHITE);
		 * 
		 * mainArea.add(topPanel); mainArea.setBackground(Color.white);
		 * 
		 * JPanel bioLabelPanel = new JPanel();
		 * bioLabelPanel.setPreferredSize(new Dimension(500, 20));
		 * bioLabelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0,
		 * 463)); bioLabelPanel.add(bioLabel);
		 * bioLabelPanel.setBackground(Color.WHITE);
		 * 
		 * mainArea.add(bioLabelPanel);
		 * 
		 * JPanel bioPanel = new JPanel();
		 * bioPanel.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
		 * bioPanel.setBackground(Color.WHITE); bioPanel.add(bio);
		 * 
		 * mainArea.add(bioPanel);
		 * 
		 * bottomPanel = new JPanel(); bottomPanel.setLayout(new GridLayout(1,
		 * 2)); bottomPanel.setPreferredSize(new Dimension(500, 20));
		 * bottomPanel.add(save); bottomPanel.add(newImage);
		 * bottomPanel.setBackground(Color.white);
		 * bottomPanel.setVisible(false);
		 * 
		 * mainArea.add(bottomPanel); add(mainArea);
		 */

	}

	public void addListeners() {
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				editPanel.setVisible(false);
				bio.setEditable(true);
				phone.setEditable(true);
				email.setEditable(true);
				position.setEditable(true);
				name.setEditable(true);
				bottomPanel.setVisible(true);

			}
		});

		save.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent evt) {
				editPanel.setVisible(true);
				bio.setEditable(false);
				phone.setEditable(false);
				email.setEditable(false);
				position.setEditable(false);
				name.setEditable(false);
				bottomPanel.setVisible(false);

				Employer temp = new Employer(client.username);
				temp.setCompany(name.getText());
				temp.setDesc(bio.getText());
				temp.setEmail(email.getText());
				temp.setJoinDate(employer.getJoinDate());
				temp.setLogo((ImageIcon) logo.getIcon());
				temp.setPhone(phone.getText());
				temp.setPosition(position.getText());

				client.sendMessage(temp, client.username, "updateEmployer");
			}
		});
		
		newImage.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JFileChooser filechooser = new JFileChooser();
				filechooser.setSize(350, 450);
				filechooser.setLocation(350, 150);
				FileNameExtensionFilter filter = new FileNameExtensionFilter(".JPG Files", "JPG");
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
					absoluteimagepath = selectedFile.getAbsolutePath();
					System.out.println(absoluteimagepath);
					

					{
						ImageIcon noImg = new ImageIcon(absoluteimagepath);
						Image dum1 = noImg.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
						noImg = new ImageIcon(dum1);
						logo.setIcon(noImg);
						logo.setPreferredSize(new Dimension(150, 150));
						logo.removeAll();
						logo.revalidate();
						logo.repaint();
					}
				} else if (status == JFileChooser.CANCEL_OPTION) {

				}
				
			}
			
		});
	}
}
