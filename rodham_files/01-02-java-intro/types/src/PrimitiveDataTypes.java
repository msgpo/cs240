/**
    1. The \\u in a character or string literal must be lower case.
 */

public class PrimitiveDataTypes {

    public static void main(String[] args) {

        int i = 5;
        byte b = -128;
        long l1 = 10;
        long l2 = 10l;
        short s1 = 5;

        //System.out.println(i + ", " + b + ", " + l1 + ", " + l2 + ", " + s1);
        System.out.printf("%d, %d, %d, %d, %d\n", i, b, l1, l2, s1);


        float f = 2.5f;
        double d = 1.0d/3.0d;

        //System.out.println(f + ", " + d);
        System.out.printf("%f, %f\n", f, d);


        char c1 = 'a';
        char c2 = '\u03A3';

        //System.out.println(c1 + ", " + c2);
        System.out.printf("%c, %c\n", c1, c2);


        String s = "A \u22C0 B \u21D2 A";
        //System.out.println(s);
        System.out.printf("%s\n", s);


        boolean t = true;
        boolean ff = false;

        //System.out.println(t + ", " + ff);
      	System.out.printf("%b, %b", t, ff);

    }
}
