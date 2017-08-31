package networking;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

import other.Job;
import users.Employer;
import users.Professor;
import users.Student;

public class Client extends Thread {
	private Vector<Job> allJobs;
	private Vector<Student> allStudents;
	private BufferedReader br;
	public int createResult = -1;
	private Employer employer;
	private int jobSentSuccessful = -1;
	private Vector<Notification> notifications;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private Professor professor;
	public boolean received = false;
	private Socket s = null;
	private Student student;
	private String resume;
	private String recLetter;

	public void setRecLetter(String recLetter) {
		this.recLetter = recLetter;
	}

	public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public boolean success = false;

	public int studentSuccess = -1;

	public String username;

	public Client(String hostname, int port) throws UnknownHostException, IOException {

		s = new Socket(hostname, port);

		oos = new ObjectOutputStream(s.getOutputStream());
		ois = new ObjectInputStream(s.getInputStream());

	}

	public int getStudentSuccess() {
		return studentSuccess;
	}

	public void clearJobs() {
		allJobs = null;
	}

	public boolean hasJobs() {
		return allJobs != null;
	}

	public void addJob(Employer e, Job j) {
		try {
			Message cm = new Message("Add_job", e, j);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void checkedNotifications() {
		try {
			Message cm = new Message("checkedNotifications", "null");
			cm.setPendingNotifs(notifications);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void checkForUpdates() {
		try {
			Message cm = new Message("getNotifs", username);
			oos.writeObject(cm);
			oos.flush();
			Message cm2 = new Message("getJobs", username);
			oos.writeObject(cm2);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void close() {
		try {
			if (br != null)
				br.close();
			if (oos != null)
				oos.close();
			if (ois != null)
				ois.close();
			if (s != null)
				s.close();
		} catch (IOException ioe) {
			System.out.println(ioe.getMessage());
		}
	}

	public void createEmployer(String type, Employer employer) {
		try {
			Message cm = new Message(type, employer);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void createProfessor(String type, Professor professor) {
		try {
			Message cm = new Message(type, professor);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void createStudent(String type, Student student) {
		try {
			Message cm = new Message(type, student);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendResume(String user, String resume, boolean isPublic) {
		try {
			Message cm = new Message("addResume");
			cm.setUsername(user);
			cm.setMessage(resume);
			cm.setBool(isPublic);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendRecLetter(String user, String recLetter, int jobID) {
		try {
			Message cm = new Message("addRecLetter");
			cm.setUsername(user);
			cm.setMessage(recLetter);
			cm.setID(jobID);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public Vector<Job> getAllJobs() {
		return allJobs;
	}

	public Vector<Student> getAllStudents() {
		return allStudents;
	}

	public Employer getEmployer() {
		return employer;
	}

	public Vector<Notification> getNotifications() {
		return notifications;
	}

	public Professor getProfessor() {
		return professor;
	}

	public Student getStudent() {
		return student;
	}

	public int isJobSentSuccessful() {
		return jobSentSuccessful;
	}

	@Override
	public void run() {
		try {
			while (true) {
				Message cm = (Message) ois.readObject();
				if (cm != null) {
					// System.out.println("Client " + username + " received type
					// " + cm.getType());
					if (cm.getType().equals("login") && cm.getUsername().equals(username)) {
						received = true;
						if (cm.getMessage().equals("true")) {
							success = true;
							if (cm.getEmployer() != null) {
								this.employer = cm.getEmployer();
							}
							if (cm.getProfessor() != null) {
								this.professor = cm.getProfessor();
							}
							if (cm.getStudent() != null) {
								this.student = cm.getStudent();
							}
							if (cm.getNotifications() != null) {
								this.notifications = cm.getNotifications();
							}
						}
					} else if (cm.getType().equals("create") && cm.getUsername().equals(username)) {
						createResult = Integer.parseInt(cm.getMessage());
					} else if (cm.getType().equals("Add_job")) {
						setJobSentSuccessful(cm.getJobSuccess());
					} else if (cm.getType().equals("getStudent")) {
						if (cm.getStudent() == null) {
							studentSuccess = 0;
						} else {
							studentSuccess = 1;
							this.student = cm.getStudent();
						}
					} else if (cm.getType().equals("getJobs")) {
						allJobs = cm.getJobs();
					} else if (cm.getType().equals("getJobsByUser")) {
						allJobs = cm.getJobs();
					} else if (cm.getType().equals("getNotifs")) {
						if (cm.getPendingNotifs() != null) {
							this.notifications = cm.getPendingNotifs();
						}
					} else if (cm.getType().equals("studentFromID")) {
						if (cm.getStudents() != null) {
							this.allStudents = cm.getStudents();
						} else {
							this.allStudents = new Vector<Student>();
						}
					}
				}
			}
		} catch (EOFException eoe) {
			// donothing
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void getStudentByJob(int jobID) {
		try {
			Message cm = new Message("studentFromID");
			cm.setID(jobID);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(Employer employer, String username, String type) {
		try {
			Message cm = new Message(type, employer, username);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(File file) {
		try {
			Message cm = new Message("file", file);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(Professor professor, String username, String type) {
		try {
			Message cm = new Message(type, professor, username);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(String type, Job j, String username) {
		try {
			Message cm = new Message(type, username);
			cm.setJob(j);
			cm.setNeedsReturn(false);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}

	}

	public void sendMessage(String type, String message) {
		try {
			Message cm = new Message(type, message);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}

	}

	public void sendMessage(String type, String username, String password, String loginType) {
		this.username = username;
		try {
			Message cm = new Message(type, username, password, loginType);
			cm.setJustToSelf(true);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendMessage(Student student, String username, String type) {
		try {
			Message cm = new Message(type, student, username);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void sendAcceptedStudent(Job job, String acceptedUsername, Vector<Student> rejectedStudent) {
		try {
			Message cm = new Message("acceptStudent", acceptedUsername);
			cm.setJob(job);
			cm.setStudents(rejectedStudent);
			oos.writeObject(cm);
			oos.flush();
		} catch (IOException ioe) {
			System.out.println("ioe: " + ioe.getMessage());
		}
	}

	public void setAllJobs(Vector<Job> allJobs) {
		this.allJobs = allJobs;
	}

	public void setAllStudents(Vector<Student> allStudents) {
		this.allStudents = allStudents;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public void setJobSentSuccessful(int jobSentSuccessful) {
		this.jobSentSuccessful = jobSentSuccessful;
	}

	public void setNotifications(Vector<Notification> notifications) {
		this.notifications = notifications;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

}
