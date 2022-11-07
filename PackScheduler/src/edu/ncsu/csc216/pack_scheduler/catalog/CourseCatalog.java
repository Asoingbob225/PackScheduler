package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.IOException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Defines functionality to build a catalog of available courses. Maintains a
 * sorted list of courses. Allows loading and saving from/to text files,
 * adding/removing courses, retrieving courses, and retrieving the whole
 * catalog.
 * 
 * @author ctmatias
 * @author rcmidget
 * @author abcoste2
 */
public class CourseCatalog {

	/** A sorted list of available courses */
	private SortedList<Course> catalog;

	/**
	 * Constructs an empty catalog
	 */
	public CourseCatalog() {
		newCourseCatalog();
	}

	/**
	 * Sets the catalog to an empty sorted list
	 */
	public void newCourseCatalog() {
		catalog = new SortedList<Course>();
	}

	/**
	 * Loads course records into the catalog from a file.
	 * 
	 * @param fileName path of file to be read
	 * @throws IllegalArgumentException if the provided file cannot be found
	 */
	public void loadCoursesFromFile(String fileName) {
		try {
			this.catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}

	/**
	 * Adds a Course with the following fields to the catalog. Before adding the
	 * course, check if there is a course with matching name and section in the
	 * catalog, returning false if so. Handles an IllegalArgumentException from
	 * SortedList.add(course) by returning false. Course constructor may throw an
	 * IllegalArgumentException if parameters are invalid.
	 * 
	 * @param name         course name
	 * @param title        course title
	 * @param section      course section
	 * @param credits      number of credits
	 * @param instructorId course's instructor ID
	 * @param meetingDays  days course meets
	 * @param startTime    time course starts
	 * @param endTime      time course ends
	 * @return true if the Course is added and false if the Course already exists in
	 *         the catalog
	 * @throws IllegalArgumentException if there is an error constructing a Course
	 *                                  object
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId,
			int enrollmentCap, String meetingDays, int startTime, int endTime) {
		// Check for courses with same name & section
		for (int i = 0; i < this.catalog.size(); i++) {
			if (name.equals(this.catalog.get(i).getName()) && section.equals(this.catalog.get(i).getSection())) {
				return false;
			}
		}

		Course newCourse = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);

		try {
			this.catalog.add(newCourse);
		} catch (IllegalArgumentException e) {
			// Course already exists
			return false;
		}
		// Successfully added
		return true;
	}

	/**
	 * Removes a course with matching name and section from the catalog. Returns
	 * true if successful, false if there is no matching course.
	 * 
	 * @param name    the name of the course to be removed
	 * @param section the section of the course to be removed
	 * @return true if the Course is removed from the catalog and false if the
	 *         Course is not in the catalog
	 */
	public boolean removeCourseFromCatalog(String name, String section) {

		for (int i = 0; i < catalog.size(); i++) {
			Course course = this.catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				this.catalog.remove(i);
				return true;

			}
		}
		return false;
	}

	/**
	 * Gets the course from catalog using name and section. Since name-section pairs
	 * must be unique, it is not needed to check any other fields for equality. Will
	 * return null if none match the name and section.
	 * 
	 * @param name    the name of the course to get from catalog
	 * @param section section of the course to get from catalog
	 * @return Course from the catalog with same name and section, null if not found
	 */
	public Course getCourseFromCatalog(String name, String section) {
		for (int i = 0; i < catalog.size(); i++) {
			Course course = this.catalog.get(i);
			if (course.getName().equals(name) && course.getSection().equals(section)) {
				return course;
			}
		}
		// Course not found
		return null;
	}

	/**
	 * Returns a String[][] that contains the whole catalog with a course on each
	 * row. Used by the GUI to create a table: the first column is the Course name,
	 * the second is the section, the third is the title, and the fourth is the
	 * meeting days/time. Returns an empty 2D array if there are no courses.
	 *
	 * @return String[][] of catalog in form { {name, section, title,
	 *         meetingString},... }
	 */
	public String[][] getCourseCatalog() {
		if (catalog == null || catalog.isEmpty()) {
			String[][] emptyCatalog = new String[0][0];
			return emptyCatalog;
		}

		String[][] fullCatalog = new String[catalog.size()][4];
		for (int x = 0; x < catalog.size(); x++) {

			Course a = catalog.get(x);
			fullCatalog[x] = a.getShortDisplayArray();

		}
		return fullCatalog;
	}

	/**
	 * Saves the catalog course records to the given file.
	 * 
	 * @param filename the file to save the course catalog to
	 * @throws IllegalArgumentException if there is an issue with saving to the file
	 */
	public void saveCourseCatalog(String filename) {

		try {
			CourseRecordIO.writeCourseRecords(filename, catalog);
		} catch (IOException e) {
			throw new IllegalArgumentException("Unable to write to file " + filename);
		}
	}
}
