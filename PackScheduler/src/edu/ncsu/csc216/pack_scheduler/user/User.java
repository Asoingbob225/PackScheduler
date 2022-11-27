package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

/**
 * Represents a user, has subclasses Student and Faculty
 * 
 * @author Davis Bryant
 */
public abstract class User {

	/** User's first name. */
	private String firstName;
	/** User's last name. */
	private String lastName;
	/** User's id. */
	private String id;
	/** User's email. */
	private String email;
	/** User's password. */
	private String password;
	
	/**
	 * Constructor that calls abstract class
	 * 
	 * @param firstName User's first name
	 * @param lastName  User's last name
	 * @param id        User's id number as a string
	 * @param email     User's email
	 * @param password  User's password
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		setFirstName(firstName);
		setLastName(lastName);
		setId(id);
		setEmail(email);
		setPassword(password);
	}

	/**
	 * gets User's email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * sets User's email, checking that it meets requirements.
	 *
	 * @param email the email to set
	 * @throws IllegalArgumentException if the '@' or '.' character are missing or
	 *                                  the last '.' is before the last '@'
	 */
	public void setEmail(String email) {
		if (email == null || "".equals(email)) {
			throw new IllegalArgumentException("Invalid email");
		} else if (!email.contains("@")) {
			throw new IllegalArgumentException("Invalid email");
		} else if (!email.contains(".")) {
			throw new IllegalArgumentException("Invalid email");
		} else {
	
			// Iterate through email string in reverse, stopping when either '.' or '@' is
			// found. We know that the string contains both '@' and '.' so we don't need
			// a stopping conditional for the loop
			for (int i = email.length() - 1;; i--) {
				char c = email.charAt(i);
	
				if (c == '.') {
					break; // email is valid so it can stop
				} else if (c == '@') {
					throw new IllegalArgumentException("Invalid email");
				}
	
			}
		}
		this.email = email;
	}

	/**
	 * gets User's password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * sets a User's password.
	 *
	 * @param password the password to set
	 * @throws IllegalArgumentException if password is a null or empty string
	 */
	public void setPassword(String password) {
		if (password == null || "".equals(password)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.password = password;
	}

	/**
	 * gets User's last name.
	 *
	 * @return User's last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * sets User's last name.
	 *
	 * @param lastName the lastName to set
	 * @throws IllegalArgumentException if last name is a null or empty string
	 */
	public void setLastName(String lastName) {
		if (lastName == null || "".equals(lastName)) {
			throw new IllegalArgumentException("Invalid last name");
		}
		this.lastName = lastName;
	}

	/**
	 * gets User's id.
	 *
	 * @return User's id
	 */
	public String getId() {
		return id;
	}

	/**
	 * sets User's id.
	 *
	 * @param id the id to set
	 * @throws IllegalArgumentException if id is a null or empty string
	 */
	protected void setId(String id) {
		if (id == null || "".equals(id)) {
			throw new IllegalArgumentException("Invalid id");
		}
		this.id = id;
	}

	/**
	 * gets User's first name.
	 *
	 * @return User's first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * sets a User's first name.
	 *
	 * @param firstName the User's first name
	 * @throws IllegalArgumentException if Student name is a null or empty string
	 */
	public void setFirstName(String firstName) {
		if (firstName == null || "".equals(firstName)) {
			throw new IllegalArgumentException("Invalid first name");
		}
		this.firstName = firstName;
	}

	/**
	 * Generates a hashCode for User using all fields.
	 *
	 * @return hashCode for User
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password);
	}

	/**
	 * Compares a given object to this User for equality on all fields.
	 * 
	 * @param obj the Object to compare
	 * @return true if obj and this User are the same on all fields.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}
	
	

}