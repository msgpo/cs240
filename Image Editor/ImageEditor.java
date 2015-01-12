import java.io.*;
import java.io.File;
import java.util.*;


public class ImageEditor {

	public static void main(String[] args) {
		try{
			if (args.length < 3){
				PrintWriter o = new PrintWriter(System.out);
				o.print("Bad args");
				o.close();
				return;
			}
			File infile = new File(args[0]);
			File outfile = new File(args[1]);
			String op = args[2];
			int blur = 0;
			if(args.length == 4){
				blur = Integer.parseInt(args[3]);
			}
			Scanner source = new Scanner(infile);

			Image img = new Image(source);

			if(op.equals("invert")){
				img.invert();
			}

			if(op.equals("grayscale")){
				img.grayscale();
			}

			if(op.equals("emboss")){
				img.emboss();
			}

			if(op.equals("motionblur")){
				img.blur(blur);
			}
			
			img.toFile(outfile);
		}
		catch(FileNotFoundException e){
			System.out.print("No such input file\n");
		}
		catch(NumberFormatException e){
			System.out.println("Bad input file");
		}
		
		

	}



}
