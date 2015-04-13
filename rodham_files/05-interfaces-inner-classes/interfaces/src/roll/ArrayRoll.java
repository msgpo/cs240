package roll;

public class ArrayRoll implements Roll
{
    private String[] names;
    private int maxSize;
    private int size;

    public ArrayRoll(int maxSize) {
        this.maxSize = maxSize;
        names = new String[maxSize];
        size = 0;
    }
    
    public void addName(String newName) {
        if(contains(newName) >= size && size < maxSize) {
            names[size++] = newName;
        }
    }

    public void removeName(String oldName) {
        int location = contains(oldName);
        if(location < size) {
            for(int i = location; i < size - 1; i++) {
                names[i] = names[i+1];
            }
            size--;
        }
    }

    public int size() {
        return size;
    }

    private int contains(String name) {
        int result = 0;
        boolean found = false;
        while(!found && result < size) {
            found = names[result].equals(name);
            if(!found) {
                result++;
            }
        }
        return result;
    }
}
