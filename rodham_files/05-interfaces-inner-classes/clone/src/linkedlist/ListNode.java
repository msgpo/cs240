package linkedlist;


public class ListNode {

	Object element;
	ListNode prev;
	ListNode next;

	ListNode( Object e, ListNode p, ListNode n ) {
		element = e;
		prev = p;
		next = n;
	}

	public Object getElement() {
		return element;
	}

	public ListNode getPrevious() {
		return prev;
	}

	public ListNode getNext() {
		return next;
	}
}
