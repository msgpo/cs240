package school;

public interface Instructor {

	void addCourse(Course c);
	void removeCourse(Course c);
	Course[] getCourses();
}
