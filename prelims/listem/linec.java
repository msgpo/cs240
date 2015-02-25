package listem;

import java.util.regex.*;
import java.io.*;
import java.util.*;

public class linec extends manipulate implements ILineCounter {
	
	public linec(){
		
	}

	public Map<File, Integer> countLines(File directory, String fileSelectionPattern, 
			boolean recursive){
		Pattern p = Pattern.compile(fileSelectionPattern);	
		Map<File, Integer> results = new TreeMap<File, Integer>();
		for(String entry : directory.list()){
			File child = new File(directory, entry);
System.out.println(child.toString());		
			if(canRead(p, child, entry)){
				doWork(results, child, p);
			}
			else if(canRecurse(child, recursive)){
				results.putAll(countLines(child, 
						fileSelectionPattern,
						recursive));
			}
		}
		return results;
	}

	public void doWork(Map<File, Integer> destination, File child, Pattern p){
				
		int lines = 0;
		try{
			Scanner read = new Scanner(child);
			while(read.hasNextLine()){
			read.nextLine();
			lines++;
			}
			destination.put(child, lines);						
		}
		catch(Exception e){
			// uh we already checked this
		}
	}
		
	

}
