package dev.okadarov.programmingtask1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Application {

	public static void main(String[] args) {
		
		// Generate new String body for the file
		String generatedBody = readFile( "test.txt" );
		// Create and write to new file
		writeToFile( generatedBody, "dictionary.txt" );

	}
	
	// Read file content and return new file body
	public static String readFile ( String filename ) {
		String newTextFileContents = "";
		File file = new File(filename);
		
		// Open and read file
		try (
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
		) {
			
			// Read file and generate String
				String line = bufferedReader.readLine();
				String text = "";
				while ( line != null ) {
					text += " "+line;
					line = bufferedReader.readLine();
				}
			// Remove all symbols from text
				text = text.replaceAll("[^a-zA-Z0-9']", " "); 
				
			// Generate TreeMap dictionary
				String[] words = text.split(" "); // Create an array with all words
				TreeMap<String,Integer> dictionary = new TreeMap<String,Integer>( String.CASE_INSENSITIVE_ORDER );
				for ( String word : words ) {
					if ( word.length() == 0 ) continue; // Skip empty spaces
					if ( dictionary.containsKey(word) ) {
						dictionary.put(word,dictionary.get(word) + 1);
					} else {
						dictionary.put(word,1);
					}
					
				}
			
			// Generate new file contents
				for ( Entry<String, Integer> entry : dictionary.entrySet()  ) {
					newTextFileContents += entry.getKey()+" : "+entry.getValue()+" \n";
				}
				
		} catch (FileNotFoundException e) {
			closeProgramWithError("File '"+filename+"' has not been found!");
			
		} catch (IOException e) {
			closeProgramWithError("There was an error while trying to open/read file  '"+filename+"' !");
		}
		
		return newTextFileContents;
		
	}
	public static void writeToFile ( String fileBody, String filename  ) {
	
		File file= new File( filename );
		// Create new file if file does not exist 
			if ( !file.exists( ) ) {
				try {
					file.createNewFile( );
			    } catch (IOException e) {
			    	closeProgramWithError("Failed to create/check file '"+filename+"'.");
			    }
				
			}
		
		// Write data to file
			try (
				FileWriter fileWriter = new FileWriter( filename );
			) {
				fileWriter.write(fileBody);
				fileWriter.flush( );
				closeProgramWithError("Writing to file '"+filename+"' was successfull ");
		    } catch (IOException e) {
		    	closeProgramWithError("There was an error while trying to write to '"+filename+"'");
		    }
	}
	
	public static void closeProgramWithError ( String message ) {
		System.out.println(message);
		System.exit(1);
	}

}
