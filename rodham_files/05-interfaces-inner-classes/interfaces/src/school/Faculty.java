package school;

import java.util.*;


public class Faculty extends Person implements Instructor, Advisor {

	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	
	public Faculty(int id, String name) {
		super(id, name);
	}

	// Instructor methods
	
	@Override
	public void addCourse(Course c) {
		courses.add(c);
	}

	@Override
	public void removeCourse(Course c) {
		courses.remove(c);
	}

	@Override
	public Course[] getCourses() {
		return courses.toArray(new Course[courses.size()]);
	}
	
	// Advisor methods

	@Override
	public void addStudent(Student s) {
		students.add(s);
	}

	@Override
	public void removeStudent(Student s) {
		students.remove(s);
	}

	@Override
	public Student[] getStudents() {
		return students.toArray(new Student[students.size()]);
	}

}
