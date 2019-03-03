package parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import symbol.Connective;
import symbol.LeftParenthesis;
import symbol.Letter;
import symbol.RightParenthesis;
import symbol.Symbol;
import tree.Node;

public class Parser {
	
	private static final String REGEX_MATCHER = "(\\\\and|\\\\or|\\\\not|\\\\imply|\\\\eq|\\(|\\)|[A-Z]+(_\\{\\d*\\})?)+";
	private static final String REGEX_FINDER = "\\\\and|\\\\or|\\\\not|\\\\imply|\\\\eq|\\(|\\)|[A-Z]+(_\\{\\d*\\})?";
	
	private List<Symbol> symbols = new ArrayList<>();
	private String proposition;
	private int depth = 0;
	public Node root;  // 解析树的根
	
	public Parser(String proposition) {
		this.proposition = proposition.replaceAll("\\s+", "");
	}
	
	public void preParse() throws NotWellDefinedException {
		// 判断有没有不应当存在的 pattern
		if (!proposition.matches(REGEX_MATCHER)) throw new NotWellDefinedException();
		// 提取每一个元素并加入数组
		Matcher matcher = Pattern.compile(REGEX_FINDER).matcher(proposition);
		while (matcher.find()) {
			String element = matcher.group();
			if (Symbol.isConnectiveNot(element)) symbols.add(Symbol.createEmptyLetter(depth, symbols.size()));
			else if (Symbol.isLeftParenthesis(element)) depth++;
			else if (Symbol.isRightParenthesis(element)) depth--;
			symbols.add(Symbol.create(element, depth, symbols.size()));
		}
	}
	
	public void parse() throws NotWellDefinedException {
		parse(null, 0, symbols.size());
	}
	
	private void parse(Node parent, int fromIndex, int toIndex) throws NotWellDefinedException {
		List<Symbol> ss = symbols.subList(fromIndex, toIndex);  // 得到这次要处理的范围
		Node me = new Node(getDisplayStr(ss));  // 生成对应的 node
		if (root == null) root = me;  // 第一次设置根
		if (parent != null) parent.addChild(me);  // 连接父亲
		// 递归解析开始
		if (ss.size() == 1 && ss.get(0) instanceof Letter) {
			return;  // 只有一个而且是命题字母，正常
		} else {  // 不是命题字母，那要判断出左右节点
			// 1. 必须被左右括号包裹
			if (!(ss.get(0) instanceof LeftParenthesis && ss.get(ss.size()-1) instanceof RightParenthesis)) throw new NotWellDefinedException();
			// 2. 得到深度最小的连接词下标作为分割点
			int splitIndex = ss.stream().filter(s -> s instanceof Connective).min((s1, s2) -> (s1.depth - s2.depth)).get().sub;
			// 3. 处理左右的部分
			parse(me, fromIndex + 1, splitIndex);
			parse(me, splitIndex + 1, toIndex - 1);
		}
	}
	
	private String getDisplayStr(List<Symbol> ss) {
		return String.join("", ss.stream().map(s -> s.display).collect(Collectors.toList()));
	}

}
