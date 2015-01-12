import java.util.*;
import java.io.*;

public class Image {
	
	public Image(Scanner src) throws NumberFormatException {
	//	System.out.println("Constructing image");
		if(src.next().equals("P3")){
			String token = src.next();
			while(token.startsWith("#")){
				//go to next line
				//skips all comments in a row
				token = src.nextLine();
				token = src.next();
			}
			width = Integer.parseInt(token);
			token = src.next();
			while(token.startsWith("#")){
				//go to next line
				//skips all comments in a row
				token = src.nextLine();
				token = src.next();
			}
			height = Integer.parseInt(token);
			token = src.next();
			pixels = new Pixel[width][height];
			if (token.equals("255")){
				token = src.next();
					// I guess the listed pixels go 
					// in order of rows, then in 
					// columns... so should be plenty
					// to just fill in rows
				for (int y = 0; y < height; y++){
					for (int x = 0; x < width; x++){
						while(token.startsWith("#")){
							token = src.nextLine();
							token = src.next();
						}
						int red = Integer.parseInt(token);
						token = src.next();
						while(token.startsWith("#")){
							token = src.nextLine();
							token = src.next();
						}
						int green = Integer.parseInt(token);
						token = src.next();
						while(token.startsWith("#")){
							token = src.nextLine();
							token = src.next();
						}
						int blue = Integer.parseInt(token);
						if(src.hasNext()){
							token = src.next();
						}

						pixels[x][y] = new Pixel(red, green, blue);
				//		System.out.print(pixels[x][y].toString());
					}
				}
			}
		}
	}

	
	private int width;
	private int height;
	private Pixel[][] pixels;

	
	public void toFile(File target)
		throws FileNotFoundException{
		PrintWriter p = new PrintWriter(target);
		p.print("P3\n" + width + "\n" + height + "\n" + "255" + "\n" );
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				p.print(pixels[x][y]);
			}
		}
		p.close();
	}
	
	public void invert(){
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				pixels[x][y].inverse();
			}
		}
	}
	
	public void grayscale(){
	System.out.printf("Killing pixels\n");
	System.out.println("Killing pixels!");
		for (int y = 0; y < height; y++){
			for (int x = 0; x < width; x++){
				pixels[x][y].gray();
				System.out.printf("Killing pixel at %d, %d\n",x,y);
			}
		}
	}
	
	public void emboss(){
	//	Pixel ded = new Pixel(128,128,128);
		for (int y = 0; y < height; y++){
			pixels[0][y].kill();
		}
		for (int x = 0; x < width; x++){
			pixels[x][0].kill();
		}
		for (int y = 1; y < height; y++){
			for (int x = 1; x < width; x++){
				pixels[x][y].emboss(pixels[x-1][y-1]);
			}
		}
	}
	
	public void blur(int r){
		// make average across the streak for each pixel
		// make new pixel, set original equal to new
	}
}

