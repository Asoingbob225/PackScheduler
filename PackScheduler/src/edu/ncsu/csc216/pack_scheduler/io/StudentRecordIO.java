package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Defines functionality that enables student records to be read from and written to a file
 * 
 * @author ctmatias
 */
public class StudentRecordIO {

	/** The number of entries in a valid student record */
	private static final int VALID_RECORD_LENGTH = 6;
	
	/**
	 * Reads Student records from a file given by specified file.
	 * Ignores lines that have an invalid format or invalid values
	 * 
	 * @param fileName the file to be read
	 * @return the Student records from the file in an SortedList 
	 * @throws FileNotFoundException if specified file does not exist
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(fileName);
		Scanner reader = new Scanner(fis);
		
		SortedList<Student> students = new SortedList<Student>();
		
		while (reader.hasNextLine()) {
		    String record = reader.nextLine();
		    String[] studentInfo = record.split(",");
		    
		    if (studentInfo.length == VALID_RECORD_LENGTH) {
		    	try {
		    		students.add(new Student(studentInfo[0], studentInfo[1], studentInfo[2], studentInfo[3], studentInfo[4], Integer.parseInt(studentInfo[5])));
		    	} catch(IllegalArgumentException e) {
		    		// Ignore invalid student
		    	}
		    }
		}
		
		reader.close();
		return students;
	}

	/**
	 * Writes a Student(s) to a specified file one record at a time
	 * 
	 * @param fileName the file to be written to
	 * @param studentDirectory list of Students to be written to the specified file
	 * @throws IOException if unable to write to specified file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		File recordsFile = new File(fileName);
		PrintStream writer = new PrintStream(recordsFile);
		
		for (int i = 0; i < studentDirectory.size(); i++) {
			writer.println(studentDirectory.get(i).toString());
		}
		
		writer.close();
	}
}
