package LoginViews;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class ClientGui extends JFrame {

	JButton studentlogin;
	JButton gueststudentlogin;
	JButton professorlogin;
	JButton employerlogin;
	JPanel mainpanel;
	JLabel loginlabel;
	BufferedImage image;
	private JFrame thisframe;
	// JTextField IPField;
	// JPanel ippanel;

	public ClientGui() {
		super("Welcome");
		setSize(360, 720);
		setLocation(100, 100);
		this.setResizable(false);
		mainpanel = new JPanel();
		mainpanel.setBackground(Color.white);
		mainpanel.setBorder(new EmptyBorder(30, 30, 30, 30));
		mainpanel.setLayout(new GridLayout(5, 1));
		thisframe = this;
		initialization();
	}

	public void initialization() {
		// IPField = new JTextField("127.0.0.1");
		// ippanel = new JPanel();
		// ippanel.setBackground(Color.white);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		studentlogin = new JButton("Student Login");
		gueststudentlogin = new JButton("Guest Student Login");
		professorlogin = new JButton("Professor Login");
		employerlogin = new JButton("Employer Login");
		studentlogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					studentlogin sl = new studentlogin();
					// thisframe.dispose();
				} catch (Exception npe) {
					Component component = (Component) e.getSource();
					JFrame frame = (JFrame) SwingUtilities.getRoot(component);
					JOptionPane.showMessageDialog(frame, "Server is down!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		gueststudentlogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// How to implement the guest login

				try {
					GuestStudentShell studentshell = new GuestStudentShell();
					// thisframe.dispose();
				} catch (Exception npe) {
					npe.printStackTrace();
					Component component = (Component) e.getSource();
					JFrame frame = (JFrame) SwingUtilities.getRoot(component);
					JOptionPane.showMessageDialog(frame, "Server is down!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		professorlogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				try {
					Professorlogin pl = new Professorlogin();
					// thisframe.dispose();
				} catch (Exception npe) {
					Component component = (Component) e.getSource();
					JFrame frame = (JFrame) SwingUtilities.getRoot(component);
					JOptionPane.showMessageDialog(frame, "Server is down!", "Error!", JOptionPane.ERROR_MESSAGE);
				}
			}

		});
		employerlogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Companylogin cl = new Companylogin();
					// thisframe.dispose();
				} catch (Exception npe) {
					Component component = (Component) e.getSource();
					JFrame frame = (JFrame) SwingUtilities.getRoot(component);
					JOptionPane.showMessageDialog(frame, "Server is down!", "Error!", JOptionPane.ERROR_MESSAGE);
				}

			}

		});

		try {
			image = ImageIO.read(this.getClass().getResource("login.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		loginlabel = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		createGui();
	}

	public void createGui() {
		add(mainpanel);
		mainpanel.add(loginlabel);
		// mainpanel.add(ippanel);
		// ippanel.add(IPField);
		mainpanel.add(studentlogin);
		mainpanel.add(gueststudentlogin);
		mainpanel.add(professorlogin);
		mainpanel.add(employerlogin);
		setVisible(true);
	}
}
