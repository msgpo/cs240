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
			if (true){
				File child = new File(directory, entry);
				if(!child.isDirectory() && child.canRead()
						&& p.matcher(entry).matches()){
					int lines = 0;
					try{
						Scanner read = new Scanner(child);
						while(read.hasNextLine()){
						read.nextLine();
						lines++;
						}
						results.put(child, lines);						
					}
					catch(Exception e){
						// uh we already checked this
					}

				}
				else if(recursive && child.isDirectory()
						&& child.canRead()){
					results.putAll(countLines(child, 
							fileSelectionPattern,
							recursive));
				}
			}
		}
		return results;
	}
}
