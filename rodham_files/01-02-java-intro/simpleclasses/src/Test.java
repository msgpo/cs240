


public class Test {

	public static void main(String[] args) {

		int tl_x = 10;
		int tl_y = 20;
		int br_x = 75;
		int br_y = 150;

		Point tl = new Point(tl_x, tl_y);
		System.out.println("Top-Left: " + tl);

		Point br = new Point(br_x, br_y);
		System.out.println("Bottom-Right: " + br);

		Rectangle rect = new Rectangle(tl, br);
		System.out.println("Rectangle: " + rect);
	}

}
