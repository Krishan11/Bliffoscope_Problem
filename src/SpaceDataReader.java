

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Reads space data when provided in two dimensional format
 * Hence used to read both space object signature and bliffoscope data
 *
 */
public class SpaceDataReader {

	//Height of the space object
	int height;
	//Width of the space object
	int width;
	int[][] spaceData;
	
	public SpaceDataReader() {

	}

	
	public int getHeight() {
		return height;
	}



	public int getWidth() {
		return width;
	}


	/**
	 * Reads the space data file and converts into 2D array
	 * @param filePath path to data file
	 * @return 2D representation of space data
	 * @throws SpaceObjectException Thrown when the method fails to read the file
	 */
	public int[][] readSpaceData(String filePath) throws SpaceObjectException {
		
		
		File f = new File(filePath);
		ArrayList<String> fileContents;
		try {
			fileContents = readFile(f);
			parseFileContents(fileContents);
		} catch (SpaceObjectException e) {
			e.printStackTrace();
			throw new SpaceObjectException(e.getMessage());
		}
		return spaceData;
		
	}
	
	
	private ArrayList<String> readFile(File f) throws SpaceObjectException {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(f));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new SpaceObjectException("Error while reading "+f.getAbsolutePath()+". Error is "+e.getMessage());
		}		

		ArrayList<String> lines = new ArrayList<String>();
		
		String lineInFile = "";
		
		while(lineInFile != null) {
			try {
				lineInFile = reader.readLine();
				lines.add(lineInFile);
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		
			try {
				reader.close();
			} catch (IOException e) {
				throw new SpaceObjectException("Error in closing reader. Resouce leak.");
			}
			
		
		return lines;
		
	}
	
	/**
	 * Converts list of strings to 2D array
	 * @param fileContents contents of space data file
	 */
	private void parseFileContents(ArrayList<String> fileContents) {
		
		//For simplicity, assuming that definition file contains square matrix.
		
		//Lines are of varied lengths
		//So calculate max width to identify matrix columns count
		int maxWidth = 0;
		for(int i =0; i < fileContents.size();i++){
			if(fileContents.get(i)!= null && fileContents.get(i).length() > maxWidth) {
				maxWidth = fileContents.get(i).length();
			}
		}
		height = fileContents.size();
		width  = maxWidth;
		spaceData = new int[height][width];
		
		for(int row = 0; row < height; row++) {
			
			String line = fileContents.get(row);
			if(line == null) {
				return;
			}
			for(int i =0; i < line.length(); i++) {
				char c = line.charAt(i);
				if(c == ' ') {
					spaceData[row][i] = 0;
				} else {
					spaceData[row][i] = 1;
				}
			}
		}
	}
	
	
	
	
}
