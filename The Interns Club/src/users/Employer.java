package users;

import java.io.Serializable;

import javax.swing.ImageIcon;

import other.Job;

public class Employer extends User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9183882178320919410L;

	public Employer(String username) {
		super(username);
		Company = "";
		Email = "";
		School = "";
		Phone = "";
		desc = "";
		position = "";
	}

	String Company, Email, School, Phone, desc, position, joinDate;

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}

	ImageIcon Logo;

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public String getCompany() {
		return Company;
	}

	public void setCompany(String company) {
		Company = company;
	}

	public ImageIcon getLogo() {
		return Logo;
	}

	public void setLogo(ImageIcon logo) {
		Logo = logo;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public void updateJobs() {
		// TODO
	}

	public void createJob(Job job) {

	}
}
