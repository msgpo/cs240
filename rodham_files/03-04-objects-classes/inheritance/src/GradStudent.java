
public class GradStudent extends Student
{
    private String advisor;

    public GradStudent(String name, int age, float gpa, String advisor){
        super(name, age, YearInSchool.GRAD, gpa);
        this.advisor = advisor;
    }

    @Override
    public String toString() {
        return super.toString() + String.format("advisor: %s\n", advisor);
    }

	@Override
	public boolean equals(Object o) {
		if (!super.equals(o)) {
			return false;
		}
		
		GradStudent other = (GradStudent)o;
		
		return advisor.equals(other.advisor);
	}

	@Override
	public int hashCode() {
		return super.hashCode() + advisor.hashCode();
	}   

	@Override
    protected float categoryPriority() {
        return 3.0f;
    }
}
