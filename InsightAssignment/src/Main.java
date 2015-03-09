import java.io.File;
import java.util.Arrays;

/**
 * 
 * @author lucacanzian
 * Program developed for the Insight Data Engineering Assignment 
 */
public class Main 
{
	public static void main(String[] args) 
	{
		System.out.println("Start program...");
		
		// Define the folder where the code is located, where the inputs are located, and where the otuputs must be saved 
		String current_folder = System.getProperty("user.dir");
		String input_folder = current_folder + "/../wc_input/";
		String output_folder = current_folder + "/../wc_output/";
		String wc_result = "wc_result.txt";
		String med_result = "med_result.txt";
		
		// Get all the input files and sort them in alphabetical order
		File[] listFiles = new File(input_folder).listFiles();
		Arrays.sort(listFiles);
		
		// Initialize a WordCountManager and fill it with words by reading all the input files, finally print the words and 
		// counts in the output file 
		WordCountManager wcm = new WordCountManager();
		for (File file : listFiles) 
		{
		    if (file.isFile()) 
		    {
		    	wcm.readFile(file);
		    }
		}
		wcm.printWordCount(output_folder, wc_result);
		
		
		// Initialize a RunningMedianManager, read the input fils in alphabetical order, and finally print the running median 
		RunningMedianManager rmm = new RunningMedianManager();
		for (File file : listFiles) 
		{
		    if (file.isFile()) 
		    {
		    	rmm.readFile(file);
		    }
		}
		rmm.printRunningMedian(output_folder, med_result);
		

		System.out.println("Finished!");
	}
	
}
