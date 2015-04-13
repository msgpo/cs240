package roll;

import java.util.ArrayList;

public class ArrayListRoll implements Roll
{
    private ArrayList<String> names;
    private int maxSize;

    public ArrayListRoll(int maxSize) {
        this.maxSize = maxSize;
        names = new ArrayList<String>();
    }
    
    public void addName(String newName) {
        if(!names.contains(newName) && names.size() < maxSize) {
            names.add(newName);
        }
    }

    public void removeName(String oldName) {
        names.remove(oldName);
    }

    public int size() {
        return names.size();
    }
}
