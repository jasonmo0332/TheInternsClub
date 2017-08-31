package client;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import networking.Client;

public class UploadResume extends JPanel {
	JLabel resumetitle, attachresume, privacysetting;
	JPanel resumetitlep, attachresumep, privacysettingp, titlefieldp, Browsep, publicbuttonp, privatebuttonp, submitp,
			buttonpanelp;
	JTextField titlefield;
	JButton Browse, submit;
	JRadioButton publicbutton, privatebutton;
	JPanel buttonpanel;
	String wholeresumeparesed = "";
	transient FileReader fileReader = null;
	transient BufferedReader bufferedReader = null;

	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	String username;
	private Client client;

	public UploadResume(String username, Client client) {
		this.username = username;
		this.client = client;
		initComponents();
		createGUI();
		addListeners();
	}

	public void initComponents() {

		this.setBackground(Color.white);

		resumetitle = new JLabel("Desired Resume Title");
		resumetitlep = new JPanel(new BorderLayout());
		resumetitlep.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
		resumetitlep.setBackground(Color.white);

		attachresume = new JLabel("Attach your Resume");
		attachresumep = new JPanel(new BorderLayout());
		attachresumep.setBorder(BorderFactory.createEmptyBorder(0, 17, 0, 0));
		attachresumep.setBackground(Color.white);

		privacysetting = new JLabel("Privacy Settings");
		privacysettingp = new JPanel(new BorderLayout());
		privacysettingp.setBorder(BorderFactory.createEmptyBorder(50, 17, 0, 0));
		privacysettingp.setBackground(Color.white);

		titlefield = new JTextField();
		titlefield.setPreferredSize(new Dimension(236, 40));
		titlefieldp = new JPanel();
		titlefieldp.setBackground(Color.white);
		titlefieldp.setAlignmentX(RIGHT_ALIGNMENT);
		// titlefieldp.setBorder(BorderFactory.createEmptyBorder(0,36,0,0));

		Browse = new JButton("Browse your Resume");
		Browse.setPreferredSize(new Dimension(150, 50));
		Browsep = new JPanel();
		Browsep.setBackground(Color.white);
		Browsep.setAlignmentX(RIGHT_ALIGNMENT);
		Browsep.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 89));

		publicbutton = new JRadioButton("Display my Resume and profile Info");
		publicbuttonp = new JPanel();
		publicbuttonp.setBackground(Color.white);
		publicbuttonp.setAlignmentX(RIGHT_ALIGNMENT);

		privatebutton = new JRadioButton("Hide my Resume and Profile Info");
		privatebutton.setSelected(true);
		privatebuttonp = new JPanel();
		privatebuttonp.setBackground(Color.white);
		privatebuttonp.setAlignmentX(RIGHT_ALIGNMENT);

		ButtonGroup group = new ButtonGroup();
		group.add(privatebutton);
		group.add(publicbutton);

		submit = new JButton("Submit your Resume");
		submit.setPreferredSize(new Dimension(150, 60));
		submitp = new JPanel();
		submitp.setBackground(Color.white);
		submitp.setAlignmentX(RIGHT_ALIGNMENT);
		submitp.setBorder(BorderFactory.createEmptyBorder(9, 0, 50, 89));

		buttonpanel = new JPanel();
		buttonpanel.setLayout(new BoxLayout(buttonpanel, BoxLayout.X_AXIS));
		buttonpanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 19));
		buttonpanel.setBackground(Color.white);
		buttonpanelp = new JPanel();

	}

	public void createGUI() {
		JPanel mainArea = new JPanel();
		mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
		mainArea.setBackground(Color.white);
		mainArea.setAlignmentX(RIGHT_ALIGNMENT);

		mainArea.add(resumetitlep);
		resumetitlep.add(resumetitle);
		mainArea.add(titlefieldp);
		titlefieldp.add(titlefield);
		mainArea.add(attachresumep);
		attachresumep.add(attachresume);
		mainArea.add(Browsep);
		Browsep.add(Browse);
		mainArea.add(privacysettingp);
		privacysettingp.add(privacysetting);
		mainArea.add(buttonpanel);
		buttonpanel.add(publicbutton);
		buttonpanel.add(privatebutton);
		mainArea.add(submitp);
		submitp.add(submit);

		add(mainArea);
	}

	public void addListeners() {

		Browse.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
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
					wholeresumeparesed = "";

					try {
						fileReader = new FileReader(selectedFile);
						bufferedReader = new BufferedReader(fileReader);
						String templine = "";
						while (((templine = bufferedReader.readLine()) != null)) {
							wholeresumeparesed += (templine + "\n");
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
			}

		});

		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (privatebutton.isSelected()) {
					client.sendResume(client.username, wholeresumeparesed, false);
				} else {
					client.sendResume(client.username, wholeresumeparesed, true);
				}
				client.setResume(wholeresumeparesed);
			}
		});
	}
}
