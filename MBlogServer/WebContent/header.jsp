<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<% String fromPath = request.getRequestURL().toString().split(".jsp")[0] + "?" + request.getQueryString(); %>
<!-- "< %=basePath% >" has been defined in main jsp file, just ignore the error -->
<!-- decide the content of inner header by cookies -->
<%@ page import="model.UserModel" %>
<%
UserModel user = UserModel.getUserFromCookie(request.getCookies());
String username = (user == null) ? "" : user.getUsername();
int userID = (user == null) ? 0 : user.getUID();
boolean isLogined = (user != null);
%>
<link href="css/header-popmenu.css" rel="stylesheet" type="text/css" />

<!-- header block -->
<div id="header">　　
	<a href="index.jsp">首页</a>　
	<input type="text" id="searchBar" placeholder="请输入搜索内容…" size="30" />
	<a href="javascript:search()">搜索</a>
	<span id="innerHeader"></span>
</div>
<div id="popback"></div>
<div id="popLogin" class="popmenu">
	<a href="javascript:close()" class="closePop">x</a>
	<h3>登录</h3>
	<form id="loginForm" method="post" >
		<p class="prompt" id="loginPrompt" >&nbsp;</p>
		<p><label>用户名：
			<input type="text" id="LUsername" name="username" size="20" />
		</label></p>
		<p><label>密　码：
			<input type="password" id="LPassword" name="password" size="20" />
		</label></p>
		<p>
			<input type="button" id="LSubmit" value="登录" />
			<a href="javascript:void(0)" onclick="openRegister()" class="hrefButton" >注册</a>
		</p>		
	</form>
	
</div>
<div id="popRegister" class="popmenu">
	<a href="javascript:close()" class="closePop">x</a>
	<h3>注册</h3>
	<form id="registerForm" action="register" method="post" >
		<input type="hidden" name="fromPath" value=<%=fromPath%> />
		<p class="prompt" id="registerPrompt" >&nbsp;</p>
		<p><label>用户名：
			<input type="text" id="username" name="username" size="20" maxlength="15" />
		</label></p>
		<p><label>密　码：
			<input type="password" id="password" name="password" size="20" maxlength="20" />
		</label></p>
		<p><label>确　认：
			<input type="password" id="rePassword" name="rePassword" size="20" maxlength="20" />
		</label></p>
		<p><label>邮　箱：
			<input type="text" id="email" name="email" size="20" maxlength="30" />
		</label></p>
		<p>
			<input type="submit" value="注册" />
			<a href="javascript:void(0)" onclick="openLogin()" class="hrefButton" >登录</a>
		</p>		
	</form>
</div>
<script type="text/javascript" src="js/header-popmenu.js"></script>
<script type="text/javascript" src="js/login.js"></script>
<script type="text/javascript" src="js/register.js"></script>
<script>showHeader(<%=isLogined%>,['<%=username%>','<%=userID%>']);</script>
