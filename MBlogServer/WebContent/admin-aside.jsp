<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<aside class="admin-aside">
	<h1>后台管理</h1>
	<ul>
		<li id="seditor"><a href="editor.jsp">发表博文</a></li>
		<li id="sblog"><a href="admin-blog.jsp">常规设置</a></li>
		<li id="suser"><a href="admin-user.jsp">用户设置</a></li>
		<li id="spost"><a href="admin-content.jsp?type=post">博文管理</a></li>
		<li id="scategory"><a href="admin-content.jsp?type=category">分类管理</a></li>
		<li id="stag"><a href="admin-content.jsp?type=tag">标签管理</a></li>
	</ul>
</aside>