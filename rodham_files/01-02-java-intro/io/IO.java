import java.io.*;
//import java.io.FileWriter;
//import java.io.BufferedWriter;
//import java.io.PrintWriter;

import java.util.Scanner;

public class IO {

	public static void main(String[] args) {

		PrintWriter pw = null;
		try {
			FileWriter fw = null;
			fw = new FileWriter("file");

			BufferedWriter bw = new BufferedWriter(fw);
			pw = new PrintWriter(bw);

			pw.print("the integer 5 = ");
			pw.println(5);

			pw.print("the character 'g' = ");
			pw.println('g');
			pw.println("this is a string");

			Age age = new Age(8);
			pw.print("age = ");
			pw.println(age);

			pw.println(123);

			pw.close();

			printContentsOfFile();

			System.out.println();

			printContentsOfFileUsingScannerDotNextInt();

			System.out.println();

			printContentsOfFileUsingIntegerDotParseInt();
		} catch (Exception e) {
			System.err.println("Nothing to see here, move along");
		} finally {
			pw.close();
		}
	}

	private static void printContentsOfFile() {

		FileInputStream fis = null;
		try {
			File file = new File("file");
			fis = new FileInputStream(file);
		} catch (Exception e) {
			System.err.println("Better NOT get here");
		}

		BufferedInputStream bis = new BufferedInputStream(fis);
		Scanner scanner = new Scanner(bis);

		int count = 1;
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			System.out.println(count + ". " + line);
			count++;
		}
		scanner.close();
	}

	private static void printContentsOfFileUsingScannerDotNextInt() {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream("file");
		} catch (Exception e) {
			System.err.println("Better NOT get here, again");
		}

		BufferedInputStream bis = new BufferedInputStream(fis);
		Scanner scanner = new Scanner(bis);

		int count = 1;
		while (scanner.hasNextLine()) {
			if (count == 5) {
				int integerValue = scanner.nextInt();
				System.out.println("integerValue = " + integerValue);
			} else {
				String line = scanner.nextLine();
				System.out.println(count + ". \"" + line + "\"");
			}
			count++;
		}

		scanner.close();
	}

	private static void printContentsOfFileUsingIntegerDotParseInt() {

		FileInputStream fis = null;
		try {
			fis = new FileInputStream("file");
		} catch (Exception e) {
			System.err.println("Better NOT get here, again");
		}

		BufferedInputStream bis = new BufferedInputStream(fis);
		Scanner scanner = new Scanner(bis);

		int count = 1;
		while (scanner.hasNextLine()) {
			if (count == 5) {
				String line = scanner.nextLine();
				int integerValue = Integer.parseInt(line);
				System.out.println("integerValue = " + integerValue);
			} else {
				String line = scanner.nextLine();
				System.out.println(count + ". \"" + line + "\"");
			}
			count++;
		}
	}

}

class Age {
	private int value;

	public Age(int value) {
		this.value = value;
	}

	public String toString() {
		return Integer.toString(value);
	}
}
