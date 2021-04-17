package tool;

public class Digest {

	public static String getPostDigest(String text) {
		// 思路：得到第一个p元素的内容，如果没有p元素，那么全部显示出来
		text = text.trim();  
		String digest = text.split("</p>")[0];
		if (!digest.equals(text)) {  // 被切割了
			return digest + "</p>";
		}
		return digest;
	}
	
	public static String getSearchDigest(String text, String keyword) {
		// 用于搜索页面，得到搜索关键词周围部分的内容（多个关键词时取第一个）并消除格式，图片、视频等
		String keyword1 = keyword.split("\\s+")[0];
		int radius = 50;
		text = text.replaceAll("<[^<]+>", "");
		if (text.length() > 0) {
			int index = text.indexOf(keyword1);
			if (index > -1) {  // 存在，理论上应该都是存在
				int indexL = Math.max(0, index - radius);
				int indexR = Math.min(text.length() - 1, index + radius);
				String digest = text.substring(indexL, indexR + 1);
				digest = ((indexL == 0) ? "" : "…") + digest;
				digest += (indexR == text.length() - 1) ? "" : "…";
				digest = highlight(digest, keyword);
				return digest;
			} else {
				int indexR = Math.min(text.length() - 1, 2 * radius);
				String digest = text.substring(0, indexR + 1);
				digest += (indexR == text.length() - 1) ? "" : "…";
				return digest;
			}
		} else {
			return "";
		}		
	}
	
	public static String highlight(String text, String keyword) {
		String[] keywords = keyword.split("\\s+");
		for (int i = 0; i < keywords.length; i++) {
			text = text.replaceAll("(?i)" + keywords[i], "<!>" + keywords[i] + "</!>");
			// 如果一开始就替换成具体的代码，那么后面一次替换有可能会破坏掉
		}
		text = text.replaceAll("<!>", "<span style='color:red'>").replaceAll("</!>", "</span>");
		return text;
	}
	
	public static boolean trueLike(String text, String keyword) {
		// 用于检测搜索结果是否真正包含关键词，因为sql查询时标签里的词也能查到，但实际标签是不包含在搜索的内容中的，因此必须检测
		// 用在search.jsp的循环中，如果不是真正like那么跳过那一条结果的输出
		// 但是副作用是不能写分页了，因为现在的分页写法是集成在sql查询语句里的，但是查询前并不能知道是否是真正like
		// sigh // 用于检测正文，因为标题是纯文本
		String[] keywords = keyword.split("\\s+");
		text = text.replaceAll("<[^<]+>", "");
		for (int i = 0; i < keywords.length; i++) {
			if (!text.contains(keywords[i])) {
				return false;
			}
		}
		return true;
	}
	
}
