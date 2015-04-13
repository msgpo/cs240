package school;

import java.util.*;

// There is no need to implement the Cloneable interface here,
// because Person already did that.  But, you can still do it here, if you want.

public class Faculty extends Person implements Instructor, Advisor /*, Cloneable */ {

	private ArrayList<Course> courses;
	private ArrayList<Student> students;
	
	public Faculty(int id, String name) {
		super(id, name);
		courses = new ArrayList<Course>();
		students = new ArrayList<Student>();
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

	// We must override "clone" here, because "courses" and "students" are mutable.
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
	
		Faculty copy = (Faculty)super.clone();
		
		// Make a copy of courses
		copy.courses = (ArrayList<Course>)this.courses.clone();
		
		for (int i = 0; i < copy.courses.size(); ++i) {
			copy.courses.set(i, (Course)copy.courses.get(i).clone());
		}
		
		// Make a copy of students
		copy.students = (ArrayList<Student>)this.students.clone();
		
		for (int i = 0; i < copy.students.size(); ++i) {
			copy.students.set(i, (Student)copy.students.get(i).clone());
		}
		
		return copy;
	}
	
}
