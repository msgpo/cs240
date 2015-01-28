package spell;

public class dictionary implements ITrie {
	
	public dictionary() {
		words = 0;
		nodes = 1;
	}
	
	private int words;
	private int nodes;
	public INode root;
	
	public void add(String word) {
		// add word to tree, count++
		// inc nodes for every new node
		// words++
		if(nodes == 1){
			root = new INode();
		}
			
		add(root, word);
	}

	private void add(INode n, String word) {
		int first = ((word.substring(0,1).toCharArray())[0] - 'a');
		String last = word.substring(1);
		if (last.length() == 0){
			// add word!
			if (n.nodes[first] == null){
				n.nodes[first] = new INode();
				nodes++;
			}
			n.nodes[first].count++;
			words++;
		}
		else{
			if (n.nodes[first] == null){
			// new node, continue
				n.nodes[first] = new INode();
				nodes++;
			}
			add(n.nodes[first], last);
		}
	}
	

	public INode find(String word){
		// check if the chain ends in a node with count > 0
		// return that node (presumably the "null"
		// would be automagic) 
		// false results in an exception farther out
		// technically only if no word is EVER found, there's 
		// a test for null pointer.

		return find(root, word);
			
	}

	private INode find(INode n, String word){
		int first;
		String last;
		if (word.length() > 1){
			first = ((word.substring(0,1).toCharArray())[0] - 'a');
			last = word.substring(1);
		}
		else if (word.length() == 1) {
			first = ((word.toCharArray())[0] - 'a');
			last = ""; 
		}
		else {
			return null;
			// "" is not in the dictionary.  like, ever.
		}
		
		if (n.nodes[first] == null){
			return null;
			// dead chain, no find.
		}
		if (last.length() == 0){
			if (n.nodes[first].count == 0){
				return null;
			}
			else {
				return n.nodes[first];
			}
		}
		else {
			return find(n.nodes[first], last);
		}
	}


	public int getWordCount(){
		return words;
	}
	
	public int getNodeCount(){
		return nodes;
	}
	
	/**
	 * The toString specification is as follows:
	 * For each word, in alphabetical order:
	 * <word>\n
	 */
	@Override
	public String toString(){
		// gonna have to somehow start with a in the root node
		// and figure out the a of each child, then the b of the
		// leaf, etc.  probably recursive.
		StringBuilder result = new StringBuilder();
		toString(root, result, new StringBuilder(""));
System.out.println("Some kind of toString.");
		return result.toString();
		
	}

	private void toString(INode n, StringBuilder out, 
			StringBuilder stem){
		StringBuilder s = new StringBuilder(stem.toString());
//System.out.println("COME ON PRINT ANYTHING");
		for (int i = 0; i < 26; i++) {
//System.out.println("New character loop.");
			if (n.nodes[i] != null) {
				if (n.nodes[i].getValue() > 0) {
//System.out.println("Adding something to the output.");
					out.append(s.append((char) ('a' + i)));
					s.setLength(s.length() - 1);
					out.append('\n');
					toString(n.nodes[i], out, 
						stem.append((char) ('a' + i)));
					stem.setLength(stem.length() - 1);
				}
				else { 
//System.out.println("Or continuing to the next node, no add.");
					toString(n.nodes[i], out, 
						stem.append((char) ('a' + i)));
					stem.setLength(stem.length() - 1);
				}
			}
		}
	}	
	
	@Override
	public int hashCode(){
		return (1237 * (words + nodes));
		// multiplying words and nodes will
		// give a hash of zero for all empty 
		// tries... so will adding them,
		// most likely.  whatever.
	}
	
	@Override
	public boolean equals(Object o){
		if (o == null){
			return false;
		}
		if (o == this){
			return true;
		}
		if (o.getClass() != this.getClass()){
			return false;
		}
		
		dictionary d = (dictionary) o;
		
		if (d.getNodeCount() != nodes || d.getWordCount() != words){
			return false;
		}
		else{
			return root.equals(d.root);
		}
	}

	public class INode implements ITrie.INode{
		
		public void INode(){
			count = 0;
		}
		
		public int count;
		public INode[] nodes = new INode[26];
		
		public int getValue(){
			return count;
		}

		@Override
		public boolean equals(Object o){

			if (o == null){
				return false;
			}
			if (o == this){
				return true;
			}
			if (o.getClass() != this.getClass()){
				return false;
			}

			INode n = (INode) o;
	
			if (count != n.getValue()){
				return false;
			}
			
			boolean retVal = true;

			for(int i = 0; i < 26; i++){
				if(nodes[i] == null){
					if(n.nodes[i] != null){
						return false;
					}					
				}
				if(nodes[i] == null && n.nodes[i] == null){
					retVal = retVal && true;
				}
				else {
					retVal = retVal && nodes[i].equals(n.nodes[i]);
				}
				if(retVal == false){
					return false;
				}
			}

			return retVal;
		}
	}
}

