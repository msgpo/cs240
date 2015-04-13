package school;


import java.util.Arrays;

import base.Person;



public class Faculty extends Person
{
    private  String[] classes; 

    public Faculty(String name, int age, String[] classes) {
        super(name, age);
        this.classes = classes;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(super.toString());
        
        result.append("classes taught:\n");
        for (String klass : classes) {
        	result.append(klass + "\n");
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
    	if (!super.equals(o)) {
    		return false;
    	}
    	
    	Faculty other = (Faculty)o;
    	
    	return Arrays.equals(classes, other.classes);
    }

    @Override
    public int hashCode() {
    	return super.hashCode() + Arrays.hashCode(classes);
    }
    
    @Override
    protected int agePriority() {
        return 1 + getAge() / 5;
    }

    @Override
    protected float categoryPriority() {
        return 4.0f;
    }
}
