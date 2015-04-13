package linkedlist;

public class Test {

	public static void main(String[] args) throws CloneNotSupportedException {

		String[] values = {"Fred", "Barney", "Wilma", "Betty"};
		
		LinkedList list1 = new LinkedList();

		ListNode last = null;
		for (String v : values) {
			last = list1.insert(v, last);
		}
		
		LinkedList list2 = (LinkedList)list1.clone();
		
		list1.remove(list1.find("Wilma", null));
		
		printList("LIST 1", list1);
		System.out.println();
		printList("LIST 2", list2);

	}
	
	private static void printList(String label, LinkedList list) {
		
		System.out.println(label);
		
		ListNode node = list.getFirst();
		while (node != null) {
			System.out.println(node.getElement());
			node = node.getNext();
		}
	}

}
