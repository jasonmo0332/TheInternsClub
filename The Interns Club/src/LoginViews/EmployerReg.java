package LoginViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import networking.Client;
import users.Employer;

public class EmployerReg extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel companyName, posAtComp, phone, description, companyLogo, rq1, rq2, rq4, rq5, rq3, requiredKey,
			iFName, email;
	private JTextField compNameField, posAtCompField, phoneField, emailField;
	private JTextArea descriptionArea;
	private JButton upload, finish;
	private JPanel mainPanel, gridPanel, flow1, flow2, flow3, flow4, flow5, flow6, flow0, flow7;
	private JScrollPane jsp;
	private JScrollPane mainScroll;
	private String username, password;
	private JFileChooser imageFileChooser;
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	private Client client;
	private File file;
	boolean isUploaded = false;
	JFrame thisframe;
	BufferedImage testimage = null;

	public EmployerReg(String username, String password, Client client) {
		super("Employer Registration");
		setSize(600, 500);
		setLocation(500, 500);
		setBackground(Color.WHITE);
		this.username = username;
		this.password = password;
		thisframe = this;
		this.client = client;
		initializeVariables();
		createGUI();
		addListeners();
		setVisible(true);
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}

	public EmployerReg getThis() {
		return this;
	}

	public void initializeVariables() {
		companyName = createLabel("Company Name                  ");
		posAtComp = createLabel("Position at Company          ");
		phone = createLabel("Phone number                    ");
		description = createLabel("Company Description");
		companyLogo = createLabel("Company Logo                            ");
		requiredKey = createLabel("* = Required field");
		iFName = createLabel("");
		email = createLabel("Email                                    ");
		rq1 = createLabel(" *");
		rq2 = createLabel(" *");
		rq3 = createLabel(" *");
		rq4 = createLabel(" *");
		rq5 = createLabel(" *");

		try {
			testimage = ImageIO.read(this.getClass().getResource("shadowblue.JPG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mainPanel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(testimage, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};

		mainPanel.setBackground(Color.WHITE);
		gridPanel = new JPanel(new GridLayout(8, 1));
		gridPanel.setOpaque(false);
		gridPanel.setBackground(Color.WHITE);

		flow1 = new JPanel();
		flow1.setOpaque(false);
		flow1.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow1.setBackground(Color.WHITE);
		flow2 = new JPanel();
		flow2.setOpaque(false);
		flow2.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow2.setBackground(Color.WHITE);
		flow3 = new JPanel();
		flow3.setOpaque(false);
		flow3.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow3.setBackground(Color.WHITE);
		flow4 = new JPanel();
		flow4.setOpaque(false);
		flow4.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow4.setBackground(Color.WHITE);
		flow5 = new JPanel();
		flow5.setOpaque(false);
		flow5.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow5.setBackground(Color.WHITE);
		flow6 = new JPanel();
		flow6.setOpaque(false);
		flow6.setLayout(new FlowLayout());
		flow6.setBackground(Color.WHITE);
		flow7 = new JPanel();
		flow7.setOpaque(false);
		flow7.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
		flow7.setBackground(Color.WHITE);
		flow0 = new JPanel();
		flow0.setOpaque(false);
		flow0.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
		flow0.setBackground(Color.WHITE);

		upload = createButton("Upload");
		finish = createButton("Finish");
		finish.setEnabled(false);
		emailField = new JTextField();
		emailField.setPreferredSize(new Dimension(150, 20));
		compNameField = new JTextField();
		compNameField.setPreferredSize(new Dimension(150, 20));
		posAtCompField = new JTextField();
		posAtCompField.setPreferredSize(new Dimension(150, 20));
		phoneField = new JTextField();
		phoneField.setPreferredSize(new Dimension(150, 20));
		descriptionArea = new JTextArea(3, 20);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setLineWrap(true);
		jsp = new JScrollPane(descriptionArea);

		imageFileChooser = new JFileChooser();
		imageFileChooser.setPreferredSize(new Dimension(400, 500));
		File workingDirectory = new File(System.getProperty("user.dir"));
		imageFileChooser.setCurrentDirectory(workingDirectory);
		FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files",
				ImageIO.getReaderFileSuffixes());
		imageFileChooser.setFileFilter(imageFilter);
	}

	public void createGUI() {
		add(mainPanel);

		mainScroll = new JScrollPane(mainPanel);
		add(mainScroll);
		flow0.add(requiredKey);
		gridPanel.add(flow0);

		flow1.add(companyName);
		flow1.add(compNameField);
		flow1.add(rq1);
		gridPanel.add(flow1);

		flow2.add(posAtComp);
		flow2.add(posAtCompField);
		flow2.add(rq2);

		gridPanel.add(flow2);
		flow3.add(phone);
		flow3.add(phoneField);
		gridPanel.add(flow3);

		flow7.add(email);
		flow7.add(emailField);
		flow7.add(rq3);
		gridPanel.add(flow7);

		flow5.add(companyLogo);
		flow5.add(upload);
		flow5.add(iFName);
		flow5.add(rq5);
		gridPanel.add(flow5);

		flow4.add(description);
		flow4.add(jsp);
		flow4.add(rq4);
		gridPanel.add(flow4);

		flow6.add(finish);
		gridPanel.add(flow6);
		mainPanel.add(gridPanel);

	}

	public void addListeners() {

		finish.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Employer employer = new Employer(username);
				employer.setCompany(compNameField.getText());
				employer.setPosition(posAtCompField.getText());
				employer.setDesc(descriptionArea.getText());
				employer.setPhone(phoneField.getText());
				employer.setEmail(emailField.getText());
				if (file != null) {
					ImageIcon temp = new ImageIcon(file.getPath());
					employer.setLogo(temp);
				} else {
					ImageIcon noImg = new ImageIcon("images/noimg.jpg");
					employer.setLogo(noImg);

				}

				DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
				Date date = new Date();

				employer.setJoinDate(dateFormat.format(date));

				client.sendMessage(employer, username, "updateEmployer");

				client.setEmployer(employer);
				thisframe.dispose();
				EmployerShell es = new EmployerShell(username, client, employer, null);
				getThis().dispose();

			}

		});

		upload.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int value = imageFileChooser.showOpenDialog(EmployerReg.this);
				if (value == JFileChooser.APPROVE_OPTION) {
					File selected = imageFileChooser.getSelectedFile();
					file = selected;
					iFName.setText(selected.getName());
					isUploaded = true;
					finish.setEnabled((!isFieldsEmpty()) && isUploaded);
				} else if (value == JFileChooser.CANCEL_OPTION) {
					System.out.println("you closed without selecting file");
				}
			}

		});

		compNameField.getDocument().addDocumentListener(new isBlank());
		posAtCompField.getDocument().addDocumentListener(new isBlank());
		descriptionArea.getDocument().addDocumentListener(new isBlank());

	}

	private class isBlank implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			finish.setEnabled((!isFieldsEmpty()) && isUploaded);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			finish.setEnabled((!isFieldsEmpty()) && isUploaded);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			finish.setEnabled((!isFieldsEmpty()) && isUploaded);
		}
	}

	public boolean isFieldsEmpty() {
		if (compNameField.getText().trim().equals("") == true || posAtCompField.getText().trim().equals("") == true
				|| descriptionArea.getText().trim().equals("") == true
				|| emailField.getText().trim().equals("") == true) {
			return true;
		} else {
			return false;
		}
	}

	public JButton createButton(String x) {
		JButton tempButton = new JButton(x);
		Font font = new Font("Verdana", Font.BOLD, 12);
		tempButton.setFont(font);
		return tempButton;
	}

	public JLabel createLabel(String x) {
		JLabel tempLabel = new JLabel(x);
		Font font = new Font("Verdana", Font.BOLD, 12);
		tempLabel.setFont(font);
		return tempLabel;
	}

}
