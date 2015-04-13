import support.YearInSchool;
import support.Age;

//Alternative form
//import support.*

/**
    1. Using user defined package "support" -- see subdirectory
        a. import support
            1. x.y.z.*
            2. a.b.c
    2. Show how to use -d option to get classes in correct directory
    3. Show directory structure of code directory
    4. java -d code Person.java
    5. move the support to different directories and uce -cp command line option
       to show how to use it.
    6. Built in packages
        a. Libraries:
            1. java.lang.* -- automatic
            2. java.io.*
            3. java.util.* 
        b. Show api
 */
public class Person {
    private String name;
    protected Age age;
    YearInSchool yearInSchool;

    public Person(String name, Age age, YearInSchool yearInSchool) {
        this.name = name;
        this.age = age;
        this.yearInSchool = yearInSchool;
    }

    public String toString() {
        return "name = " + name + ", age = " + age + ", year in school = " + yearInSchool;
    }

    private void setName(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Person person = new Person("Fred", new Age(23), YearInSchool.sophomore);
        System.out.println("person 1 = " + person);
        person.setName("Jane");
        System.out.println("person 2 = " + person);
        System.out.println("person 2 = " + person.toString());
        person.yearInSchool.increment();
        System.out.println("person 2 = " + person);
        YearInSchool.listYearsInSchool();
    }
}
