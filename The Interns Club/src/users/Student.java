package users;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Student extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8712570072570629711L;

	public Student(String username) {
		super(username);
		FirstName = "";
		LastName = "";
		Email = "";
		School = "";
		Resume = "";
		Phone = "";
		Bio = "";
		Major = "";
		GPA = "";
	}

	String FirstName, LastName, Email, School, Resume, Phone, Bio, Major, GPA, joinDate;

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	ImageIcon image;

	public ImageIcon getImage() {
		return image;
	}

	public void setImage(ImageIcon image) {
		this.image = image;
	}

	public String getGPA() {
		return GPA;
	}

	public void setGPA(String gPA) {
		GPA = gPA;
	}

	public String getMajor() {
		return Major;
	}

	public void setMajor(String major) {
		Major = major;
	}

	public String getBio() {
		return Bio;
	}

	public void setBio(String bio) {
		Bio = bio;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getSchool() {
		return School;
	}

	public void setSchool(String school) {
		School = school;
	}

	public String getResume() {
		return Resume;
	}

	public void setResume(String resume) {
		if (resume != null) {
			Resume = resume;

		}
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public void sendNotification() {
		// TODO
	}

	public void updateJobs() {
		// TODO
	}

}
