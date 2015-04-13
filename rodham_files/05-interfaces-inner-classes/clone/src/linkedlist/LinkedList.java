package linkedlist;


public class LinkedList implements Cloneable {

	private ListNode head;
	private ListNode tail;
	private int count;


	/**
	 * Initializes a new list
	 */
	public LinkedList() {
		clear();
	}


	/**
	 * Returns the number of elements in the list
	 */
	public int size() {
		return count;
	}


	/**
	 * Returns true if the list is empty and false if it is not
	 */
	public boolean isEmpty() {
		return (count == 0);
	}


	/**
	 * Removes all elements from the list
	 */
	public void clear() {
		head = null;
		tail = null;
		count = 0;
	}


	/**
	 * Returns the first node in the list, or null if the list is empty
	 */
	public ListNode getFirst() {
		return head;
	}


	/**
	 * Returns the last node in the list, or null if the list is empty
	 */
	public ListNode getLast() {
		return tail;
	}


	/**
	 * Inserts element x after node n
	 * If n is null, x is inserted at the beginning of the list
	 * Returns the node that contains x
	 */
	public ListNode insert( Object x, ListNode n ) {

		ListNode newNode = new ListNode(x, null, null);

		if (n == null) {
			newNode.prev = null;
			newNode.next = head;
			if (head != null) {
				head.prev = newNode;
			}
			head = newNode;
		}
		else {
			newNode.prev = n;
			newNode.next = n.next;
			if (n.next != null) {
				n.next.prev = n;
			}
			n.next = newNode;
		}

		if (newNode.next == null) {
			tail = newNode;
		}

		++count;
		return newNode;
	}


	/**
	 * Returns the first occurrence of x that comes after node n,
	 *    or null if x is not found
	 * If n is null, the list is searched from the beginning
	 */
	public ListNode find( Object x, ListNode n ) {
		
		ListNode cur = null;
		
		if (n == null) {
			cur = head;
		}
		else {
			cur = n.next;
		}

		while (cur != null) {
			if (x == null) {
				if (cur.element == null) {
					return cur;
				}
			}
			else {
				if (x.equals(cur.element)) {
					return cur;
				}
			}

			cur = cur.next;
		}

		return null;
	}


	/**
	 * Removes node n from the list
	 */
	public void remove( ListNode n ) {

		if (n == head) {
			head = n.next;
			if (head != null) {
				head.prev = null;
			}
			if (tail == n) {
				tail = null;
			}
			--count;
		}
		else {
			ListNode previous = n.prev;
			previous.next = n.next;
			if (n.next != null) {
				n.next.prev = previous;
			}
			if (tail == n) {
				tail = previous;
			}
			--count;
		}
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		
		LinkedList copy = (LinkedList)super.clone();
		
		// At this point, copy  points to the same node list as this.
		// Having two LinkedList objects share the same node list would be disastrous.
		// Therefore, we clear the copy, and then insert each value in this list into copy.
		
		// Clear the copy
		copy.clear();
		
		// Insert each value in this list into copy
		ListNode thisCurNode = head;
		
		ListNode copyLastNode = null;
		
		while (thisCurNode != null) {
			
			copyLastNode = copy.insert(thisCurNode.getElement(), copyLastNode);
			
			thisCurNode = thisCurNode.next;
		}
		
		return copy;
	}

}
