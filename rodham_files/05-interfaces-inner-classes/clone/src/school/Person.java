package school;

// We must implement the Cloneable interface, even though it doesn't define any methods.
// Otherwise, Java will refuse to clone Person objects.

public class Person implements Cloneable {

	private int id;
	private String name;

	public Person(int id, String name) {
		setId(id);
		setName(name);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
		
		Person other = (Person)o;
		
		return (id == other.id && name.equals(other.name));
	}

	// This is needed to change "clone" from protected to public
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}	
	
}
