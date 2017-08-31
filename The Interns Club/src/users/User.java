package users;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2163588333193654567L;
	protected String username, profilePic;

	// Each user will have some form of update or server connection? so we'll
	// have
	// Protected TICServer server;
	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public User(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
