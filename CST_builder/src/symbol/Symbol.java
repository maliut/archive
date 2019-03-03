package symbol;

public class Symbol {
	
	public int depth; // 嵌套深度
	public int sub;   // 符号数组中的下标
	public String content, display;  // 内容和显示的内容
	
	public static Symbol create(String value, int depth, int sub) {
		Symbol ret = null;
		if (isLeftParenthesis(value)) {
			ret = new LeftParenthesis();
		} else if (isRightParenthesis(value)) {
			ret = new RightParenthesis();
		} else if (value.matches("\\\\and|\\\\or|\\\\not|\\\\imply|\\\\eq")) {
			ret = new Connective(value);
		} else if (value.matches("[A-Z]+(_\\{\\d*\\})?")) {
			ret = new Letter(value);
		}
		ret.depth = depth;
		ret.sub = sub;
		return ret;
	}
	
	public static Symbol createEmptyLetter(int depth, int sub) {
		Symbol ret = new Letter("");
		ret.depth = depth;
		ret.sub = sub;
		return ret;
	}
	
	public static boolean isLeftParenthesis(String s) {
		return s.equals("(");
	}
	
	public static boolean isRightParenthesis(String s) {
		return s.equals(")");
	}
	
	public static boolean isConnectiveNot(String s) {
		return s.equals("\\not");
	}

}
