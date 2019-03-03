package propositionTree;

import symbol.Connective;

public class Node {
	
	public String content;
	public Connective connective;
	public Node parent, left, right;
	
	public Node(String content, Connective connective) {
		this.content = content;
		this.connective = connective;
	}
	
	public void addChild(Node child) {
		if (left == null) {
			left = child;
		} else if (right == null) {
			right = child;
		}
		child.parent = this;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Node) {
			Node other = (Node) o;
			return (this.content.equals(other.content));
		} else {
			return false;
		}
	}

}
