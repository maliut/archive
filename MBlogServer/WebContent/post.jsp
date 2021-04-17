<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  errorPage="error.jsp" %>
<%@ page import="model.*" %>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;%>
<% 
UserModel targetUser = (UserModel) request.getAttribute("user"); 
PostModel post = (PostModel) request.getAttribute("post");
PostModel prevPost = (PostModel) request.getAttribute("prevPost");
PostModel nextPost = (PostModel) request.getAttribute("nextPost");
CategoryModel category = (CategoryModel) request.getAttribute("category"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=post.getTitle() %>&nbsp;-&nbsp;<%=targetUser.getBlogName() %></title>
<link href="<%=basePath%>/css/home.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<%@ include file="header.jsp"%>
	<%@ include file="banner.jsp"%>
	<%@ include file="aside.jsp" %>
	<%	// 处理阅读数
	boolean isAdminLogin = targetUser.getUsername().equals(username) ? true : false;
	int added = ((user != null) && (!isAdminLogin)) ? 1 : 0;
	if (added == 1) post.addViewNum(); %>
	<main class="site-main" role="main">
	<div id="main-wrapper">
		<article>
			<h1 class="post-title"><%=post.getTitle() %></h1>
			<p class="post-info">Time:<a href="javascript:void(0)"><%=post.getPublishTime() %></a>　From:<a href="category?c=<%=category.getCategoryID() %>" title="查看<%=category.getName() %>下的所有文章"><%=category.getName() %></a>　
			<% if (!post.getTags().isEmpty()) { %>
				Tags:
					<% for (int i = 0; i < post.getTags().size(); i++) { %>
						<a href="tag?t=<%=post.getTags().get(i).getTagID() %>"><%=post.getTags().get(i).getName() %></a>
					<% } %>
			<% } %></p>
			<div class="post-content"><%=post.getContent() %></div>

			<div class="post-other">
				<p><a>阅读(<%=post.getViewNum() + added %>)</a>　
				<% if (user == null) { // not logined %>
					<a href="#" onclick="alert('请先登录');openLogin()" id="like">赞(<span id="likeNum"><%=post.getLikeNum() %></span>)</a>　
				<% } else { %>
					<a href="javascript:void(0)" onclick="changeLike(<%=post.getPostID() %>, <%=user.getUID() %>)" id="like"><span id="likeText">
					<% if (LikeModel.personalLiked(post.getPostID(), user.getUID())) { %>取消赞<% } else { %>赞<% }%></span>
					(<span id="likeNum"><%=post.getLikeNum() %></span>)</a>　
				<% } %>
				<% if (isAdminLogin) { %>
					<a href="editor.jsp?id=<%=post.getPostID() %>">编辑</a>　<a href="javascript:void(0)" onclick="deletePost(<%=post.getPostID() %>,<%=user.getUID() %>)">删除</a>
				<% } %></p>
			</div>
		</article>



		<div class="prev-next">
			<% if (prevPost != null) { %>
				<p class="prev"><a href="post?p=<%=prevPost.getPostID() %>">&larr;<%=prevPost.getTitle() %></a></p>
			<% } else { %>
				<p class="prev"><a>&larr;当前已是第一篇</a></p>
			<% } %>
			<% if (nextPost != null) { %>
				<p class="next"><a href="post?p=<%=nextPost.getPostID() %>"><%=nextPost.getTitle() %>&rarr;</a></p>
			<% } else { %>
				<p class="next"><a>当前已是最后一篇&rarr;</a></p>
			<% } %>
		</div>

		<div class="post-comment">
			<p>对此文章的评论</p>
			<% 
			List<CommentModel> comments = (List<CommentModel>) request.getAttribute("comments");  
			if (comments != null) {  // 有评论则显示，否则不操作
				for (int i = 0; i < comments.size(); i++) { %>
					<div id="<%=comments.get(i).getCommentID() %>">
						<p class="comment-username"><%=UserModel.getUserFromUID(comments.get(i).getUserID()).getUsername() %></p>
						<p class="comment-content"><%=comments.get(i).getContent() %></p>
						<p class="comment-time"><%=comments.get(i).getPublishTime() %>
						<% if (isAdminLogin || (user != null && user.getUID() == comments.get(i).getUserID())) { %>
							<a class="comment-del" href="javascript:void(0)" onclick="deleteOther('comment',<%=comments.get(i).getCommentID() %>)" >删除</a>
						<% } %></p>
					</div>
			<% 	}
			} %>
		</div>

		<div class="comment-editor">
			<% if(user != null) { %>
			<p id="comment">发表评论</p>
			<form id="commentForm" method="post" action="new">
				<input type="hidden" name="fromPath" value=<%=fromPath%> />
				<input type="hidden" name="postID" value=<%=post.getPostID() %> />
				<input type="hidden" name="type" value="comment" />
				<p><textarea name="content"></textarea></p>
				<p><input type="submit" /></p>
			</form>
			<% } else { %>
				<p>请<a href="#" onclick="openLogin()" class="innerlink">登录</a>后发表评论……没有账号？<a href="#" onclick="openRegister()" class="innerlink">点此注册</a></p>
			<% } %>
		</div>
	</div>
	</main>
	<%@ include file="footer.jsp"%>
	<script type="text/javascript" src="<%=basePath%>/js/like.js"></script>
	<script type="text/javascript" src="<%=basePath%>/js/delete.js"></script>
</body>
</html>