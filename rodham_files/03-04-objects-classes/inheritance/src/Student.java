
public class Student extends Person {
	
	private YearInSchool year = YearInSchool.FRESHMAN;
    private float gpa;

	public Student(String name, int age, YearInSchool year, float gpa) {
        super(name, age);
        this.year = year;
        this.gpa = gpa;
    }

    public YearInSchool getYear() {
		return year;
	}

	public void setYear(YearInSchool year) {
		this.year = year;
	}

	public float getGpa() {
		return gpa;
	}

	public void setGpa(float gpa) {
		this.gpa = gpa;
	}

	@Override
    public String toString() {
        return super.toString() + String.format("year: %s\ngpa: %f\n", year, gpa);
    }

	@Override
    public boolean equals(Object o) {
        if (!super.equals(o)) {
        	return false;
        }
        
    	Student other = (Student)o;
    	
    	return (year == other.year && gpa == other.gpa);
    }
	
	@Override
	public int hashCode() {
		return super.hashCode() + year.hashCode() + (int)gpa;
	}

	@Override
    protected int agePriority() {
        return 1 + getAge()/10;
    }

	@Override
    protected float categoryPriority() {
        return 2.0f;
    }
}
