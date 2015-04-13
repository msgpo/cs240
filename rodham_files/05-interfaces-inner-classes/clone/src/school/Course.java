package school;

// We must implement the Cloneable interface, even though it doesn't define any methods.
// Otherwise, Java will refuse to clone Course objects.

public class Course implements Cloneable {
	
	private String name;

	public Course(String name) {
		setName(name);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (o == null) {
			return false;
		}
		
		if (getClass() != o.getClass()) {
			return false;
		}
		
		Course other = (Course)o;
		
		return name.equals(other.name);
	}

	// This is needed to change "clone" from protected to public

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
