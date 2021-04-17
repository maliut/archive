<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="model.PostModel" %>
<%@ page import="model.UserModel" %>
<%@ page import="tool.Digest" %>
<% int state = (int) request.getAttribute("state"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>搜索</title>
<link href="css/search.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="header.jsp"/>
	<div class="search-setting">
		<form action="search" method="get" id="searchForm">
			<div class="search-main">
				<img id="logo" alt="logo" src="image/logo.png" width="200" height="100" />
				<div class="searchbar">
					<input type="text" name="keyword" id="keyword" value="<%=request.getParameter("keyword") %>" placeholder="请输入搜索内容…" />
					<input type="submit" class="submit" value="搜索" /><a id="switch" href="javascript:void(0)" onclick="showOption()">高级</a>					
				</div>
			</div>
			<div id="search-option" class="search-option" style="display:none">
				<p><label>搜索范围：　　　<input type="radio" name="range" value="0" checked="checked">全站<input type="radio" name="range" value="1" />用户：<input type="text" name="username" placeholder="在此输入用户名"/></label></p>
				<p><label>搜索内容：　　　<input type="radio" name="place" value="title" checked="checked">标题<input type="radio" name="place" value="content">正文<input type="radio" name="place" value="tag">标签</label></p>
				<p><label>排　　序：　　　　　<select id="sort_by" name="sort_by">
					<option value="time">发表时间</option>
					<option value="viewNum">浏览量</option>
				</select><input type="radio" name="sort" value="d" checked="checked">降序<input type="radio" name="sort" value="a">升序</label></p>
			</div>
		</form>
	</div>
	<div class="search-result">
	<% if (state == 0) {
		List<PostModel> posts = (List<PostModel>) request.getAttribute("posts");
		for (int i = 0; i < posts.size(); i++) { 
			if (request.getParameter("place").equals("content") && !Digest.trueLike(posts.get(i).getContent(), request.getParameter("keyword"))) continue;%>	
			<div class="result-one">
				<h1><a class="result-title" href="post?p=<%=posts.get(i).getPostID() %>"><%=Digest.highlight(posts.get(i).getTitle(), request.getParameter("keyword")) %></a></h1>
				<p class="result-content"><%=Digest.getSearchDigest(posts.get(i).getContent(), request.getParameter("keyword")) %></p>
				<p class="result-other"><span class="other-left"><%=posts.get(i).getViewNum() %>次浏览&nbsp;-&nbsp;<%=posts.get(i).getCommentNum() %>个评论&nbsp;-&nbsp;<%=posts.get(i).getLikeNum() %>个点赞</span><span class="other-right"><span class="other-date"><%=posts.get(i).getPublishTime() %></span>&nbsp;-&nbsp;<a href="home?u=<%=posts.get(i).getUserID() %>" class="other-user"><%=UserModel.getUserFromUID(posts.get(i).getUserID()).getUsername() %></a></span></p>
			</div>
		<% } %>
			<p class="search-prompt">没有更多搜索结果</p>
	<% } else if (state == 3) { %>
		<p class="search-prompt">没有找到指定的用户</p>
	<% } else if (state == 2) { %>
		<p class="search-prompt">请输入搜索内容</p>
	<% } else if (state == 1) { %>
		<p class="search-prompt">对不起，没有您想查找的内容</p>
	<% } %>
	</div>
</body>
<script charset="utf-8" src="js/search.js"></script>
</html>