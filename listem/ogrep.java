package listem;

import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ogrep extends manipulate implements IGrep{
	
	public ogrep(){
		// do nothing;
	}


	public Map<File, List<String>> grep(File directory, String fileSelectionPattern, 
			String substringSelectionPattern, boolean recursive){

		Pattern p = Pattern.compile(fileSelectionPattern);
		Pattern ss = Pattern.compile(substringSelectionPattern);	
		Map<File, List<String>> results = new TreeMap<File, List<String>>();
		for(String entry : directory.list()){
			File child = new File(directory, entry);
			if(canRead(p, child, entry)){
				doWork(results, child, ss);
			}
			else if(canRecurse(child, recursive)){
				results.putAll(grep(child, fileSelectionPattern,
						substringSelectionPattern, recursive));
			}
		}
		return results;
	}
	
	
	public void doWork(Map<File, List<String>> destination, File child, Pattern p){
		ArrayList<String> lines = new ArrayList<String>();
		try{
			Scanner read = new Scanner(child);
			while(read.hasNextLine()){
				String line = read.nextLine();
				if (p.matcher(line).find()){
					lines.add(line);
				}
			}
		}
	
		catch(Exception e){
			// unreachable code
		}
		
		if(lines.size() > 0){
			destination.put(child,lines);
		}		
	}
}
