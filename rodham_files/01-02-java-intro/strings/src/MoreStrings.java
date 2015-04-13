

public class MoreStrings {

	public static void main(String[] args) {

		String s1 = "BYU Cougars";
		String s2 = new String("BYU Cougars");

		// WRONG!

		if (s1 == s2) {
			System.out.println("EQUAL");
		}


		// RIGHT!

		if (s1.equals(s2)) {
			System.out.println("EQUAL");
		}


		// Strings are IMMUTABLE (i.e., once created, a String cannot be modified)


		// This does not modify the String object referenced by s1.
		// Rather, it creates a new String object containing the result, and assigns it to s1

		s1 += " are the best";


		// WRONG! - creates 1000 String objects

		String message = "";
		for (int i = 0; i < 1000; ++i) {
			message += s1;
		}
		System.out.println(message);
	

		// RIGHT!

		StringBuilder messageBuilder = new StringBuilder();
		for (int i = 0; i < 1000; ++i) {
			messageBuilder.append(s1);
		}
		System.out.println(messageBuilder.toString());	
	}
}
