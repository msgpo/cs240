package listem;

import java.util.regex.*;
import java.util.*;
import java.io.*;

public abstract class manipulate {
	
	private Map<File, Object> results;

	
	final boolean canRead(Pattern p, File child, String entry){
		return (!child.isDirectory() && child.canRead()
					&& p.matcher(entry).matches());
	}
	
	
	final boolean canRecurse(File child, boolean recursive){
		return (recursive && child.isDirectory()
					&& child.canRead());
	}
	
	//bstract void doWork(Map m, File f, Pattern p);
	
	public manipulate(){

	}
	
	

}
