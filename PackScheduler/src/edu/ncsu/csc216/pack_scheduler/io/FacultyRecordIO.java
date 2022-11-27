/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Defines functionality that enables faculty records to be read from and written to a file
 * @author Adharsh
 *
 */
public class FacultyRecordIO {

	/** The number of entries in a valid faculty record */
	private static final int VALID_RECORD_LENGTH = 6;
	
	/**
	 * Reads faculty records from a file given by specified file.
	 * Ignores lines that have an invalid format or invalid values
	 * 
	 * @param fileName the file to be read
	 * @return the Faculty records from the file in a LinkedList 
	 * @throws FileNotFoundException if specified file does not exist
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		FileInputStream fis = new FileInputStream(fileName);
		Scanner reader = new Scanner(fis);
		
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		
		while (reader.hasNextLine()) {
		    String record = reader.nextLine();
		    String[] facultyInfo = record.split(",");
		    
		    if (facultyInfo.length == VALID_RECORD_LENGTH) {
		    	try {
		    		faculty.add(new Faculty(facultyInfo[0], facultyInfo[1], facultyInfo[2], facultyInfo[3], facultyInfo[4], Integer.parseInt(facultyInfo[5])));
		    	} catch(IllegalArgumentException e) {
		    		// Ignore invalid faculty
		    	}
		    }
		}
		
		reader.close();
		return faculty;
	}

	/**
	 * Writes a faculty(s) to a specified file one record at a time
	 * 
	 * @param fileName the file to be written to
	 * @param facultyDirectory list of faculty to be written to the specified file
	 * @throws IOException if unable to write to specified file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		File recordsFile = new File(fileName);
		PrintStream writer = new PrintStream(recordsFile);
		
		for (int i = 0; i < facultyDirectory.size(); i++) {
			writer.println(facultyDirectory.get(i).toString());
		}
		
		writer.close();
	}
}
