<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ page import="model.*" %><%@page import="tool.Digest"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;%>
<%
UserModel targetUser = (UserModel) request.getAttribute("user"); 
CategoryModel category = (CategoryModel) request.getAttribute("category"); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=category.getName() %>&nbsp;-&nbsp;<%=targetUser.getBlogName() %></title>
<link href="<%=basePath%>/css/home.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="header.jsp"%>
	<%@ include file="banner.jsp"%>
	<%@ include file="aside.jsp" %>
	<main class="site-main" role="main">
	<div id="main-wrapper">
		<p class="entry-prompt">查看<%=category.getName() %>下的所有文章</p>
		<% 
		boolean isAdminLogin = targetUser.getUsername().equals(username) ? true : false;
		List<PostModel> posts = (List<PostModel>) request.getAttribute("posts"); 
		if (posts == null || posts.size() == 0) {  // 没有文章  %>
			<p class="post-prompt">该用户当前没有文章<br /><br /><br /><br /><br /></p>
		<% } else { 
		 	for (int i = 0; i < posts.size(); i++) {  %>
				<article id="<%=posts.get(i).getPostID() %>">
					<h1 class="post-title"><a href="post?p=<%=posts.get(i).getPostID() %>"><%=posts.get(i).getTitle() %></a></h1>
					<p class="post-info">Time:<a href="###"><%=posts.get(i).getPublishTime() %></a>　From:<a href="category?c=<%=category.getCategoryID() %>" title="查看<%=category.getName() %>下的所有文章"><%=category.getName() %></a>　
						<% if (!posts.get(i).getTags().isEmpty()) { %>
							Tags:
								<% for (int j = 0; j < posts.get(i).getTags().size(); j++) { %>
									<a href="tag?t=<%=posts.get(i).getTags().get(j).getTagID() %>"><%=posts.get(i).getTags().get(j).getName() %></a>
								<% } %>
						<% } %>
					<% if (isAdminLogin) { %>
						<span class="post-admin"><a href="editor.jsp?id=<%=posts.get(i).getPostID() %>">编辑</a>&nbsp;&nbsp;<a href="javascript:void(0)" onclick="deleteOther('post',<%=posts.get(i).getPostID() %>)">删除</a></span>
					<% } %></p>	
					<div class="post-content">
						<%=Digest.getPostDigest(posts.get(i).getContent()) %>
						<p><a href="post?p=<%=posts.get(i).getPostID() %>">继续阅读&rarr;</a></p>
					</div>
				</article>
			<% } 
		} %>

		<%@ include file="pageSwitch.jsp"%>	
	</div>
	</main>
	<%@ include file="footer.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/delete.js"></script>
	<!-- 为选中的分类着色 -->
	<script>document.getElementById("<%="c" + category.getCategoryID() %>").className="selected";</script>
</body>
</html>