/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Static methods for reading Course records from text files and writing a set
 * of Course records to a file. Separating these from WolfScheduler enforces
 * encapsulation of file IO logic, enhances readability, and could potentially
 * prevent future refactoring if a future extension of WolfScheduler needed to
 * write Courses to files as well.
 * 
 * @author Sarah Heckman
 * @author abcoste2
 */
public class CourseRecordIO {

	/**
	 * Reads course records from a file and generates a list of valid Courses. Any
	 * invalid Courses are ignored. If the file to read cannot be found or the
	 * permissions are incorrect a File NotFoundException is thrown.
	 * 
	 * @param fileName file to read Course records from
	 * @return a list of valid Courses
	 * @throws FileNotFoundException if the file cannot be found or read
	 */
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
		Scanner fileReader = new Scanner(new FileInputStream(fileName)); // Create a file scanner to read the file
		SortedList<Course> courses = new SortedList<Course>(); // Create an empty array of Course objects
		while (fileReader.hasNextLine()) { // While we have more lines in the file
			try { // Attempt to do the following
					// Read the line, process it in readCourse, and get the object
					// If trying to construct a Course in readCourse() results in an exception, flow
					// of control will transfer to the catch block, below
				Course course = readCourse(fileReader.nextLine());

				// Create a flag to see if the newly created Course is a duplicate of something
				// already in the list
				boolean duplicate = false;
				// Look at all the courses in our list
				for (int i = 0; i < courses.size(); i++) {
					// Get the course at index i
					Course current = courses.get(i);
					// Check if the name and section are the same
					if (course.getName().equals(current.getName())
							&& course.getSection().equals(current.getSection())) {
						// It's a duplicate!
						duplicate = true;
						break; // We can break out of the loop, no need to continue searching
					}
				}
				// If the course is NOT a duplicate
				if (!duplicate) {
					courses.add(course); // Add to the List!
				} // Otherwise ignore
			} catch (IllegalArgumentException e) {
				// The line is invalid b/c we couldn't create a course, skip it!
			}
		}
		// Close the Scanner b/c we're responsible with our file handles
		fileReader.close();
		// Return the List with all the courses we read!
		return courses;
	}

	/**
	 * Parse course parameters from a single line and creates a new course object
	 * from them. If the line is not valid, either this method or the Course
	 * constructor will throw an IllegalArgumentException. There is no use case for
	 * reading individual courses in the main flow, but this allows for more
	 * readability and ease of debugging in readCourseRecords()
	 *
	 * @param nextLine the next line
	 * @return course a new Course object from line
	 * @throws IllegalArgumentException If the amount of elements on the line are
	 *                                  different than expected. The Course
	 *                                  constructor will throw an
	 *                                  IllegalArgumentException if the parameters
	 *                                  from the line are invalid.
	 */
	private static Course readCourse(String nextLine) {
		Course course;

		// There are multiple points where method can be interrupted, so use a
		// try-with-resources
		try (Scanner lineReader = new Scanner(nextLine);) {
			lineReader.useDelimiter(",");

			String name, title, section, instructor, meetingDay;
			int creditHours, enrollmentCap, startTime, endTime;

			name = lineReader.next();
			title = lineReader.next();
			section = lineReader.next();
			creditHours = Integer.parseInt(lineReader.next());
			instructor = lineReader.next();
			enrollmentCap = Integer.parseInt(lineReader.next());
			meetingDay = lineReader.next();

			// Call correct constructor if the course is arranged
			if ("A".equals(meetingDay)) {
				course = new Course(name, title, section, creditHours, instructor, enrollmentCap, meetingDay);
			} else {
				startTime = Integer.parseInt(lineReader.next());
				endTime = Integer.parseInt(lineReader.next());

				course = new Course(name, title, section, creditHours, null, enrollmentCap, meetingDay, startTime, endTime);
			}
			
			if (RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructor) != null) {
				course.setInstructorId(instructor);
				RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructor).getSchedule().addCourseToSchedule(course);
			}
		

			// If there are remaining elements, throw exception
			if (lineReader.hasNext()) {
				throw new IllegalArgumentException();
			}
		} catch (NoSuchElementException e) {
			throw new IllegalArgumentException();
		}

		return course;
	}
	
	/**
	 * Writes the given list of Courses to a file. PrintStream object will throw an
	 * (uncaught) IOException if there is a problem writing to the file.
	 * 
	 * @param fileName   file to write schedule of Courses to
	 * @param courses list of Courses to write
	 * @throws IOException if cannot write to file
	 */
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));

		for (int i = 0; i < courses.size(); i++) {
			fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();
	}

}
