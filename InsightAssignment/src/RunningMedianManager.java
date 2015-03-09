import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class RunningMedianManager 
{
	// I will keep a max and min heap to efficiently compute the median, and I will add each new median to running_median
	// I keep also the sizes of the two heaps since I use these variables often 
	PriorityQueue<Integer> max_heap;
	PriorityQueue<Integer> min_heap;
	int size_min_heap;
	int size_max_heap;
	ArrayList<Float> running_median; 

	/**
	 * Constructor: initialize the map
	 */
	public RunningMedianManager()
	{
		max_heap = new PriorityQueue<Integer>(10, Collections.reverseOrder());
		min_heap = new PriorityQueue<Integer>();
		size_max_heap = 0;
		size_min_heap = 0;
		running_median = new ArrayList<Float>();
	}
	
	/**
	 * This method is used to read a file, each line is then split into words, the number of words is added to the heaps 
	 * and a new element is inserted in running_median
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
		    	splitLineAddNumWords(line);
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
	 * This method is used to split a line into words, the number of words are then added to the heaps and a new element is 
	 * inserted in running_median
	 * @param line: the line whose number of words must be added to the heaps 
	 */
	public void splitLineAddNumWords(String line) 
	{
	    // Split the String into words and add the number of words to the heaps
 		StringTokenizer st = new StringTokenizer(line, " \t\n\r\f,;.");
 		addNumber(st.countTokens());
	}
	
	
	/**
	 * This method is used to add a number to the heaps and update the running median 
	 * @param number: the number to add
	 */
	public void addNumber(int number) 
	{
		// If max_heap is empty then insert the number there 
		if ( size_max_heap == 0 )
		{
			max_heap.add(number);
			size_max_heap++;
		}
		
		// Otherwise if min_heap is empty then insert the number there, but first compare the number in max_heap and the new 
		// number, if they are not compatible then put the number in max_heap into min_heap, and put the new number into max_heap
		else if ( size_min_heap == 0 )
		{
			int num_max_heap = max_heap.peek();
			if (number < num_max_heap)
			{
				min_heap.add(num_max_heap);
				max_heap.remove();
				max_heap.add(number);
			}
			else 
			{
				min_heap.add(number);
			}
			
			size_min_heap++;
		}
		
		// Otherwise add the number in the correct heap 
		else 
		{
			if (number < max_heap.peek())
			{
				max_heap.add(number);
				size_max_heap++;
			}
			else 
			{
				min_heap.add(number);
				size_min_heap++;
			}
		}
		

		// Now I need to re-balance the heaps in case they are not 
		if (size_max_heap > size_min_heap + 1)
		{
			int num_max_heap = max_heap.remove();
			min_heap.add(num_max_heap);
			size_max_heap--;
			size_min_heap++;
		}
		else if (size_min_heap > size_max_heap + 1)
		{
			int num_min_heap = min_heap.remove();
			max_heap.add(num_min_heap);
			size_min_heap--;
			size_max_heap++;
		}
		
		// Finally update the running median
		updateRunMedian();
	}
	
	
	/**
	 * This method is used to update the running median and add it to the list running_median
	 */
	public void updateRunMedian() 
	{		
		// If one heap contain more elements than the other, then the head of that heap is the median.
		// Otherwise the median is the mean between the head of the two heaps  
		if (size_min_heap > size_max_heap)
		{
			int median = min_heap.peek();
			running_median.add( (float)(median*10) / 10.0F );
		}
		else if (size_max_heap > size_min_heap)
		{
			int median = max_heap.peek();
			running_median.add( (float)(median*10) / 10.0F );
		}
		else 
		{
			int head1 = min_heap.peek();
			int head2 = max_heap.peek();
			running_median.add( (float)((head2+head1)*10) / 20.0F );
		}
	}
	
	
	/**
	 * This method is used to print the running median in a txt file 
	 * @param folderPath: the folder in which to save the file
	 * @param filename: the file name
	 * @return true if the operation was successful, false if there has been a problem 
	 */
	public boolean printRunningMedian(String folderPath, String filename) 
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
            for (float current_median : running_median)
    		{
    			pw.println(current_median);
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
