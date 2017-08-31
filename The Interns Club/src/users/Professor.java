package users;

import java.io.Serializable;

import javax.swing.ImageIcon;

public class Professor extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6953645994722850525L;

	public Professor(String username) {
		super(username);
		// TODO Auto-generated constructor stub
		FirstName = "";
		LastName = "";
		Email = "";
		School = "";
		Phone = "";
		Bio = "";
	}

	String FirstName, LastName, Email, School, Phone, Bio, joinDate;

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

	public void recommendJobs() {
		// TODO
	}

	public void currentJobList(/* JobList */) {
		// TODO
	}

}
