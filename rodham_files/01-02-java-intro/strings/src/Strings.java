/**
    1. String literals
        a. special characters \t, \\, \r,\b,\f, \',\"
        b. can't backslash a non-special character
    2. == vs equals
    3. methods
        a. charAt
        b. trim
        c. startsWith
        d. indexOf
        e. substring(int)
        f. substring(int, int)
        g. +
    4. Converting string to integers and integers to strings.
 */

public class Strings {
    
    public static void main(String[] args) {
        String s = "Hi";
        System.out.println("s = " + s + ", s.length() = " + s.length());
        String t = "Hi\tThere";
        String u = "Two\nLines\\Slash\b";

        String a = "123";
        String b = "123";
        String c = new String("123");
        String d = new String("123");
        System.out.println("a == b = " + (a == b));
        System.out.println("a == c = " + (a == c));
        System.out.println("c == d = " + (c == d));

        System.out.println("a.equals(b) = " + (a == b));
        System.out.println("a.equals(c) = " + (a == b));
        System.out.println("c.equals(d) = " + (a == b));

        System.out.println("a.charAt(1) = '" + a.charAt(1) + "'");

        String whitespace = "\b\tOne Two Three\b\t\n\r";
        System.out.println("whitespace = '" + whitespace.trim() + "'");

        String ex1 = "Starts With";
        System.out.println("ex1 starts with 'St' = " + ex1.startsWith("St"));

        String ex2 = "Mississippi";
        System.out.println("Index of i in Mississippi = " + ex2.indexOf("i"));

        System.out.println("ex2.substring(3) = " + ex2.substring(3));
        System.out.println("ex2.substring(3,6) = " + ex2.substring(3, 6));

        int x = 201;
        String twoZeroOne = Integer.toString(x);
        int xAgain = Integer.parseInt(twoZeroOne);

        System.out.println("x = " + x + ", twoZeroOne = '" + twoZeroOne  + "', xAgain = " + xAgain);

    }
}
