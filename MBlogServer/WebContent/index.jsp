<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="model.PostModel" %>
<%@ page import="model.UserModel" %>
<%
List<UserModel> hotUsers = UserModel.getHotUsers();
List<PostModel> hotPosts = PostModel.getHotPosts();
List<PostModel> latestPosts = PostModel.getLatestPosts();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>myBlog</title>
<link href="css/index.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="header.jsp"/>
	<div id="bg">
		<div id="index-static">
			<img id="logo" alt="logo" src="image/logo.png"><div id="word">最纯粹的写作体验</div>	
			<input type="button" value="立即体验" onclick="openRegister()"/><input type="button" value="　登录　" onclick="openLogin()"/>
		</div>
		<div id="index-list">
			<div id="hotUser" class="chart">热门用户
				<ol>
				<% for (int i = 0; i < hotUsers.size(); i++) { %>
					<li><a href="home?u=<%=hotUsers.get(i).getUID() %>"><%=hotUsers.get(i).getUsername() %></a><span class="sortIndex">(<%=hotUsers.get(i).getPostNum() %>)</span></li>
				<% } %>
				</ol>
			</div>
			<div id="latestPost" class="chart">最新博文
				<ol>
				<% for (int i = 0; i < latestPosts.size(); i++) { 
					String title = (latestPosts.get(i).getTitle().length() > 10) ? latestPosts.get(i).getTitle().substring(0, 9)+"…" : latestPosts.get(i).getTitle(); %>
					<li><a href="post?p=<%=latestPosts.get(i).getPostID() %>"><%=title %></a></li>
				<% } %>
				</ol>
			</div>
			<div id="hotPost" class="chart">热门博文
				<ol>
				<% for (int i = 0; i < hotPosts.size(); i++) { 
					String title = (hotPosts.get(i).getTitle().length() > 8) ? hotPosts.get(i).getTitle().substring(0, 7)+"…" : hotPosts.get(i).getTitle(); %>
					<li><a href="post?p=<%=hotPosts.get(i).getPostID() %>"><%=title %></a><span class="sortIndex">(<%=hotPosts.get(i).getLikeNum() %>)</span></li>
				<% } %>
				</ol>
			</div>
		</div>
	</div>
</body>
</html>