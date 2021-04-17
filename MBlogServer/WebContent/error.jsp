<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<%
 String path = request.getContextPath();
 String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path;%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>出错了！</title>
<link href="<%=basePath%>/css/error.css" rel="stylesheet" type="text/css" />
</head>
<body>
<div>
<h1>出错了_(:зゝ∠)_</h1>
<p>您可以按照下列步骤尝试解决问题：</p>
<ol>
<li>不要通过直接更改浏览器的路径访问网站内容</li>
<li>尝试刷新此页面</li>
</ol>
<p>　　</p>
<p>　　</p>
<p>如果上述信息依然无法帮助您解决问题，请记录出错的url和使用的浏览器并<a href="mailto:14302010030@fudan.edu.cn">联系管理员</a></p>
</div>
</body>
</html>