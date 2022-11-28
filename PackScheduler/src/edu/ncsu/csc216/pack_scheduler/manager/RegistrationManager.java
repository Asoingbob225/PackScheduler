package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * Manages registration process and contains registrar inner class. Maintains a
 * static singleton instance of itself so that there is only one manager per
 * application run. Also maintains an instance each of CourseCatalog and
 * StudentDirectory along with a current User and a registrar User. Handles
 * logins and enrolling/dropping students in/from courses. To allow controller
 * to update the CourseCatalog and the StudentDirectory, there are getters for
 * each.
 * 
 * @author abcoste2, cbthomp3, rdbryan2
 */
public class RegistrationManager {

	/** Instance of Registration */
	private static RegistrationManager instance;
	/** Catalog of available courses */
	private CourseCatalog courseCatalog;
	/** Directory of current students */
	private StudentDirectory studentDirectory;
	/** Directory of current faculty */
	private FacultyDirectory facultyDirectory;
	/** Registrar user */
	private User registrar;
	/** Current user */
	private User currentUser;
	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** Name of registrar file for current user */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * Private constructor for the RegistrationManager class that creates a
	 * registrar object
	 */
	private RegistrationManager() {
		createRegistrar();
		studentDirectory = new StudentDirectory();
		courseCatalog = new CourseCatalog();
		facultyDirectory = new FacultyDirectory();
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c Course to enroll in
	 * @return true if enrolled
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c Course to drop
	 * @return true if dropped
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 */
	public void resetSchedule() {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * Creates a registrar object
	 * 
	 * @throws IllegalArgumentException if a registrar object cannot be created due
	 *                                  to an IOException
	 */
	private void createRegistrar() {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Converts an unhashed password to a hashed one
	 * 
	 * @param pw the password to be hashed
	 * @return a hashed password
	 * @throws IllegalArgumentException if there is an error finding the algorithm
	 */
	private String hashPW(String pw) {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * Method used to ensure that only one instance of the RegistrationManager class
	 * is created because the GUI needs to interact with the same instance of the
	 * class
	 * 
	 * @return the instance of the RegistrationManager class
	 */
	public static RegistrationManager getInstance() {
		if (instance == null) {
			instance = new RegistrationManager();
		}
		return instance;
	}

	/**
	 * Returns a CourseCatalog object containing a list of all the Courses and their
	 * information
	 * 
	 * @return an object of type CourseCatalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * Returns a StudentDirectory object containing the list of student objects
	 * 
	 * @return a StudentDirectory object
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}
	
	/**
	 * Returns a FacultyDirectory object containing the list of faculty objects
	 * 
	 * @return a FacultyDirectory object
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * Logs in the current user, first attempting to get a student from the
	 * directory and setting the User object (representing the current user) equal
	 * to the retrieved student object, and then if there is not a student with that
	 * id and password, it checks for a registrar with that id.
	 * 
	 * @param id       the id of the student to be logged in
	 * @param password the password of the student to be logged in
	 * @return true if the student/registrar can be logged in
	 * @throws IllegalArgumentException if user doesn't exist
	 */
	public boolean login(String id, String password) {
		// New user cannot log in while another is already logged in
		if (currentUser != null) {
			return false;
		}
		String localHashPW = hashPW(password);
		
		Student s = studentDirectory.getStudentById(id);

		if (s != null && s.getPassword().equals(localHashPW)) {
			currentUser = s;
			return true;
		}
		
		Faculty f = facultyDirectory.getFacultyById(id);
		
		if (f != null && f.getPassword().equals(localHashPW)) {
			currentUser = f;
			return true;
		}
		
		
		boolean registrarLogin = false;
		if (registrar.getId().equals(id)) {
			registrarLogin = true;
			if (registrar.getPassword().equals(localHashPW)) {
				currentUser = registrar;
				return true;
			}
		}
		if (s != null || f != null || registrarLogin) {
			return false;
		} else {
			throw new IllegalArgumentException("User doesn't exist.");
		}

	}

	/**
	 * Logs out the current user of the program, clearing all fields of the current
	 * User object.
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * Returns the current user as a User object
	 * 
	 * @return currentUser the user currently using the program as a User object
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * Clears the current catalog and student directory
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Static constructor for the inner Registrar class, which creates a single
	 * instance of the class in order to achieve a singleton structure
	 * 
	 * @author chase
	 *
	 */
	private static class Registrar extends User {

		/**
		 * Create a registrar user.
		 * 
		 * @param firstName first name of user
		 * @param lastName  last name of user
		 * @param id        id of user
		 * @param email     email of user
		 * @param hashPW    hashed password of user
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}
