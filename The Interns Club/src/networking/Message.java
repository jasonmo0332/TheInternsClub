package networking;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;

import other.Job;
import users.Employer;
import users.Professor;
import users.Student;

public class Message implements Serializable {
	private static final long serialVersionUID = 1L;
	private String sentUser = "";
	private boolean neededBool;
	private int ID;

	private Employer employer;

	public File file;

	private Job job;

	private boolean justToSelf = false;

	public String loginType, owner;

	private String message;

	private boolean needsReturn = true;
	private Notification notification;
	private Vector<Notification> pendingNotifs = new Vector<Notification>();
	private Vector<Job> jobs;
	private Vector<Student> students;
	private Professor professor;
	private int jobSuccess = -1;
	public int serverID = -1;
	private Student student;
	private String type;

	public String username, password;

	private ArrayList<String> usernames;

	public Message(String type) {
		this.type = type;
	}

	public Message(String type, Employer employer) {
		this.employer = employer;
	}

	public Message(String type, Employer employer, Job j) {
		this.type = type;
		this.employer = employer;
		this.job = j;
	}

	public Message(String type, Employer employer, String username) {
		this.type = type;
		this.employer = employer;
		this.username = username;
	}

	public Message(String type, File file) {
		this.file = file;
		this.type = type;
	}

	public Message(String type, Professor professor) {
		this.type = type;
		this.professor = professor;
	}

	public Message(String type, Professor professor, String username) {
		this.type = type;
		this.username = username;
		this.professor = professor;
	}

	public Message(String type, String message) {
		this.type = type;
		this.message = message;
	}

	public Message(String type, String username, String password, String loginType) {
		this.username = username;
		this.password = password;
		this.type = type;
		this.loginType = loginType;
	}

	public Message(String type, Student student) {
		this.type = type;
		this.student = student;
	}

	public Message(String type, Student student, String username) {
		this.type = type;
		this.username = username;
		this.student = student;

	}

	public void addNotification(Notification n) {
		pendingNotifs.add(n);
	}

	public Employer getEmployer() {
		return employer;
	}

	public int getID() {
		return ID;
	}

	public Job getJob() {
		return job;
	}

	public Vector<Job> getJobs() {
		return jobs;
	}

	public int getJobSuccess() {
		return jobSuccess;
	}

	public String getLoginType() {
		return loginType;
	}

	public String getMessage() {
		return this.message;
	}

	public Notification getNotification() {
		return notification;
	}

	public Vector<Notification> getNotifications() {
		return pendingNotifs;
	}

	public String getOwner() {
		return owner;
	}

	public String getPassword() {
		return password;
	}

	public Vector<Notification> getPendingNotifs() {
		return pendingNotifs;
	}

	public Professor getProfessor() {
		return professor;
	}

	public String getSentUser() {
		return sentUser;
	}

	public Student getStudent() {
		return student;
	}

	public Vector<Student> getStudents() {
		return students;
	}

	public String getType() {
		return this.type;
	}

	public String getUsername() {
		return username;
	}

	public ArrayList<String> getUsernames() {
		return usernames;
	}

	public boolean isBool() {
		return neededBool;
	}

	public boolean isJustToSelf() {
		return justToSelf;
	}

	public boolean isNeedsReturn() {
		return needsReturn;
	}

	public void setBool(boolean neededBool) {
		this.neededBool = neededBool;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public void setJobs(Vector<Job> jobs) {
		this.jobs = jobs;
	}

	public void setJobSuccess(int jobSuccess) {
		this.jobSuccess = jobSuccess;
	}

	public void setJustToSelf(boolean justToSelf) {
		this.justToSelf = justToSelf;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setNeedsReturn(boolean needsReturn) {
		this.needsReturn = needsReturn;
	}

	public void setNotification(Notification notification) {
		this.notification = notification;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPendingNotifs(Vector<Notification> pendingNotifs) {
		this.pendingNotifs = pendingNotifs;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public void setSentUser(String sentUser) {
		this.sentUser = sentUser;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public void setStudents(Vector<Student> students) {
		this.students = students;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setUsernames(ArrayList<String> usernames) {
		this.usernames = usernames;
	}
}
