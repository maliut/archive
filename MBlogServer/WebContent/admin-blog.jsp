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
<title>常规&nbsp;-&nbsp;<%=user.getBlogName() %></title>
<link href="css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<jsp:include page="header.jsp"/>
	<jsp:include page="admin-aside.jsp"/>
	<main class="admin-main">
		<h1>常规设置</h1>
		<form action="admin" method="post" enctype="multipart/form-data" >
			<input type="hidden" name="fromPath" value=<%=fromPath %> />
			<input type="hidden" name="type" value="blog" />
			<p><label>　　　站点图片　　　　　　<input type="file" name="banner" size="50" /></label></p>
			<p><label>　　　站点标题　　　　　　<input type="text" name="blogName" size="50" value=<%=user.getBlogName() %> /></label></p>
			<p><label>　　　　副标题　　　　　　<input type="text" name="blogDes" size="50" value=<%=user.getBlogDes() %> /></label></p>
			<p><label>　　　标题颜色　　　　　　<input type="radio" name="bannerColor" id="0" value="0" />&nbsp;黑　　<input type="radio" name="bannerColor" id="1" value="1" />&nbsp;白</label></p>
			<p>　　　<input type="submit" value="保存" id="submit"/></p>
		</form>
		<script>document.getElementById('<%=user.getBannerColor() %>').checked="checked";</script>
	</main>
	<script>document.getElementById("sblog").className="selected";</script>
</body>
</html>