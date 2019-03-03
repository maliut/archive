

import org.abego.treelayout.demo.TextInBox;
import org.abego.treelayout.util.DefaultTreeForTreeLayout;

import tableau.TNode;

public class TreeBuilder {
	
	private DefaultTreeForTreeLayout<TextInBox> tree;

	public static DefaultTreeForTreeLayout<TextInBox> build(TNode root) {
		TreeBuilder tb = new TreeBuilder();
		TextInBox tbRoot = new TextInBox(root.toString(), getWidth(root.toString()), 25);
		tb.tree = new DefaultTreeForTreeLayout<TextInBox>(tbRoot);
		if (root.left != null && !root.left.content.equals("")) addNode(tb, tbRoot, root.left);
		if (root.right != null && !root.right.content.equals("")) addNode(tb, tbRoot, root.right);
		return tb.tree;
	}
	
	private static void addNode(TreeBuilder tb,TextInBox tbParent, TNode root) {
		// 构造当前节点
		TextInBox tbRoot = new TextInBox(root.toString(), getWidth(root.toString()), 25);
		// 先序遍历增加节点。必须先加上根节点
		tb.tree.addChild(tbParent, tbRoot);
		if (root.left != null && !root.left.content.equals("")) addNode(tb, tbRoot, root.left);
		if (root.right != null && !root.right.content.equals("")) addNode(tb, tbRoot, root.right);
	}
	
	private static int getWidth(String content) {
		int length = 0;
		for (char c : content.toCharArray()) length += (c == '(' || c == ')') ? -8 : 18;
		return length/2;
	}

}
