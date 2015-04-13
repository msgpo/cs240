package simple;

public class Person {
	
	private String name = "";
	private int age = 0;
	private YearInSchool year = YearInSchool.FRESHMAN;
	
//	{
//		name = "";
//		age = 0;
//		year = YearInSchool.FRESHMAN;
//	}

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public Person(String name, int age, YearInSchool year) {
		this(name, age);
		this.year = year;
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

	public YearInSchool getYear() {
		return year;
	}

	public void setYear(YearInSchool year) {
		this.year = year;
	}

//	public AcademicProgressReport analyzeAcademicProgress() {
//		
//	}
//	
//	public void register(ClassSchedule schedule) {
//		...
//	}
	
}