package networking;

import java.io.Serializable;

public class Notification implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5956644631235446950L;
	private String username;
	private String message;
	private boolean hasRead = false;

	public boolean isHasRead() {
		return hasRead;
	}

	public void setHasRead(boolean hasRead) {
		this.hasRead = hasRead;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Notification(String username, String notificationContents) {
		this.username = username;
		this.message = notificationContents;
	}
}
