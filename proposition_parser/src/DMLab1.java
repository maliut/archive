import parser.NotWellDefinedException;
import parser.Parser;
import tree.Node;


public class DMLab1 {
	
	public static void main(String[] args) throws NotWellDefinedException {
		String str = "((\\not A_{1}) \\and (A_{} \\imply B))";
		Parser parser = new Parser(str);
		parser.preParse();
		parser.parse();
		printTree(parser.root);
	}
	
	private static void printTree(Node root) {
		if (root.left != null) printTree(root.left);
		System.out.print(root.content);
		if (root.right != null) printTree(root.right);
	}

}
