<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.io.File"%>
<%@ page import="model.CategoryModel" %>
<% 
String avatarPath = request.getServletContext().getRealPath("/") + "/image/avatar/";
File avatar = new File(avatarPath + targetUser.getUID());
String url = "image/avatar/" + ((avatar.exists()) ? targetUser.getUID() : "default"); %>
<aside id="blog-aside">
<div id="aside-wrapper">
	<section class="admin-info">
		<p class="admin-about"><img src="<%=url %>" alt="头像" height="50" width="50" class="admin-avatar"/><br /><%=targetUser.getUsername() %></p>
	</section>

	<section class="categories">
		<h1>Categories</h1>
		<ul>
		<% 
		List<CategoryModel> categories = (List<CategoryModel>) request.getAttribute("categories");
		for (int i = 0; i < categories.size(); i++) {
	  		if (categories.get(i).getName().equals("未分类") && categories.get(i).getPostNum() == 0) continue; %>
			<li id=<%="c" + categories.get(i).getCategoryID() %>><a href="category?c=<%=categories.get(i).getCategoryID() %>"><%=categories.get(i).getName() %></a>(<%=categories.get(i).getPostNum() %>)</li>
		<% 
		} %>
		</ul>
	</section>
</div>
</aside>
    