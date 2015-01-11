

public class Pixel {
	private int red;
	private int blue;
	private	int green;
	
	public Pixel(int r, int g, int b){
		red = r;
		blue = b;
		green = g;
	}
	
	public int getR(){
		return red;
	}
	
	public int getB(){
		return blue;
	}
	
	public int getG(){
		return green;
	}
	
	public void setR(int n){
		red = n;
	}
	
	public void setB(int n){
		blue = n;
	}
	
	public void setG(int n){
		green = n;
	}
	
	public String toString(){
		return (red + " " + green + " " + blue);
	}
	
	public void kill() {
		red = 128;
		green = 128;
		blue = 128;
	}
	public void inverse(){
		red = Math.abs(red-255);
		green = Math.abs(green-255);
		blue = Math.abs(blue-255);
	}
	
	public void gray(){
		int result = (red + green + blue) / 3;
		red = result;
		green = result;
		blue = result;
	}
	
	public void emboss(Pixel neighbor){
		int max = red - neighbor.getR();
		if (-(green - neighbor.getG()) == max){
			// do nothing
		}
		else if (Math.abs(green - neighbor.getG()) > Math.abs(max)){
			max = green - neighbor.getG();
		}
		if (-(blue - neighbor.getB()) == max){
			// do nothing
		}
		else if (Math.abs(blue - neighbor.getB()) > Math.abs(max)){
			max = blue - neighbor.getB();
		}
		
		max += 128;
		if(max > 255){
			max = 255;
		}
		else if(max < 0){
			max = 0;
		}
		
		red = max;
		blue = max;
		green = max;
	}
	
	
}
