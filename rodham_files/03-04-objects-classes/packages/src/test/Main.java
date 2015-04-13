package test;
import base.Person;
import school.Faculty;
import school.GradStudent;
import school.Student;
import school.YearInSchool;


public class Main {
    public static void main(String[] args) {
        Person p1 = new Person("Susan", 23);
        Person p2 = new Person("Susan", 23);
        Person p3 = new Person("Fred", 23);
        Person p4 = new Person("Susan", 19);

        System.out.println("p1 = " + p1);
        System.out.println("p2 = " + p2);
        System.out.println("p3 = " + p3);
        System.out.println("p4 = " + p4);

        System.out.println("p1 == null = " + p1.equals(null));
        System.out.println("p1 == p1 = " + p1.equals(p1));
        System.out.println("p1 == p2 = " + p1.equals(p2));
        System.out.println("p1 == p3 = " + p1.equals(p3));
        System.out.println("p1 == p4 = " + p1.equals(p4));
        System.out.println("p2 == p3 = " + p2.equals(p3));
        System.out.println("p2 == p4 = " + p2.equals(p4));
        System.out.println("p3 == p4 = " + p3.equals(p4));

        System.out.println("p1's age = " + p1.getAge());
        p1.setAge(45);
        System.out.println("p1's new age = " + p1.getAge());

        System.out.println("p1.hashCode() = " + p1.hashCode());
//        System.out.println("p1.agePriority() = " + p1.agePriority());

        Student s1 = new Student("Susan", 19, YearInSchool.SOPHOMORE, 3.4f);
        Student s2 = new Student("Susan", 19, YearInSchool.SOPHOMORE, 3.4f);
        System.out.println("s1 = " + s1);
        System.out.println("p1 == s1 = " + p1.equals(s1));
        System.out.println("s1 == s2 = " + s1.equals(s2));

//        System.out.println("s1.agePriority = " + s1.agePriority());

        Person p5 = s1;
        System.out.println("p5 = " + p5);
        
        String[] classes = {"cs236", "cs240", "cs340"};
        Faculty f1 = new Faculty("Fred", 42, classes);
        System.out.println("f1 = " + f1);

        GradStudent g1 = new GradStudent("Adam", 23, 3.75f, "Dr. Hee");
        GradStudent g2 = new GradStudent("Alice", 32, 3.77f, "Dr. Haw");


        System.out.println("------------------------------------");
        System.out.println("g1 = " + g1);
        System.out.println("------------------------------------");
        System.out.println("g2 = " + g2);

        System.out.println("g1.getPriority() = " + g1.getPriority());
        System.out.println("g2.getPriority() = " + g2.getPriority());

        System.out.println();
        System.out.println("Person stored in Variable of type Person");
        Person p = p1;

        System.out.println("p1 is instance of Person: " + (p instanceof Person));
        System.out.println("p1 is instance of Student: " + (p instanceof Student));
        System.out.println("p1 is instance of GradStudent: " + (p instanceof GradStudent));
        System.out.println("p1 is instance of Faculty: " + (p instanceof Faculty));

        System.out.println();
        System.out.println("Student stored in Variable of type Person");
        p = s1;

        System.out.println("s1 is instance of Person: " + (p instanceof Person));
        System.out.println("s1 is instance of Student: " + (p instanceof Student));
        System.out.println("s1 is instance of GradStudent: " + (p instanceof GradStudent));
        System.out.println("s1 is instance of Faculty: " + (p instanceof Faculty));

        System.out.println();
        System.out.println("GradStudent stored in Variable of type Person");
        p = g1;

        System.out.println("g1 is instance of Person: " + (p instanceof Person));
        System.out.println("g1 is instance of Student: " + (p instanceof Student));
        System.out.println("g1 is instance of GradStudent: " + (p instanceof GradStudent));
        System.out.println("g1 is instance of Faculty: " + (p instanceof Faculty));

        System.out.println();
        System.out.println("Faculty stored in Variable of type Person");
        p = f1;

        System.out.println("f1 is instance of Person: " + (p instanceof Person));
        System.out.println("f1 is instance of Student: " + (p instanceof Student));
        System.out.println("f1 is instance of GradStudent: " + (p instanceof GradStudent));
        System.out.println("f1 is instance of Faculty: " + (p instanceof Faculty));

        System.out.println();
        //Commented lines are illegal
        System.out.println("Student stored in Variable of type Student");
        Student s = s1;  //To be consistent with following examples
        System.out.println("s1 is instance of Person: " + (s instanceof Person));
        System.out.println("s1 is instance of Student: " + (s instanceof Student));
        System.out.println("s1 is instance of GradStudent: " + (s instanceof GradStudent));
        //System.out.println("s1 is instance of Faculty: " + (s instanceof Faculty));

        System.out.println();
        System.out.println("GradStudent stored in Variable of type Student");
        s = g1;  
        //Commented lines are illegal
        System.out.println("g1 is instance of Person: " + (s instanceof Person));
        System.out.println("g1 is instance of Student: " + (s instanceof Student));
        System.out.println("g1 is instance of GradStudent: " + (s instanceof GradStudent));
        //System.out.println("g1 is instance of Faculty: " + (s instanceof Faculty));

        System.out.println();
        System.out.println("GradStudent stored in Variable of type GradStudent");
        GradStudent g = g1; 
        //Commented lines are illegal
        System.out.println("g1 is instance of Person: " + (g instanceof Person));
        System.out.println("g1 is instance of Student: " + (g instanceof Student));
        System.out.println("g1 is instance of GradStudent: " + (g instanceof GradStudent));
        //System.out.println("g1 is instance of Faculty: " + (g instanceof Faculty));

        System.out.println();
        System.out.println("Faculty stored in Variable of type Faculty");
        Faculty f = f1; 
        //Commented lines are illegal
        System.out.println("f1 is instance of Person: " + (f instanceof Person));
        //System.out.println("f1 is instance of Student: " + (f instanceof Student));
        //System.out.println("f1 is instance of GradStudent: " + (f instanceof GradStudent));
        System.out.println("f1 is instance of Faculty: " + (f instanceof Faculty));
    }
}
