package roll;

public class RollTest {

	public static void main(String[] args) {
        Roll list = new ArrayRoll(2);
        System.out.println("Testing ArrayRoll");
        test(list);

        list = new ArrayListRoll(2);
        System.out.println("Testing ArrayListRoll");
        test(list);
    }

    private static void test(Roll roll) {
        String name1 = "Jane";
        String name2 = "Bob";
        String name3 = "Mary";

        roll.addName(name1);
        if(roll.size() != 1){
            System.err.println("    roll should have had only 1 name: name1");
        }

        roll.addName(name2);
        if(roll.size() != 2){
            System.err.println("    roll should have had only 2 names: name1 and name2");
        }

        roll.addName(name2);
        if(roll.size() != 2){
            System.err.println("    roll should still have had only 2 names, can't add same name twice");
        }

        roll.addName(name3);
        if(roll.size() != 2){
            System.err.println("    studentList should still have had only 2 names, since the list was full");
        }

        roll.removeName(name1);
        if(roll.size() != 1){
            System.err.println("    roll should have only 1 name after removing a name1");
        }

        roll.removeName(name3);
        if(roll.size() != 1){
            System.err.println("    roll should have only 1 name after trying to remove a name that isn't there");
        }

        roll.removeName(name2);
        if(roll.size() != 0){
            System.err.println("    roll should have no names");
        }

        roll.removeName(name2);
        if(roll.size() != 0){
            System.err.println("    roll should still have no names after trying to remove a name from an empty list");
        }
    }
}
