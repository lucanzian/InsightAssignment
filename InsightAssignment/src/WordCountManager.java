import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;

public class WordCountManager 
{
	// The instance variable "map" maps words into integer representing the number of times that word occurred
	public Hashtable<String,Integer> map;
	
	/**
	 * Constructor: initialize the map
	 */
	public WordCountManager()
	{
		map = new Hashtable<String,Integer>();
	}
	
	/**
	 * This method is used to read a file each line is then split into words and the words are added to the map 
	 * @param File file: the file to read 
	 */
	public boolean readFile(File file) 
	{
        // Read the file and process it line by line
		FileReader fr;
		try 
		{
			fr = new FileReader(file);
		    BufferedReader br = new BufferedReader(fr);
		    String line;
		    while ((line = br.readLine()) != null) 
		    {
		    	splitLineAddWords(line);
		    }
		    fr.close();
		}
	    catch (FileNotFoundException e) 
	    {
	    	return false;
		} 
		catch (IOException e) 
		{
			return false;
		}
		
		return true;
	}
	
	
	/**
	 * This method is used to split a line into words, the words are then added to the map 
	 * @param line: the line whose words must be added to the map
	 */
	public void splitLineAddWords(String line) 
	{
	    // Split the String into words
 		StringTokenizer st = new StringTokenizer(line, " \t\n\r\f,;.");
 		
 		// Add each word to the map
 		while (st.hasMoreTokens()) 
 		{
 			addWord(st.nextToken());
 		}
	}
	
	
	/**
	 * This method is used to add a word in the map and increase the counts associated to that word
	 * @param word: the word to add
	 */
	public void addWord(String word) 
	{
		// Get the the value corresponding to that key
		Object obj = map.get(word);
		
		// If the value is null, then it is the first time this word has been seen, hence add the value 1
		if (obj == null) 
		{
			map.put(word, new Integer(1));
		} 
		
		// If this word has been seen in the past increase its count by one unit
		else 
		{
			int i = ((Integer) obj).intValue() + 1;
			map.put(word, new Integer(i));
		}
	}
	
	
	/**
	 * This method is used to print the words and the corresponding counts in a txt file 
	 * @param folderPath: the folder in which to save the file
	 * @param filename: the file name
	 * @return true if the operation was successful, false if there has been a problem 
	 */
	public boolean printWordCount(String folderPath, String filename) 
	{
		// Create a new directory in case it does not exist
		File dir = new File(folderPath);
        dir.mkdirs();   	
		
		// Get the file path and a file instance 
		String filePath = folderPath + "/" + filename;
        File file = new File(filePath);
		     
        // Try to create a file and write into it
        try 
        {   
        	// Create a file 
        	file.createNewFile();
            
            // Write the data into the file
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            Enumeration<String> e = map.keys();
    		while (e.hasMoreElements()) 
    		{
    			String key = (String) e.nextElement();
    			pw.println(key + " " + map.get(key));
    		}	
            pw.flush();
            pw.close();
            f.close();
        } 
        catch (Exception e) 
        {
        	return false;
        }
        
        return true;
	}

	
}
