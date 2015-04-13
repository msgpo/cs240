package anonymousinnerclass;


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
    
    public Iterator iterator(final int increment) {
        
    	// ANONYMOUS LOCAL INNER class implements the Iterator pattern

    	return new Iterator() {
        	
            // start stepping through the array from the beginning
            private int next = 0;
            
            public boolean hasNext() {
                // check if a current element is the last in the array
                return (next <= SIZE - 1);
            }
            
            public int getNext() {
                // record a value from the array
                int retValue = arrayOfInts[next];
                
                //get the next element
                next += increment;
                
                return retValue;
            }
    	};
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