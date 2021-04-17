<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ page import="model.UserModel" %>
<% String fromPath = request.getRequestURL().toString(); 
//注意此处的路径不经过header.jsp里的那种处理，因为后台只有管理员能进，所以可以从cookie直接获得用户信息，不用从servlet跳转 %>
<% UserModel user = UserModel.getUserFromCookie(request.getCookies()); 
if (user == null) {
	response.getWriter().print("<script type='text/javascript'>alert('非法操作！');window.location.href='index.jsp';</script>");
} %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户&nbsp;-&nbsp;<%=user.getBlogName() %></title>
<link href="css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="header.jsp"/>
	<jsp:include page="admin-aside.jsp"/>
	<main class="admin-main">
		<h1>用户设置</h1>
		<form action="admin" method="post" enctype="multipart/form-data" >
			<input type="hidden" name="fromPath" value=<%=fromPath %> />
			<input type="hidden" name="type" value="user" />
			<p><label>　　　　　头像　　　　　　<input type="file" name="avatar" size="50" /></label></p>
			<p><label>　　　　新密码　　　　　　<input type="password" name="newPassword" size="50" /></label></p>
			<p><label>　　　重复密码　　　　　　<input type="password" name="rePassword" size="50" /></label></p>
			<p><label>　　　当前密码　　　　　　<input type="password" name="oldPassword" size="50" /></label></p>
			<p>　　　<input type="submit" value="保存" id="submit"/></p>
		</form>
	</main>
	<script>document.getElementById("suser").className="selected";</script>
</body>
</html>