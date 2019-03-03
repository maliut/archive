import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import parser.Parser;
import propositionTree.Node;
import tableau.Tableau;

public class DMLab2 {

	public static void main(String[] args) throws IOException {
		List<String> strs = FileReader.readLines("propositions.txt");
		if (strs.size() > 1) strs.remove(1);
		//strs.add("(((A \\imply B) \\imply (A \\imply C)) \\imply (A \\imply (B \\imply C)))");
		//strs.add("(((A \\imply B) \\imply (A \\imply C)) \\imply (B \\imply C))");
		//strs.add("(A \\or (\\not B))");
		//strs.add("(B \\or (\\not C))");
		//strs.add("(C \\or (\\not D))");

		Queue<Node> nodes = new LinkedList<>();
		strs.forEach(s -> {
			Parser p = new Parser(s);
			try {
				p.preParse();
				p.parse();
				nodes.add(p.root);
			} catch (Exception e) {
				System.err.println(s + " is not well-defined!");
				System.exit(1);
			}
		});
		
		Node root = nodes.remove();
		Tableau t = new Tableau(root, nodes);
		t.out = new FileWriter(new File("output.txt"));
		t.build();
		//t.prove();
		t.out.close();
		/*DefaultTreeForTreeLayout<TextInBox> tree = TreeBuilder.build(t.root);
		addTree(tree);
		showTree();*/
	}

	/*private static JFrame frame = new JFrame();
	
	private static void showTree() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}

	private static void addTree(DefaultTreeForTreeLayout<TextInBox> tree) {
		DefaultConfiguration<TextInBox> configuration = new DefaultConfiguration<TextInBox>(20, 20);
		TextInBoxNodeExtentProvider nodeExtentProvider = new TextInBoxNodeExtentProvider();
		TreeLayout<TextInBox> treeLayout = new TreeLayout<TextInBox>(tree, nodeExtentProvider, configuration);
		TextInBoxTreePane panel = new TextInBoxTreePane(treeLayout);
		frame.add(panel);
	}*/
}
