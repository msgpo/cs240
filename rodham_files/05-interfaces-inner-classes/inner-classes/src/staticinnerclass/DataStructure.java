package staticinnerclass;


public class DataStructure {
	
    // create an array
    private final static int SIZE = 15;
    private int[] arrayOfInts = new int[SIZE];
    
    public DataStructure() {
        // fill the array with ascending integer values
        for (int i = 0; i < SIZE; i++) {
            arrayOfInts[i] = i;
        }
    }
    
    public Iterator iterator(int increment) {
    	return new DataStructureIterator(arrayOfInts, increment);
    }
    
    // STATIC INNER class implements the Iterator pattern
    // (Notice that the static inner class has no access to the outer object's fields,
    // so we must pass all the data it needs into its constructor.)
    
    private static class DataStructureIterator implements Iterator {
    	
    	private int[] array = null;
    	private int increment = 0;
    	
        // start stepping through the array from the beginning
        private int next = 0;
        
        DataStructureIterator(int[] array, int increment) {
        	this.array = array;
        	this.increment = increment;
        }
        
        public boolean hasNext() {
            // check if a current element is the last in the array
            return (next <= SIZE - 1);
        }
        
        public int getNext() {
            // record a value from the array
            int retValue = array[next];
            
            //get the next element
            next += increment;
            
            return retValue;
        }
    }
    
    public static void main(String s[]) {
    	
        // fill the array with integer values
        DataStructure ds = new DataStructure();

        // print out the values in the array
        Iterator iterator = ds.iterator(2);
        while (iterator.hasNext()) {
            System.out.println(iterator.getNext() + " ");
        }
    }
    
}