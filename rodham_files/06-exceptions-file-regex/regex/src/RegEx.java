
import java.util.regex.*;

public class RegEx {

	public static void main(String[] args) {
		
		if (args.length >= 3) {
			
			String patternStr = args[0];
			String operation = args[1];
			
			Pattern pattern = Pattern.compile(patternStr);
			Matcher matcher = null;
			
			for (int i = 2; i < args.length; ++i) {
								
				String str = args[i];
				
				if (matcher == null) {
					matcher = pattern.matcher(str);
				}
				else {
					matcher.reset(str);
				}
				
				System.out.println();

				if (operation.equalsIgnoreCase("matches")) {
					if (matcher.matches()) {
						System.out.println(str + " matches");
					}
					else {
						System.out.println(str + " does not match");
					}
				}
				else if (operation.equalsIgnoreCase("find")) {
					
					boolean foundSome = false;					
					while (matcher.find()) {
						foundSome = true;
						
						String match = matcher.group();
						int start = matcher.start();
						int end = matcher.end();
						
						System.out.printf("%s found in %s at (%d, %d)\n", match, str, start, end);
					}
					
					if (!foundSome) {
						System.out.println("No matches found in " + str);
					}
				}
			}
		}
		else {
			usage();
		}

	}

	private static void usage() {
		System.out.println("USAGE: java RegEx <pattern> {matches|find} <str1> <str2> ...");
	}

}
