package base;


public class Person {
	
	private static int nextID = 1;
	
//	static {
//		nextID = 1;
//	}

	private static int allocateID() {
		return nextID++;
	}
	
	private int id = 0;
	private String name = "";
	private int age = 0;
	
//	{
//		id = 0;
//		name = "";
//		age = 0;
//	}

	public Person(String name, int age) {
		this.id = allocateID();
		this.name = name;
		this.age = age;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

//	public AcademicProgressReport analyzeAcademicProgress() {
//		
//	}
//	
//	public void register(ClassSchedule schedule) {
//		...
//	}
	
	@Override
	public String toString() {
		return String.format("id: %d\nname: %s\nage: %d\n", id, name, age);
	}
	
	/*
		The equals method implements an equivalence relation on non-null object references: 
		•It is reflexive: for any non-null reference value x, x.equals(x) should return true. 
		•It is symmetric: for any non-null reference values x and y, x.equals(y) should return true if and only if y.equals(x) returns true. 
		•It is transitive: for any non-null reference values x, y, and z, if x.equals(y) returns true and y.equals(z) returns true, then x.equals(z) should return true. 
		•It is consistent: for any non-null reference values x and y, multiple invocations of x.equals(y) consistently return true or consistently return false, provided no information used in equals comparisons on the objects is modified. 
		•For any non-null reference value x, x.equals(null) should return false. 
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		
		if (o == null)
			return false;
		
		if (getClass() != o.getClass())
			return false;
		
		Person other = (Person)o;
		
		return (id == other.id && 
				name.equals(other.name) && 
				age == other.age);
	}
//	public boolean equals(Object o) {
//		if (o == this)
//			return true;
//		
//		if (o == null)
//			return false;
//		
//		if (getClass() != o.getClass())
//			return false;
//		
//		Person other = (Person)o;
//		
//		return (id == other.id);
//	}
	
	/*
		The general contract of hashCode is: 
		•Whenever it is invoked on the same object more than once during an execution of a Java application, the hashCode method must consistently return the same integer, provided no information used in equals comparisons on the object is modified. This integer need not remain consistent from one execution of an application to another execution of the same application. 
		•If two objects are equal according to the equals(Object) method, then calling the hashCode method on each of the two objects must produce the same integer result. 
		•It is not required that if two objects are unequal according to the equals(java.lang.Object) method, then calling the hashCode method on each of the two objects must produce distinct integer results. However, the programmer should be aware that producing distinct integer results for unequal objects may improve the performance of hash tables.
 	 */
	@Override
	public int hashCode() {
		return (id + name.hashCode() + age);
	}
//	public int hashCode() {
//		return id;
//	}
	
	public int getPriority() {
		return (int)(agePriority() * categoryPriority());
	}
	
	protected int agePriority() {
		return 1;
	}
	
	protected float categoryPriority() {
		return 1.0f;
	}
	
}