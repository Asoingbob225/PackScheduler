package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * List of courses that a user is enrolled in. Maintains a title for the
 * schedule and an instance of the custom ArrayList with type Course. Includes
 * functionality for adding/removing courses, updating the title, clearing the
 * schedule, and checking if a course can be added.
 * 
 * @author chase
 */
public class Schedule {

    /** The title of the schedule */
    private String title;
    
    /** The schedule of courses */
    private ArrayList<Course> schedule;
    
    /**
     * Constructor for the schedule class
     */
    public Schedule() {
        schedule = new ArrayList<Course>();
        title = "My Schedule";
    }
    
    /**
     * Public getter method for the title of the schedule
     * @return  title of this schedule instance
     */
    public String getTitle() {
        return title;
    }

    /**
     * Adds a given course object to the schedule
     * @param course the course to be added to the schedule
     * @return true if the course has been added
     * @throws IllegalArgumentException if the course is a duplicate or if the course causes a conflict with other activities
     */
    public boolean addCourseToSchedule(Course course) {
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isDuplicate(course)) {
                throw new IllegalArgumentException("You are already enrolled in " + course.getName());
            }
        }
        try {
            for (int i = 0; i < schedule.size(); i++) {
                schedule.get(i).checkConflict(course);
            }
        } catch (ConflictException e) {
            throw new IllegalArgumentException("The course cannot be added due to a conflict.");
        }
        schedule.add(schedule.size(), course);
        return true;
    }

    /**
     * Returns the schedule of courses using the getShortDisplayArray() method
     * @return a 2d array of course information
     */
    public String[][] getScheduledCourses() {
        // If the schedule is null or empty return a empty String[][] to avoid a NullPointerException
        if (schedule == null || schedule.isEmpty()) {
            String[][] emptySchedule = new String[0][0];
            return emptySchedule;
        }
        String[][] returnString = new String[schedule.size()][5];
        for (int i = 0; i < schedule.size(); i++) {
            Course c = schedule.get(i);
            returnString[i] = c.getShortDisplayArray();

        }
        return returnString;
    }

    /**
     * Removes the given course object from the ArrayList of Course objects
     * @param course the course to remove
     * @return true if the course has been removed and false if it could not be removed
     */
    public boolean removeCourseFromSchedule(Course course) {
        if (schedule == null || schedule.isEmpty()) {
            return false;
        }
        
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isDuplicate(course)) {
                schedule.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Sets the title of this schedule to the given title
     * @param title the title to set
     * @throws IllegalArgumentException if the title is null or an empty string
     */
    public void setTitle(String title) {
        if (title == null || "".equals(title)) {
            throw new IllegalArgumentException("Title cannot be null.");
        } else {
            this.title = title;
        }
        
    }

    /**
     * Resets the title of the schedule to the default value and clears the list of courses
     */
    public void resetSchedule() {
        title = "My Schedule";
        schedule = new ArrayList<Course>();
    }
    
    /**
     * Calculates the total number of credits in a schedule and returns it
     * @return the total number of credits in a schedule
     */
    public int getScheduleCredits() {
        if (schedule.size() == 0) {
            return 0;
        }
        int totalCredits = 0;
        for (int i = 0; i < schedule.size(); i++) {
            totalCredits += schedule.get(i).getCredits();
        }
        return totalCredits;
    }
    
    /**
     * Method used by RegistrationManager to check if a course can be added to the schedule
     * @param course the course to check
     * @return true if it can be added, false otherwise
     */
    public boolean canAdd(Course course) {
        for (int i = 0; i < schedule.size(); i++) {
            if (schedule.get(i).isDuplicate(course)) {
                return false;
            }
        }
        try {
            for (int i = 0; i < schedule.size(); i++) {
                schedule.get(i).checkConflict(course);
            }
        } catch (ConflictException e) {
            return false;
        }
        return true;
    }

}
