<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp" %>
<%@ page import="model.*" %>
<%@ page import="java.util.List" %>
<% String fromPath = request.getRequestURL().toString(); 
//注意此处的路径不经过header.jsp里的那种处理，因为后台只有管理员能进，所以可以从cookie直接获得用户信息，不用从servlet跳转 %>
<% UserModel user = UserModel.getUserFromCookie(request.getCookies()); 
if (user == null) {
	response.getWriter().print("<script type='text/javascript'>alert('非法操作！');window.location.href='index.jsp';</script>");
} %>
<%  // 不用mvc了
String type = request.getParameter("type");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>内容管理&nbsp;-&nbsp;<%=user.getBlogName() %></title>
<link href="css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="header.jsp"/>
	<jsp:include page="admin-aside.jsp"/>
	<main class="admin-main">
		<h1>内容管理</h1>
		<table>
		<% if (type.equals("category")) {  // category
			List<CategoryModel> c = CategoryModel.getCategoriesFromUID(user.getUID()); %>
			<th>分类管理</th>
			<tr><td>ID</td><td>名称</td><td>文章数量</td><td>操作</td></tr>
			<% for (int i = 0; i < c.size(); i++) {
		  		if (c.get(i).getName().equals("未分类")) continue; %>
		  		<tr id="<%=c.get(i).getCategoryID() %>"><td><%=c.get(i).getCategoryID() %></td><td><input type="text" id="name<%=i %>" value="<%=c.get(i).getName() %>" /></td><td><%=c.get(i).getPostNum() %></td><td><a href="javascript:void(0)" onclick="saveChange('category',<%=c.get(i).getCategoryID() %>,document.getElementById('name<%=i %>').value)">保存</a><a href="javascript:void(0)" onclick="deleteOne('category',<%=c.get(i).getCategoryID() %>)">删除</a></td></tr>
		  	<% } %>
		  	<tr><td>&nbsp;</td></tr><tr><td>新分类</td><td colspan="2"><input type="text" id="addOne"/></td><td><a href="javascript:void(0)" onclick="addOne('category',<%=user.getUID() %>,document.getElementById('addOne').value)">新增</a></td></tr>
		<% } else if (type.equals("tag")) { 
			List<TagModel> c = TagModel.getTagsFromUID(user.getUID()); %>
			<th>标签管理</th>
			<tr><td>ID</td><td>名称</td><td>操作</td></tr>
			<% for (int i = 0; i < c.size(); i++) { %>
		  		<tr id="<%=c.get(i).getTagID() %>"><td><%=c.get(i).getTagID() %></td><td><input type="text" id="name<%=i %>" value="<%=c.get(i).getName() %>" /></td><td><a href="javascript:void(0)" onclick="saveChange('tag',<%=c.get(i).getTagID() %>,document.getElementById('name<%=i %>').value)">保存</a><a href="javascript:void(0)" onclick="deleteOne('tag',<%=c.get(i).getTagID() %>)">删除</a></td></tr>
		  	<% } %>	
		  	<tr><td>&nbsp;</td></tr><tr><td>新标签</td><td><input type="text" id="addOne"/></td><td><a href="javascript:void(0)" onclick="addOne('tag',<%=user.getUID() %>,document.getElementById('addOne').value)">新增</a></td></tr>
		<% } else { 
			List<PostModel> c = PostModel.getAllPostsFromUID(user.getUID()); %>
			<th>博文管理</th>
			<tr><td>ID</td><td>标题</td><td>操作</td></tr>
			<% for (int i = 0; i < c.size(); i++) { %>
		  		<tr id="<%=c.get(i).getPostID() %>"><td><%=c.get(i).getPostID() %></td><td><%=c.get(i).getTitle() %></td><td><a href="editor.jsp?id=<%=c.get(i).getPostID() %>">编辑</a><a href="javascript:void(0)" onclick="deleteOne('post',<%=c.get(i).getPostID() %>)">删除</a></td></tr>
		  	<% } 
		} %>
		</table>
	</main>
	<script type="text/javascript" src="js/admin.js"></script>
	<script>document.getElementById("s<%=type %>").className="selected";</script>
</body>
</html>