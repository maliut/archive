<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% 
String servletPath = request.getServletPath(); 
String targetPath = fromPath.replaceAll("&page=\\d+|page=\\d+&|#", "");
int maxPage = 0;  // 5 为分页，若更改最好与model中一起更改
if (servletPath.contains("search")) {
	
} else if (servletPath.contains("home")) {
	maxPage = (int) Math.ceil(targetUser.getPostNum() / 5.0); 
} else if (servletPath.contains("category")) {
	CategoryModel category2 = (CategoryModel) request.getAttribute("category"); // 我也不知道为什么，不加的话报错说无法编译，但是明明在访问home的时候根本不会执行到，这也太xxx
	maxPage = (int) Math.ceil(category2.getPostNum() / 5.0); 
}

int currentPage = (request.getParameter("page") == null) ? 1 : Integer.parseInt(request.getParameter("page")); 
%>
<div class="page-switch" >
<% if (maxPage > 1) {  // 没有文章就不显示
if (maxPage > 1 && maxPage <= 5) {   // 5页以下，全部显示出来
	for (int i = 1; i <= maxPage; i++) { 
		if (i != currentPage) { %>
			<a href="<%=targetPath + "&page=" + i %>" ><%=i %></a>
		<% } else { %>
			<a id="page-current"><%=i %></a>		
		<% } 
	} 
} else if (currentPage <= 3) {   // 5页以上，但当前在前三页，显示12345>>
	for (int i = 1; i <= 5; i++) { 
		if (i != currentPage) { %>
			<a href="<%=targetPath + "&page=" + i %>" ><%=i %></a>
		<% } else { %>
			<a id="page-current"><%=i %></a>		
		<% } 
	} %>
	<a href="<%=targetPath + "&page=" + 6 %>" >&rarr;</a>
<% } else if (maxPage - currentPage <= 2) {  // 在最后三页  %>
	<a href="<%=targetPath + "&page=" + (maxPage - 5) %>" >&larr;</a>
	<% for (int i = maxPage - 4; i <= maxPage; i++) { 
		if (i != currentPage) { %>
			<a href="<%=targetPath + "&page=" + i %>" ><%=i %></a>
		<% } else { %>
		<a id="page-current"><%=i %></a>		
		<% } 
	}
} else { // 在中间  %>
	<a href="<%=targetPath + "&page=" + (currentPage - 3) %>" >&larr;</a>
	<% for (int i = currentPage - 2; i <= currentPage + 2; i++) { 
		if (i != currentPage) { %>
			<a href="<%=targetPath + "&page=" + i %>" ><%=i %></a>
		<% } else { %>
			<a id="page-current"><%=i %></a>		
		<% } 
	} %>
	<a href="<%=targetPath + "&page=" + (currentPage + 3) %>" >&rarr;</a>
<% } 
} %>

</div>