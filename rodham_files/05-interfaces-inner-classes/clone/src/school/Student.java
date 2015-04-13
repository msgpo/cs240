package school;

// There is no need to implement the Cloneable interface here,
// because Person already did that.  But, you can still do it here, if you want.

public class Student extends Person /* implements Cloneable */ {

	private YearInSchool year;
	
	public Student(int id, String name, YearInSchool year) {
		super(id, name);
		setYear(year);
	}

	public YearInSchool getYear() {
		return year;
	}

	public void setYear(YearInSchool year) {
		this.year = year;
	}

	// Strictly speaking, overriding "clone" here is unnecessary, because:
	//
	//	1. Person already overrode "clone" to make it public
	//	2. "year" is immutable
	//
	// but it doesn't hurt to override it anyway just to be safe.

	/*
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	*/
}
