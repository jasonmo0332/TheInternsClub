package networking;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import org.jasypt.util.password.StrongPasswordEncryptor;

import edu.vt.middleware.password.CharacterCharacteristicsRule;
import edu.vt.middleware.password.DigitCharacterRule;
import edu.vt.middleware.password.LengthRule;
import edu.vt.middleware.password.LowercaseCharacterRule;
import edu.vt.middleware.password.NonAlphanumericCharacterRule;
import edu.vt.middleware.password.Password;
import edu.vt.middleware.password.PasswordData;
import edu.vt.middleware.password.PasswordValidator;
import edu.vt.middleware.password.Rule;
import edu.vt.middleware.password.RuleResult;
import edu.vt.middleware.password.UppercaseCharacterRule;
import edu.vt.middleware.password.WhitespaceRule;
import other.Job;
import users.Employer;
import users.Professor;
import users.Student;

public class Server implements Runnable {

	private List<ServerThread> serverThreads;
	ServerSocket ss = null;
	private int threadID;
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	private Vector<Job> allJobs = new Vector<Job>();
	private Vector<Notification> pendingNotifications = new Vector<Notification>();
	// private static String networkedjdbc =
	// "jdbc:mysql://jonlu.ca/jonluca_tic?user=jonluca_jonluca&password=TICPassword1!&useSSL=false";
	private static String networkedjdbc = "jdbc:mysql://localhost/TheInternsClub?user=root&useSSL=false";

	public static void main(String[] args) {
		Server temp = new Server(6789);
		Thread up = new Thread(temp);
		up.start();

	}

	public Server(int port) {
		threadID = 0;
		try {
			ss = new ServerSocket(port);
			serverThreads = Collections.synchronizedList(new ArrayList<ServerThread>());
			pendingNotifications = getPendingNotifications();
			getAllJobs();

		} catch (IOException ioe) {
			System.out.println("ERROR: " + ioe.getMessage());
			System.out.println("Quitting!");
			System.exit(0);
		}
	}

	public void end() {
		try {
			if (ss != null) {
				ss.close();
			}
		} catch (IOException ioe) {
		}
	}

	public void sendNotification(Notification n) {
		addNotifToDatabase(n);
	}

	public Vector<Student> getAllStudents() {
		Vector<Student> studs = new Vector<Student>();
		try {
			ResultSet rs;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String selectName = "SELECT * FROM `student`";
			try {
				PreparedStatement ps = conn.prepareStatement(selectName);
				rs = ps.executeQuery();
				while (rs.next()) {
					Student temp = new Student(rs.getString("username"));
					byte[] byteArray;
					if (rs.getBlob(16) != null) {
						try {
							byteArray = new byte[rs.getBinaryStream(16).available()];
							rs.getBinaryStream(16).read(byteArray);
							ImageIcon image = new ImageIcon(byteArray);
							Image dum1 = image.getImage().getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
							image = new ImageIcon(dum1);
							temp.setImage(image);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}

					temp.setSchool(rs.getString(3));
					temp.setFirstName(rs.getString(4));
					temp.setLastName(rs.getString(5));
					temp.setEmail(rs.getString(6));
					temp.setProfilePic(rs.getString(7));
					temp.setBio(rs.getString(9));
					temp.setSchool(rs.getString(10));
					temp.setMajor(rs.getString(11));
					temp.setGPA(rs.getString(12));
					temp.setResume(rs.getString(8));
					studs.add(temp);
				}

			} catch (SQLException sql) {
				System.out.println("sqle: line 131 " + sql.getMessage());
				sql.printStackTrace();
			}
		} catch (SQLException sql) {
			System.out.println("sqle: line 136" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sqle) {
				System.out.println("sql problem");
			}
		}
		return studs;

	}

	public void sendMessageToAllClients(Message cm) {
		if (cm != null) {
			if (!cm.getType().equals("getJobs") && !cm.getType().equals("getNotifs")) {
				System.out.println(cm.getSentUser() + " sent message with type " + cm.getType());
			}
			if (cm.getType().equals("login")) {
				if (login(cm.getUsername(), cm.getPassword(), cm.getLoginType(), cm)) {
					cm.setMessage("true");
					if (pendingNotifications != null) {
						for (Notification n : pendingNotifications) {
							if (n.getUsername().equals(cm.getUsername())) {
								cm.addNotification(n);
							}
						}
					}
					if (cm.getLoginType().equals("employer")) {
						cm.setJobs(getJobsByUser(cm.getUsername()));

					}
				} else {
					cm.setMessage("false");
				}
				System.out.println("Login success: " + cm.getMessage() + " for " + cm.getSentUser());
			} else if (cm.getType().equals("create")) {
				int result = create(cm.getUsername(), cm.getPassword(), cm.getLoginType());
				cm.setMessage(Integer.toString(result));
			} else if (cm.getType().equals("updateStudent")) {
				Student temp = cm.getStudent();
				if (temp.getResume() != null && !temp.getResume().trim().equals("")) {
					addResume(temp.getUsername(), temp.getResume(), true);
				}
				studentRegistration(temp.getFirstName(), temp.getLastName(), temp.getSchool(), temp.getEmail(),
						temp.getUsername(), temp.getBio(), temp.getProfilePic(), temp.getJoinDate(), temp.getMajor(),
						temp.getGPA(), temp.getImage());
				cm.setNeedsReturn(false);

			} else if (cm.getType().equals("updateProfessor")) {
				Professor temp = cm.getProfessor();
				professorRegistration(temp.getFirstName(), temp.getLastName(), temp.getSchool(), temp.getEmail(),
						temp.getUsername(), temp.getBio(), temp.getProfilePic(), temp.getJoinDate(), temp.getPhone(),
						temp.getImage());
				cm.setNeedsReturn(false);
			} else if (cm.getType().equals("updateEmployer")) {
				Employer temp = cm.getEmployer();
				employerRegistration(temp.getCompany(), temp.getPosition(), temp.getPhone(), temp.getUsername(),
						temp.getDesc(), temp.getEmail(), temp.getProfilePic(), temp.getJoinDate(), temp.getLogo());
				cm.setNeedsReturn(false);
			} else if (cm.getType().equals("addNotification")) {
				Notification temp = cm.getNotification();
				addNotifToDatabase(temp);
			} else if (cm.getType().equals("Add_job")) {
				Job j = cm.getJob();
				cm.setJobSuccess(addJob(j));
			} else if (cm.getType().equals("checkedNotifications")) {
				if (cm.getPendingNotifs() == null) {
					return;
				}
				clearNotifications(cm.getPendingNotifs());
				return;
			} else if (cm.getType().equals("apply")) {
				applyToJob(cm.getJob(), cm.getMessage());
			} else if (cm.getType().equals("getNotifs")) {
				Vector<Notification> temp2 = getPendingNotifications();
				Vector<Notification> temp = new Vector<Notification>();
				for (Notification n : temp2) {
					if (n.getUsername().equals(cm.getMessage())) {
						temp.add(n);
						cm.setPendingNotifs(temp);
					}
				}
			} else if (cm.getType().equals("getStudents")) {
				cm.setStudents(getAllStudents());
			} else if (cm.getType().equals("getStudent")) {
				cm.setStudent(getStudent(cm.getMessage()));
			} else if (cm.getType().equals("getJobs")) {
				getAllJobs();
				cm.setJobs(allJobs);
			} else if (cm.getType().equals("getJobsByUser")) {
				Vector<Job> temp = getJobsByUser(cm.getMessage());
				cm.setJobs(temp);
			} else if (cm.getType().equals("acceptStudent")) {
				acceptToJob(cm.getJob(), cm.getMessage(), cm.getStudents());
			} else if (cm.getType().equals("rejectStudent")) {
				rejectJob(cm.getJob(), cm.getMessage());
			} else if (cm.getType().equals("addResume")) {
				addResume(cm.getUsername(), cm.getMessage(), cm.isBool());
			} else if (cm.getType().equals("addRecLetter")) {
				addLetter(cm.getUsername(), cm.getMessage(), cm.getID());
			} else if (cm.getType().equals("studentFromID")) {
				Vector<String> usernames = getStudentsByID(cm.getID());
				if (usernames != null && usernames.size() > 0) {
					Vector<Student> stud = new Vector<Student>();
					for (String s : usernames) {
						stud.add(getStudent(s));
					}
					cm.setStudents(stud);
				}

			}
			if (cm.isNeedsReturn()) {
				synchronized (serverThreads) {
					for (ServerThread st : serverThreads) {
						if (st.getID() == cm.serverID) {
							st.sendMessage(cm);
							break;
						}
					}
				}
			}
		}
	}

	public Vector<String> getStudentsByID(int ID) {
		Vector<String> temp = new Vector<String>();
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();

			String updateinfo = "SELECT * FROM `jobs` WHERE `JOBID`=" + Integer.toString(ID);
			try {
				rs = st.executeQuery(updateinfo);
				while (rs.next()) {

					if (!rs.getString("APPLIEDSTUDENT").equals("null")) {
						temp.add(rs.getString("APPLIEDSTUDENT"));
					}
				}

			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
		return temp;
	}

	public void addLetter(String username, String letter, int id) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String updateinfo = "";

			updateinfo = "UPDATE `jobs` SET `LETTER`=? WHERE `APPLIEDSTUDENT` = ? AND `JOBID`=?";

			try {
				PreparedStatement ps2 = conn.prepareStatement(updateinfo);

				ps2.setString(1, letter);
				ps2.setString(2, username);
				ps2.setInt(3, id);
				ps2.executeUpdate();

				Notification n = new Notification(username, "You've been recommended!");
				addNotifToDatabase(n);
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
				sql.printStackTrace();
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
			sql.printStackTrace();

		} catch (ClassNotFoundException e1) {

		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
	}

	public void rejectJob(Job j, String username) {
		Notification n = new Notification(username,
				"Unfortunately you have been rejected from the job \"" + j.getJobTitle() + "\"!");

		addNotifToDatabase(n);
	}

	public Student getStudent(String username) {
		Vector<Student> temp = getAllStudents();
		for (Student t : temp) {
			if (t.getUsername().equals(username)) {
				return t;
			}
		}
		return null;
	}

	public Vector<Job> getJobsByUser(String Username) {
		Vector<Job> temp = new Vector<Job>();

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();

			String updateinfo = "SELECT * FROM `jobs`";
			try {
				rs = st.executeQuery(updateinfo);
				while (rs.next()) {
					int jobID = rs.getInt("JOBID");
					String title = rs.getString("TITLE");
					String emp = rs.getString("EMPLOYER");
					String desc = rs.getString("DESCRIPTION");
					String studentApplied = rs.getString("APPLIEDSTUDENT");
					int avail = rs.getInt("AVAILABLE");
					String letter = rs.getString("LETTER");

					Job j = new Job();
					j.setUniqueJobID(jobID, emp, title);
					j.setDescription(desc);
					j.setStudUsernames(studentApplied);
					j.setRecLetters(letter);

					if (avail == 1) {
						j.setAvailable(true);
					} else {
						j.setAvailable(false);
					}
					if (j.getUsername().equals(Username)) {
						temp.add(j);
					}

				}
				allJobs = temp;

			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
		return temp;
	}

	public void applyToJob(Job j, String username) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();

			String updateinfo = "INSERT INTO `jobs`(`JOBID`, `TITLE`, `EMPLOYER`, `DESCRIPTION`, `AVAILABLE`,`APPLIEDSTUDENT`) VALUES(?,?,?,?,?,?)";
			try {

				PreparedStatement ps2 = conn.prepareStatement(updateinfo);
				ps2.setInt(1, j.getUniqueJobID());
				ps2.setString(2, j.getJobTitle());
				ps2.setString(3, j.getUsername());
				ps2.setString(4, j.getDescription());
				ps2.setInt(5, 1);
				ps2.setString(6, username);

				ps2.executeUpdate();

			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
	}

	public void acceptToJob(Job j, String username, Vector<Student> rejected) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();

			String updateinfo = "UPDATE `jobs` SET `AVAILABLE`=? WHERE `JOBID`=?";
			try {
				PreparedStatement ps = conn.prepareStatement(updateinfo);
				ps.setInt(1, 0);
				ps.setInt(2, j.getUniqueJobID());
				ps.executeUpdate();

				for (Student s : rejected) {
					if (s.getUsername().equals(username)) {
						Notification temp = new Notification(s.getUsername(),
								"You've been accepted to the job \"" + j.getJobTitle() + "\"!");

						addNotifToDatabase(temp);

					} else {
						Notification temp = new Notification(s.getUsername(),
								"You've been rejected from the job \"" + j.getJobTitle() + "\"!");
						addNotifToDatabase(temp);
					}

				}

			} catch (SQLException sql) {
				System.out.println("sqle:11" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:10" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:9" + sql.getMessage());
			}
		}
	}

	public void clearNotifications(Vector<Notification> n) {
		System.out.println("SERVER clearnotifs called");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String updateinfo = "UPDATE `notification` SET `r`=?  WHERE `username` = ? AND `message` = ?";
			try {
				PreparedStatement ps2 = conn.prepareStatement(updateinfo);

				for (Notification not : n) {
					ps2.setInt(1, 1);
					ps2.setString(2, not.getUsername());
					ps2.setString(3, not.getMessage());
					ps2.executeUpdate();
				}

			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
	}

	public int addJob(Job j) {
		int success = -1;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String selectName = "SELECT * FROM `jobs` WHERE  `JOBID`=?";

			String updateinfo = "INSERT INTO `jobs`(`JOBID`, `TITLE`, `EMPLOYER`, `DESCRIPTION`, `AVAILABLE`, `APPLIEDSTUDENT`) VALUES(?,?,?,?,?,?)";
			try {
				PreparedStatement ps = conn.prepareStatement(selectName);
				ps.setInt(1, j.getUniqueJobID());
				rs = ps.executeQuery();
				boolean exists = false;
				while (rs.next()) {
					exists = true;
					success = 0;
				}
				if (!exists) {
					success = 1;
					allJobs.add(j);
					PreparedStatement ps2 = conn.prepareStatement(updateinfo);
					ps2.setInt(1, j.getUniqueJobID());
					ps2.setString(2, j.getJobTitle());
					ps2.setString(3, j.getUsername());
					ps2.setString(4, j.getDescription());
					ps2.setInt(5, 1);
					ps2.setString(6, "null");
					ps2.executeUpdate();
				}

			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
		return success;
	}

	public void addNotifToDatabase(Notification notification) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();

			String updateinfo = "INSERT INTO `notification`(`username`, `message`,`r`) VALUES(?,?,?)";
			try {
				PreparedStatement ps = conn.prepareStatement(updateinfo);
				ps.setString(1, notification.getUsername());
				ps.setString(2, notification.getMessage());
				ps.setInt(3, 0);
				ps.executeUpdate();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
	}

	@Override
	public void run() {
		while (true) {
			System.out.println("waiting for connection...");
			Socket s;
			try {
				s = ss.accept();
				System.out.println("Connection from " + s.getInetAddress());
				ServerThread st = new ServerThread(s, this, threadID);
				++threadID;
				serverThreads.add(st);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public boolean login(String Username, String Password, String type, Message cm) {
		boolean found = false;
		try {
			ResultSet rs = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String selectName = "SELECT * FROM `" + type + "` WHERE `username`=?";
			try {
				PreparedStatement ps = conn.prepareStatement(selectName);
				ps.setString(1, Username);
				rs = ps.executeQuery();
				while (rs.next()) {
					StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

					// if (passwordEncryptor.checkPassword(Password,
					// rs.getString(2))) {
					if (true) {
						found = true;

						// If the type is employer
						if (type.equals("employer")) {
							String Company = rs.getString(3);
							String Email = rs.getString(4);
							String Pos = rs.getString(5);
							String Path = rs.getString(6);
							String phone = rs.getString(7);
							String Bio = rs.getString(8);
							String joinDate = rs.getString(9);

							Employer e = new Employer(Username);
							if (rs.getBinaryStream(10) != null) {
								byte[] byteArray;
								try {
									byteArray = new byte[rs.getBinaryStream("image").available()];
									rs.getBinaryStream("image").read(byteArray);
									ImageIcon image = new ImageIcon(byteArray);
									Image dum1 = image.getImage().getScaledInstance(150, 150,
											java.awt.Image.SCALE_SMOOTH);
									image = new ImageIcon(dum1);
									e.setLogo(image);
								} catch (IOException e1) {
									e1.printStackTrace();
								}
							}

							e.setCompany(Company);
							e.setEmail(Email);
							e.setPosition(Pos);
							e.setPhone(phone);
							e.setDesc(Bio);
							e.setJoinDate(joinDate);
							cm.setEmployer(e);

						} else if (type.equals("professor")) {
							Professor temp = new Professor(Username);
							if (rs.getBinaryStream(9) != null) {
								byte[] byteArray;
								try {
									byteArray = new byte[rs.getBinaryStream("image").available()];
									rs.getBinaryStream("image").read(byteArray);
									ImageIcon image = new ImageIcon(byteArray);
									Image dum1 = image.getImage().getScaledInstance(150, 150,
											java.awt.Image.SCALE_SMOOTH);
									image = new ImageIcon(dum1);
									temp.setImage(image);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}

							temp.setSchool(rs.getString("school"));
							temp.setFirstName(rs.getString("first"));
							temp.setLastName(rs.getString("last"));
							temp.setEmail(rs.getString("email"));
							temp.setProfilePic(rs.getString(5));
							temp.setBio(rs.getString("bio"));
							temp.setPhone(rs.getString("phone"));
							temp.setJoinDate(rs.getString("joinDate"));
							cm.setProfessor(temp);

						} else if (type.equals("student")) {
							Student temp = new Student(Username);
							byte[] byteArray;
							if (rs.getBinaryStream(16) != null) {
								try {
									byteArray = new byte[rs.getBinaryStream(16).available()];
									rs.getBinaryStream(16).read(byteArray);
									ImageIcon image = new ImageIcon(byteArray);
									Image dum1 = image.getImage().getScaledInstance(150, 150,
											java.awt.Image.SCALE_SMOOTH);
									image = new ImageIcon(dum1);
									temp.setImage(image);
								} catch (IOException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}

							temp.setSchool(rs.getString(3));
							temp.setFirstName(rs.getString(4));
							temp.setLastName(rs.getString(5));
							temp.setEmail(rs.getString("email"));
							temp.setProfilePic(rs.getString(7));
							temp.setBio(rs.getString("bio"));
							temp.setSchool(rs.getString("university"));
							temp.setMajor(rs.getString("Major"));
							temp.setGPA(rs.getString("GPA"));
							temp.setJoinDate(rs.getString("joinDate"));
							temp.setResume(rs.getString("resume"));
							cm.setStudent(temp);
						}
					}
				}

			} catch (SQLException sql) {
				System.out.println("sqle: line 131 " + sql.getMessage());
				sql.printStackTrace();
			}
		} catch (SQLException sql) {
			System.out.println("sqle: line 136" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sqle) {
				System.out.println("sql problem");
			}
		}
		return found;

	}

	public int create(String Username, String Password, String type) {
		// 0 = success, 1 = invalid password requirements, 2 = result already
		// exists
		int success = -1;
		LengthRule lengthRule = new LengthRule(8, 16);
		WhitespaceRule whitespaceRule = new WhitespaceRule();
		CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();
		charRule.getRules().add(new DigitCharacterRule(1));
		charRule.getRules().add(new NonAlphanumericCharacterRule(1));
		charRule.getRules().add(new UppercaseCharacterRule(1));
		charRule.getRules().add(new LowercaseCharacterRule(1));
		List<Rule> ruleList = new ArrayList<Rule>();

		ruleList.add(lengthRule);

		ruleList.add(whitespaceRule);

		ruleList.add(charRule);

		PasswordValidator validator = new PasswordValidator(ruleList);

		PasswordData passwordData = new PasswordData(new Password(Password));

		RuleResult result = validator.validate(passwordData);
		if (result.isValid()) {
			StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();

			String encryptedPassword = passwordEncryptor.encryptPassword(Password);
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(networkedjdbc);
				st = conn.createStatement();
				String selectName = "SELECT * FROM `" + type + "` WHERE  `username`=?";
				String addinfo = "";
				if (type.equals("employer")) {
					addinfo = "INSERT INTO `" + type
							+ "`(`username`, `password`,`company`, `pathLogo`) VALUES(?,?,?,?)";
				} else {
					addinfo = "INSERT INTO `" + type + "`(`username`, `password`,`first`, `last`) VALUES(?,?,?,?)";
				}
				try {
					PreparedStatement ps = conn.prepareStatement(selectName);
					PreparedStatement ps2 = conn.prepareStatement(addinfo);
					ps.setString(1, Username);
					rs = ps.executeQuery();
					while (rs.next()) {
						success = 2;
					}
					if (success != 2) {
						success = 0;
						ps2.setString(1, Username);
						ps2.setString(2, encryptedPassword);
						ps2.setString(3, "");
						ps2.setString(4, "");
						ps2.executeUpdate();
					}
				} catch (SQLException sql) {
					System.out.println("sqle: 206 " + sql.getMessage());
				}
			} catch (SQLException sql) {
				System.out.println("sqle: 209 " + sql.getMessage());
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} finally {
				try {
					if (rs != null)
						rs.close();
					if (st != null)
						st.close();
					if (conn != null)
						conn.close();
				} catch (SQLException sqle) {
					System.out.println("sql problem");
				}
			}
		} else {
			success = 1;
		}
		System.out.println("Server creation of " + Username + " Finished with sucess " + success);
		return success;
	}

	public void addResume(String username, String resume, boolean isPublic) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String updateinfo = "";

			if (isPublic) {
				updateinfo = "UPDATE `student` SET `resume`=?,`publicaccount`=1 WHERE `username` = ?";
			} else {
				updateinfo = "UPDATE `student` SET `resume`=?,`publicaccount`=0  WHERE `username` = ?";
			}

			try {
				PreparedStatement ps2 = conn.prepareStatement(updateinfo);

				ps2.setString(1, resume);
				ps2.setString(2, username);
				ps2.executeUpdate();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
				sql.printStackTrace();
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
			sql.printStackTrace();

		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
	}

	public void studentRegistration(String fname, String lname, String school, String email, String username,
			String bio, String profPic, String date, String Major, String GPA, ImageIcon icon) {
		InputStream is = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String updateinfo = "UPDATE `student` SET `first`=? ,`last`=? ,`school`=? , `email`=? ,`pathPicture`=?  ,`bio`=?,`image`=?, `joinDate`=?, `Major`=?, `GPA`=?  WHERE `username` = ?";
			try {
				PreparedStatement ps2 = conn.prepareStatement(updateinfo);

				BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics g = bi.createGraphics();
				icon.paintIcon(null, g, 0, 0);
				g.dispose();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					ImageIO.write(bi, "jpg", baos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] imageInByte = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);

				ps2.setString(1, fname);
				ps2.setString(2, lname);
				ps2.setString(3, school);
				ps2.setString(4, email);
				ps2.setString(5, "null"); // profpic
				ps2.setString(6, bio);
				ps2.setBlob(7, bais);
				ps2.setString(8, date);
				ps2.setString(9, Major);
				ps2.setString(10, GPA);
				ps2.setString(11, username);
				ps2.executeUpdate();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}

	}

	public void professorRegistration(String fname, String lname, String school, String email, String username,
			String bio, String profPic, String date, String phone, ImageIcon icon) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String updateinfo = "UPDATE `professor` SET `first`=? ,`last`=? ,`school`=? , `email`=? ,`image`=? ,`bio`=?, `joinDate`=?, `phone`=? WHERE `username` = ?";
			try {
				PreparedStatement ps2 = conn.prepareStatement(updateinfo);
				BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics g = bi.createGraphics();
				icon.paintIcon(null, g, 0, 0);
				g.dispose();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					ImageIO.write(bi, "jpg", baos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] imageInByte = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);

				ps2.setString(1, fname);
				ps2.setString(2, lname);
				ps2.setString(3, school);
				ps2.setString(4, email);
				ps2.setBlob(5, bais); // profpic
				ps2.setString(6, bio);
				ps2.setString(7, date);
				ps2.setString(8, phone);
				ps2.setString(9, username);
				ps2.executeUpdate();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
	}

	public void employerRegistration(String companyName, String posComp, String phone, String username, String bio,
			String email, String logo, String date, ImageIcon icon) {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();
			String updateinfo = "UPDATE `employer` SET `company`=? ,`email`=? ,`bio`=? , `position`=? ,`image`=? ,`phone`=?, `joinDate`=?  WHERE `username` = ?";
			try {
				PreparedStatement ps2 = conn.prepareStatement(updateinfo);
				BufferedImage bi = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(),
						BufferedImage.TYPE_INT_RGB);
				Graphics g = bi.createGraphics();
				icon.paintIcon(null, g, 0, 0);
				g.dispose();
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				try {
					ImageIO.write(bi, "jpg", baos);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				byte[] imageInByte = baos.toByteArray();
				ByteArrayInputStream bais = new ByteArrayInputStream(imageInByte);

				ps2.setString(1, companyName);
				ps2.setString(2, email);
				ps2.setString(3, bio);
				ps2.setString(4, posComp);
				ps2.setBlob(5, bais);
				ps2.setString(7, date);
				ps2.setString(6, phone);
				ps2.setString(8, username);

				ps2.executeUpdate();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
	}

	private synchronized Vector<Notification> getPendingNotifications() {
		Vector<Notification> tempnots = new Vector<Notification>();
		try {
			ResultSet rs = null;
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();

			String updateinfo = "SELECT * FROM `notification`";
			try {
				rs = st.executeQuery(updateinfo);
				while (rs.next()) {
					String user = rs.getString("username");
					String message = rs.getString("message");
					Notification temp = new Notification(user, message);
					if (rs.getInt("r") == 1) {
						temp.setHasRead(true);
					} else {
						temp.setHasRead(false);
					}
					tempnots.add(temp);

				}
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
		return tempnots;
	}

	private synchronized void getAllJobs() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(networkedjdbc);
			st = conn.createStatement();

			String updateinfo = "SELECT * FROM `jobs` WHERE `AVAILABLE`=1";
			try {
				Vector<Job> temp = new Vector<Job>();
				rs = st.executeQuery(updateinfo);
				while (rs.next()) {
					int jobID = rs.getInt("JOBID");
					String title = rs.getString("TITLE");
					String emp = rs.getString("EMPLOYER");
					String desc = rs.getString("DESCRIPTION");
					String studentApplied = rs.getString("APPLIEDSTUDENT");
					int avail = rs.getInt("AVAILABLE");

					Job j = new Job();
					j.setUniqueJobID(jobID, emp, title);
					j.setDescription(desc);
					j.setStudUsernames(studentApplied);
					if (avail == 1) {
						j.setAvailable(true);
					} else {
						j.setAvailable(false);
					}

					temp.add(j);

				}
				allJobs = temp;

			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		} catch (SQLException sql) {
			System.out.println("sqle:" + sql.getMessage());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			// e1.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (st != null)
					st.close();
				if (conn != null)
					conn.close();
			} catch (SQLException sql) {
				System.out.println("sqle:" + sql.getMessage());
			}
		}
	}
}