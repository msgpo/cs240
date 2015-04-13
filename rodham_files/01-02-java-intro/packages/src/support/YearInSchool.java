package support;

public class YearInSchool {
    public static YearInSchool freshman = new YearInSchool("Freshman");
    public static YearInSchool sophomore = new YearInSchool("Sophomore");
    public static YearInSchool junior = new YearInSchool("Junior");
    public static YearInSchool senior = new YearInSchool("Senior");

    private String value;

    private YearInSchool(String value) {
        this.value = value;
    }

    public String toString() {
        return value;
    }

    public void increment() {
        if(value.equals("Freshman")) {
            value = "Sophomore";
        } else if(value.equals("Sophomore")) {
            value = "Junior";
        } else if(value.equals("Junior")) {
            value = "Senior";
        }
    }

    public static void listYearsInSchool() {
        System.out.println("Freshman\n" + "Sophomore\n" + "Junior\n" + "Senior");
    }
}

