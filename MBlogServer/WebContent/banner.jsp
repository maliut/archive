<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.File"%>
<% 
String bannerPath = request.getServletContext().getRealPath("/") + "/image/banner/";
String type = "";
File f = new File(bannerPath + targetUser.getUID());
String bannerUrl = "image/banner/" + ((f.exists()) ? targetUser.getUID() : "default");
%>
<header role="banner">
	<div id="blog-header">
		<h1 id="blog-name"><a id="blog-name-a" href="home?u=<%=targetUser.getUID() %>"><%=targetUser.getBlogName() %></a></h1>
		<p id="blog-description"><%=targetUser.getBlogDes() %></p>
	</div>
</header>
<script type="text/javascript" src="<%=basePath%>/js/banner.js"></script>
<!-- show header bg -->
<script>showBanner('<%=bannerUrl%>')</script>
<script>setBannerTextColor(<%=targetUser.getBannerColor() %>)</script>