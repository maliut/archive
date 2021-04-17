package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.CategoryModel;
import model.CommentModel;
import model.PostModel;
import model.TagModel;
import model.UserModel;

/**
 * Servlet implementation class New
 */
@WebServlet("/new")
public class New extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public New() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {  // 处理ajax删除标签与文章的关联
			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			response.setCharacterEncoding("UTF-8");
			int postID = Integer.parseInt(request.getParameter("postID"));
			int tagID = Integer.parseInt(request.getParameter("tagID"));
			TagModel.deletePost_tag(postID, tagID);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.setCharacterEncoding("UTF-8");
		// get type
		String type = request.getParameter("type");
		//System.out.print("".split("\\s+|,")[0].equals(""));
		if (type.equals("comment")) {
			addComment(request, response);
		} else if (type.equals("post")) {
			commomDeal(request, response);
			String id = request.getParameter("id");
			if (id.equals("null")) {
				addPost(request, response);
			} else {
				updatePost(request, response);
			}
		} else {
			response.sendRedirect("error.jsp");
		}
	}

	private void commomDeal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 处理新增分类、标签等
		try {
			UserModel user = UserModel.getUserFromCookie(request.getCookies());
			// 分类处理
			String newCategory = request.getParameter("newCategory").trim().split(" ")[0];
			if (!newCategory.equals("")) { // new
				CategoryModel existCategory = CategoryModel.getCategoryFromName(newCategory, user.getUID());
				if (existCategory == null) {  // 不存在名字相同的旧有分类
					int newCID = CategoryModel.addCategory(newCategory, user.getUID());
					request.setAttribute("categoryID", newCID);
				} else {
					request.setAttribute("categoryID", existCategory.getCategoryID());
				}
			} else {
				int selectedCID = Integer.parseInt(request.getParameter("category"));
				request.setAttribute("categoryID", selectedCID);
			}
			// 标签处理
			String[] tagsName = request.getParameter("tags").trim().split("\\s+|,");
			List<Integer> tagsID = new ArrayList<Integer>();
			if (!tagsName[0].equals("")) {  // 输入了标签
				for (int i = 0; i < tagsName.length; i++) {
					int tagID = TagModel.existTag(tagsName[i], user.getUID());
					if (tagID == -1) {  // 不存在原有标签
						tagID = TagModel.addTag(tagsName[i], user.getUID());
					}
					if (!tagsID.contains(tagID)) { // 排除用户输入多次一样的标签的现象
						tagsID.add(tagID);
					}
				}
				request.setAttribute("tagsID", tagsID);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	private void updatePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			int id = Integer.parseInt(request.getParameter("id"));
			PostModel post = PostModel.getPostFromPID(id);
			int categoryID = (int) request.getAttribute("categoryID");
			List<Integer> tagsID = (List<Integer>) request.getAttribute("tagsID");
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			post.setCategoryID(categoryID);
			if (tagsID != null && tagsID.size() > 0) { // 有设置过标签
				// 如果这篇文章已经有这个标签了那么不能再增加
				for (int i = 0; i < tagsID.size(); i++) {
					if (TagModel.existPost_tag(id, tagsID.get(i))) tagsID.set(i, null);  
				}  // 不能直接删除而是要设null，否则多个重复时下标会乱，到增加的时候再判断是否为null
				if (tagsID.size() > 0) post.setTags(tagsID);
			}
			post.setTitle(title);
			post.setContent(content);
			response.sendRedirect("post?p=" + id);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
	}

	private void addPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			UserModel user = UserModel.getUserFromCookie(request.getCookies());
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int categoryID = (int) request.getAttribute("categoryID");
			int pid = PostModel.addPost(title, content, categoryID, user.getUID());
			// 处理标签
			List<Integer> tagsID = (List<Integer>) request.getAttribute("tagsID");
			if (tagsID != null && (!tagsID.get(0).equals(""))) {
				for (int i = 0; i < tagsID.size(); i++) {
					TagModel.addPost_tag(pid, tagsID.get(i));
				}
			}
			response.sendRedirect("post?p=" + pid);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}
		
	}

	private void addComment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// get parameter
			String content = request.getParameter("content").trim();
			String fromPath = request.getParameter("fromPath");
			int postID = Integer.parseInt(request.getParameter("postID"));
			if (content.equals("")) {
				response.sendRedirect(fromPath);
				return;
			}
			// create
			PostModel post = PostModel.getPostFromPID(postID);
			UserModel user = UserModel.getUserFromCookie(request.getCookies());
			// change
			post.deltaCommentNum(1);
			CommentModel.addComment(content, user.getUID(), postID);
			// back
			response.sendRedirect(fromPath);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect("error.jsp");
		}

	}

}
