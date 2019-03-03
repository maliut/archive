package tableau;

import propositionTree.Node;

public class TNode {

	public Node content;
	public boolean value;
	public TNode parent, left, right;
	public int depth;
	public boolean isEndOfPath;	// 若该路径为矛盾路径且该节点为该路径最后一个
	public String dicOrderStr = "";
	public boolean isBigReduced;	// 代替之前队列的删除功能
	
	public TNode(boolean value, Node content) {
		this.value = value;
		this.content = content;
	}
	
	public void addChild(TNode child) {
		if (left == null) {
			left = child;
		} else if (right == null) {
			right = child;
		}
		child.parent = this;
	}

	@Override
	public String toString() {
		return (value ? "T " : "F ") + content.content;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof TNode) {
			TNode other = (TNode) o;
			return (this.content.equals(other.content) && this.value == other.value);
		} else {
			return false;
		}
	}

	public boolean isParentOrChildOf(TNode other) {
		//if (!this.toString().equals("F (B→ C)")) return true;
		//System.out.println(this.toString()+this.hashCode()+","+other.toString()+other.hashCode());
		TNode deeper = (this.depth > other.depth) ? this : other;  // 更深的说明在更下面
		TNode remain = (this.depth > other.depth) ? other : this;
		while (deeper != null) {
			if (deeper == remain) return true;	// 完全相同，说明一直向上能找到父亲
			deeper = deeper.parent;
		}
		//System.out.println(false);
		return false;
	}
	
	@Override
	public Object clone() {
		return new TNode(this.value, this.content);
	}
}
