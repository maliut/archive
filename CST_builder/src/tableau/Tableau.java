package tableau;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import propositionTree.Node;

public class Tableau {
	
	public TNode root;
	private Queue<TNode> premises = new LinkedList<>();
	private List<TNode> nodesIsReduced = new ArrayList<>();
	public FileWriter out;
	
	public Tableau(Node root, Queue<Node> premises) {
		this.root = new TNode(false, root);
		this.root.depth = 0;
		this.root.dicOrderStr = "0";
		while (premises.size() > 0) {
			this.premises.add(new TNode(true, premises.remove()));
		}
	}
	
	public void build() throws IOException {
		while (true) {
			TNode nodeToReduce = poll();	// 从树中取出一个正在归约的底层节点
			if (nodeToReduce == null) break;
			// 归约
			pprint(nodeToReduce);
			if (!isReduced(nodeToReduce) && isReducable(nodeToReduce)) {
				reduce(nodeToReduce);
			}
			// 引入
			if (!premises.isEmpty()) {	// 有前件
				Collection<TNode> leafNodesTmp = getLeafNodesTmp(root); 	// 得到所有叶子节点
				TNode aPremise = premises.remove();
				leafNodesTmp.forEach(leaf -> addOnTree(leaf, (TNode) aPremise.clone()));
			}
		}
		// 能到这里说明已经有不矛盾的路径了，否则在找路径那一步就会退出
		// 当然这里第一步也是先找路径
		findRefutation();
	}
	
	// 若不能被表证明，寻找反例并输出之
	public void findRefutation() throws IOException {
		// 找出所有的路径
		TNode leaf = getLeafNodesTmp(root).stream().findFirst().get(); 	// 任意取一个不矛盾路径的叶节点
		List<TNode> outputList = new ArrayList<>();	// 要输出的命题字母
		while (leaf != null) {
			if (isReducable(leaf)) {	// 不是命题字母
				leaf = leaf.parent;		// 接着找上一个
				continue;	
			}
			if (!outputList.contains(new TNode(leaf.value, leaf.content))) {
				outputList.add(leaf);
			}
			leaf = leaf.parent;
		}
		writeFile("表可反驳！存在如下反例：");
		outputList.forEach(e -> writeFile(e + ","));
		writeFile("其余任意。\r\n");
	}

	private List<TNode> reIntroduce(TNode node) {
		List<TNode> ret = new ArrayList<>();  // 返回重新引入后的节点以供原子表展开。否则展开的是clone前的，已经是非叶子了
		// 得到需要加入的叶子节点
		Collection<TNode> leafNodesTmp = getLeafNodesTmp(node);	// 以当前节点以下的叶子节点后面重抄
		leafNodesTmp.forEach(n -> {
			TNode newNode = (TNode) node.clone();
			ret.add(newNode);
			addOnTree(n, newNode);
		});
		return ret;
	}

	private void reduce(TNode node) {
		// 调用这个方法说明必定还能归约
		List<TNode> newNodes = reIntroduce(node);  // 重新抄一遍
		newNodes.forEach(n -> atomic(n));  // 原子表展开
		nodesIsReduced.add(node);	// 标记为已归约
	}
	
	private void atomic(TNode node) {
		String connective = node.content.connective.content;
		if (node.value) {	// T(xxx)
			switch (connective) {
			case "\\and": {
				TNode t1 = new TNode(true, node.content.left);
				TNode t2 = new TNode(true, node.content.right);
				addOnTree(node, t1);
				addOnTree(t1, t2);
				break;	
			} case "\\or": {
				TNode t1 = new TNode(true, node.content.left);
				TNode t2 = new TNode(true, node.content.right);
				addOnTree(node, t1);
				addOnTree(node, t2);
				break;
			} case "\\not": {
				TNode t1 = new TNode(false, node.content.right);
				addOnTree(node, t1);
				break;
			} case "\\imply": {
				TNode t1 = new TNode(false, node.content.left);
				TNode t2 = new TNode(true, node.content.right);
				addOnTree(node, t1);
				addOnTree(node, t2);
				break;
			} case "\\eq": {
				TNode t11 = new TNode(true, node.content.left);
				TNode t12 = new TNode(true, node.content.right);
				addOnTree(node, t11);
				addOnTree(t11, t12);
				TNode t21 = new TNode(false, node.content.left);
				TNode t22 = new TNode(false, node.content.right);
				addOnTree(node, t21);
				addOnTree(t21, t22);
				break;
			}
			}
		} else {  // F(xxx)
			switch (connective) {
			case "\\and": {
				TNode t1 = new TNode(false, node.content.left);
				TNode t2 = new TNode(false, node.content.right);
				addOnTree(node, t1);
				addOnTree(node, t2);
				break;	
			} case "\\or": {
				TNode t1 = new TNode(false, node.content.left);
				TNode t2 = new TNode(false, node.content.right);
				addOnTree(node, t1);
				addOnTree(t1, t2);
				break;
			} case "\\not": {
				TNode t1 = new TNode(true, node.content.right);
				addOnTree(node, t1);
				break;
			} case "\\imply": {
				TNode t1 = new TNode(true, node.content.left);
				TNode t2 = new TNode(false, node.content.right);
				addOnTree(node, t1);
				addOnTree(t1, t2);
				break;
			} case "\\eq": {
				TNode t11 = new TNode(true, node.content.left);
				TNode t12 = new TNode(false, node.content.right);
				addOnTree(node, t11);
				addOnTree(t11, t12);
				TNode t21 = new TNode(false, node.content.left);
				TNode t22 = new TNode(true, node.content.right);
				addOnTree(node, t21);
				addOnTree(t21, t22);
				break;
			}
			}
		}
		
	}

	// 判断节点是否已经被归约过
	private boolean isReduced(TNode node) {
		// 1. 找到保存的已归约节点中是否含有内容相同的节点
		List<TNode> savedNodes = nodesIsReduced.stream().filter(n -> n.equals(node)).collect(Collectors.toList());
		if (savedNodes.isEmpty()) return false;
		// 2. 对于每个节点，判断是否和当前节点有直接父子关系（如果没有则说明和其他已归约节点在不同的路径上，因此仍然需要归约当前节点）
		for (TNode sn: savedNodes) {
			if (sn.isParentOrChildOf(node)) return true;
		}
		return false;
	}
	
	// 判断节点是否能被归约（是否为命题字母）
	private boolean isReducable(TNode node) {
		return node.content.left != null;	// 有孩子说明还能归约
	}

	private void addOnTree(TNode parent, TNode child) {
		if (parent.isEndOfPath) return;
		child.depth = parent.depth + 1;
		parent.addChild(child);
		child.dicOrderStr = parent.dicOrderStr + ((child == parent.left) ? "0" : "1");
		provePath(child);
	}
	
	private void provePath(TNode child) {
		TNode current = child;
		List<TNode> trueList = new ArrayList<>();
		List<TNode> falseList = new ArrayList<>();
		while (current != null) {
			if (current.value) { 	// T(xxx)
				if (falseList.contains(new TNode(false, current.content))) {  // 已经有 F(xxx)了
					child.isEndOfPath = true;
				} else {
					trueList.add(child);	// 记录到 true 的表值中
				}
			} else {	// F(xxx)
				if (trueList.contains(new TNode(true, current.content))) {  // 已经有 F(xxx)了
					child.isEndOfPath = true;
				} else {
					falseList.add(current);	// 记录到 true 的表值中
				}
			}
			current = current.parent;
		}
	}

	/* ****************************************************
	/* 得到以当前节点为根的子树的所有叶节点，不含已矛盾的节点
	 * 若所有叶节点均已矛盾，则说明已经被表证明。输出并退出
	 * ****************************************************/
	private Collection<TNode> getLeafNodesTmp(TNode node) {
		Collection<TNode> leafNodesTmp = new ArrayList<>();
		getLeafNodesTmpRec(leafNodesTmp, node);
		if (node == root && leafNodesTmp.isEmpty()) {	// 根节点扫描时才判断是否要结束
			try {
				// 已经没有可以被插入的叶节点了
				writeFile("表可证明！所有路径均矛盾。");
				out.close();
			} catch (IOException e) {}
			System.exit(0);
		}
		return leafNodesTmp;
	}

	private void getLeafNodesTmpRec(Collection<TNode> leafNodesTmp, TNode node) {
		if (node.left != null) getLeafNodesTmpRec(leafNodesTmp, node.left);
		if (node.right != null) getLeafNodesTmpRec(leafNodesTmp, node.right);
		if (node.left == null && node.right == null) {
			if (!node.isEndOfPath) leafNodesTmp.add(node);
		}
	}
	
	// 格式化输出一个节点
	private void pprint(TNode nodeToReduce) {
		writeFile(nodeToReduce.toString().replaceAll("\\( ", "(").replaceAll(" \\)", ")") + "\r\n");
	}
	
	// 取出最底层的 node
	private TNode poll() {
		List<TNode> allNodes = new ArrayList<>();
		getAllNodes(allNodes, root);
		allNodes.sort((n1, n2) -> (n1.dicOrderStr.length() - n2.dicOrderStr.length() == 0) ? n1.dicOrderStr.compareTo(n2.dicOrderStr) : (n1.dicOrderStr.length() - n2.dicOrderStr.length()));
		if (allNodes.stream().filter(n -> !n.isBigReduced).count() == 0) return null;
		TNode ret = allNodes.stream().filter(n -> !n.isBigReduced).findFirst().get();
		ret.isBigReduced = true;
		return ret;
	}
	
	private void getAllNodes(List<TNode> allNodes, TNode node) {
		allNodes.add(node);
		if (node.left != null) getAllNodes(allNodes, node.left);
		if (node.right != null) getAllNodes(allNodes, node.right);
	}

	private void writeFile(String content) {
		try {
			out.write(content);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
}
