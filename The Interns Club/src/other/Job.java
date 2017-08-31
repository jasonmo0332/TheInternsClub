package other;

import java.io.Serializable;

public class Job implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7809700312155414373L;

	String Username, JobTitle, description, studUsernames, recLetters;


	boolean available = true;

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void setUniqueJobID(int uniqueJobID) {
		UniqueJobID = uniqueJobID;
	}

	int UniqueJobID;

	public int getUniqueJobID() {
		return UniqueJobID;
	}

	public void setUniqueJobID(int uniqueJobID, String username, String jt) {
		UniqueJobID = uniqueJobID;
		this.Username = username;
		this.JobTitle = jt;
	}

	public Job() {

	}

	public void acceptStudent(String studentID) {
		// TODO
	}

	public String getUsername() {
		return Username;
	}

	public void setUsername(String username) {
		Username = username;
	}

	public String getJobTitle() {
		return JobTitle;
	}

	public void setJobTitle(String jobTitle) {
		JobTitle = jobTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStudUsernames() {
		return studUsernames;
	}

	public void setStudUsernames(String studUsernames) {
		this.studUsernames = studUsernames;
	}

	public String getRecLetters() {
		return recLetters;
	}

	public void setRecLetters(String recLetters) {
		this.recLetters = recLetters;
	}

	public void addRecommendation(/* ProfessorName, StudentName, Filepath */) {

	}

	public void rejectStudent(String StudentID) {

	}

	public void addStudentApplied(String StudentID) {

	}

	public boolean isProfileVisible() {
		return true;
	}

}
