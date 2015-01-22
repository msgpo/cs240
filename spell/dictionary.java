package spell;

public class ITrie implements ITrie {
	
	public ITrie() {
		words = 0;
		nodes = 0;
	}
	
	private int words;
	private int nodes;
	private INode root;
	
	public void add(String word) {
		// add word to tree, count++
		// inc nodes for every new node
		// words++
	}
	

	public INode find(String word){
		// check if the chain ends in a node with count > 0
		// return that node (presumably the "null"
		// would be automagic) 
		// false results in an exception farther out
		return null;
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
		return null;
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
		
		ITrie d = (ITrie) o;
		
		if (d.getNodeCount() != nodes || d.getWordCount() != words){
			return false;
		}
		else{
			// traverse entire trie and compare
		}
	}

	public class INode {
		
		public void INode(){
			count = 0;
		}
		
		public int count;
		public INode nodes = new INode[26];
		
		public int getValue(){
			return count;
		}
	}
}

