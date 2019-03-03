package tree;

public class Node {
	
	public String content;
	public Node parent, left, right;
	
	public Node(String content) {
		this.content = content;
	}
	
	public void addChild(Node child) {
		if (left == null) {
			left = child;
		} else if (right == null) {
			right = child;
		}
		child.parent = this;
	}

}
