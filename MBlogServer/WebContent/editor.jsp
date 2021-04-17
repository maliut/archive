<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"%>
<%@ page import="model.*" %>
<%@ page import="java.util.List" %>
<% String fromPath = request.getRequestURL().toString(); 
//注意此处的路径不经过header.jsp里的那种处理，因为后台只有管理员能进，所以可以从cookie直接获得用户信息，不用从servlet跳转 %>
<% UserModel user = UserModel.getUserFromCookie(request.getCookies()); 
if (user == null) {
	response.getWriter().print("<script type='text/javascript'>alert('非法操作！');window.location.href='index.jsp';</script>");
} %>
<% List<CategoryModel> categories = CategoryModel.getCategoriesFromUID(user.getUID()); 
//List<TagModel> tags = TagModel.getTagsFromUID(user.getUID());  预留
List<TagModel> tagsInPost = null;
PostModel post = null;
%>
<% 	String id = request.getParameter("id");
if (id != null) {  // 编辑模式
	int pid = Integer.parseInt(id);
	post = PostModel.getPostFromPID(pid);
	if (post.getUserID() == user.getUID()) {  // 验证是否为作者编辑
		tagsInPost = TagModel.getTagsFromPID(pid);
	} else {
		response.sendRedirect("editor.jsp");
	}
}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>编辑&nbsp;-&nbsp;<%=user.getBlogName() %></title>
<link href="css/admin.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="editor/themes/simple/simple.css" />
</head>
<body>
	<jsp:include page="header.jsp"/>
	<jsp:include page="admin-aside.jsp"/>
	<main class="admin-main">
		<h1>博文编辑</h1>
		<form id="editorForm" name="editorForm" action="new" method="post">
			<input type="hidden" name="type" value="post" />
			<input type="hidden" name="id" value="<%=id %>" />
			<label>标题：<input type="text" size="103" id="title" name="title" /></label><br /><br />
			<textarea id="editor_id" name="content" style="width:800px;height:300px;"></textarea><br />
			<label>分类：<select id="category" name="category">
			<% for (int i = 0; i < categories.size(); i++) { %>
				<option id="c<%=categories.get(i).getCategoryID() %>" value="<%=categories.get(i).getCategoryID() %>"><%=categories.get(i).getName() %></option>
			<% } %></select></label>
			<label>　　　　…或者新建分类：<input type="text" size="30" name="newCategory" placeholder="在此输入新分类名" /></label><a class="inputTip" title="只读取半角空格之前的字符作为新分类的名字。&#13;您也可以输入已有的分类名，则系统会将该博文归到这一类">(?)</a><br /><br />
			<label>标签：<input type="text" size="50" name="tags" placeholder="按半角逗号和空格分隔"/></label><a class="inputTip" title="系统会自动识别已有的标签和新建的标签和重复的标签">(?)</a><br /><br />
			<% if (id != null && tagsInPost.size() > 0) { // 编辑 %>
				<label>现有标签:　
				<% for (int i = 0; i < tagsInPost.size(); i++) { %>
					<span id="tip<%=tagsInPost.get(i).getTagID() %>"><%=tagsInPost.get(i).getName() %>&nbsp;<a href="javascript:void(0)" onclick="deletePost_tag(<%=id %>,<%=tagsInPost.get(i).getTagID() %>)">x</a></span>　
				<% } %>
				</label>
			<% } %>
			<p><input type="button" value="保存" id="submit1"/></p>
		</form>
	</main>
</body>
<script charset="utf-8" src="editor/kindeditor.js"></script>
<script charset="utf-8" src="editor/lang/zh_CN.js"></script>
<script charset="utf-8" src="js/editor.js"></script>
<script>
KindEditor.ready(function(K) {
	var options = {			
		themeType : 'simple',   //???
		uploadJson : 'editor/jsp/upload_json.jsp',
		fileManagerJson : 'editor/jsp/file_manager_json.jsp',
	};
	var editor = K.create('textarea[name="content"]', options);
	K('input[id=submit1]').click(function(e) {
		if (document.getElementById("title").value == "") {
			alert("请填写标题！");
		} else {
			editor.sync();
			document.forms['editorForm'].submit();
		}

		
	});
	<% if (id != null) { // 编辑模式 %>
		document.getElementById("title").value="<%=post.getTitle() %>";			
		editor.html('<%=post.getContent().replaceAll("'", "\\\\'").replaceAll("[\n\r]", "") %>');
		document.getElementById("c<%=post.getCategoryID() %>").selected="selected";
	<% } %>
});
</script>

<script>document.getElementById("seditor").className="selected";</script>
</html>