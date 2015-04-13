

import java.io.*;
import java.util.*;

public class Catalog implements Serializable {

	private List<CD> items;
	
	public Catalog() {
		items = new ArrayList<CD>();
	}
	
	public void add(CD cd) {
		items.add(cd);
	}
	
	public Collection<CD> getItems() {
		return Collections.unmodifiableCollection(items);
	}
	
}
