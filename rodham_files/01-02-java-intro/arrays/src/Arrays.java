
public class Arrays {

	private static final int numChars = 5;   
    private static int[] a1;
    private static int[] a1Different = {1,4,9};
    private static char[] a2 = {'a', 'e', 'i', 'o', 'u'};
    private static char[] a3 = new char[numChars];
    private static Cell[][] ticTacToeBoard; 

    public static void main(String[] args) {
        a1 = new int[3];
        for(int i = 0; i < a1.length; i++) {
            a1[i] = i*i;
        }
        for(int i = 0; i < a1.length; i++) {
            System.out.print("a1[" + i + "] = " + a1[i]);
            if(i < a1.length -1 ) {
                System.out.print(", ");
            }
        }
        System.out.println();

        for(int i = 0; i < a1Different.length; i++) {
            System.out.print("a1Different[" + i + "] = " + a1Different[i]);
            if(i < a1Different.length -1 ) {
                System.out.print(", ");
            }
        }
        System.out.println();

        for(int i = 0; i < a2.length; i++) {
            System.out.print("a2[" + i + "] = '" + a2[i] + "'");
            if(i < a2.length -1 ) {
                System.out.print(", ");
            }
        }
        System.out.println();

        ticTacToeBoard = new Cell[3][3];
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                ticTacToeBoard[row][col] = new Cell();
            }
        }

        printTicTacToeBoard(ticTacToeBoard);

        Cell[] secondRow = ticTacToeBoard[1];
        for(int col = 0; col < secondRow.length; col++) {
            secondRow[col].set(true);
        }

        printTicTacToeBoard(ticTacToeBoard);

        System.out.println("ticTacToeBoard.length = " + ticTacToeBoard.length);
    }

    private static void printTicTacToeBoard(Cell[][] board) {
        System.out.println("Tic Tac Toe Board = ");
        for(int row = 0; row < 3; row++) {
            for(int col = 0; col < 3; col++) {
                System.out.print(board[row][col]);
                if(col < 3) {
                    System.out.print(", ");
                }
            }
            if(row < 3) {
                System.out.println();
            }
        }
    }
}

class Cell {
    boolean occupied;

    public Cell() {
        occupied = false;
    }

    public boolean occupied() {
        return occupied;
    }

    public void set(boolean value) {
        occupied = value;
    }

    public String toString() {
        StringBuilder result = new StringBuilder(Boolean.toString(occupied));
        if(occupied) {
            result.append(' ');
        }
        return result.toString();
    }
}
