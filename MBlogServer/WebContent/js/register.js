/**
 * check whether form has been complete 
 */

function checkUsername() {
	var username = document.getElementById("username");
	var prompt = document.getElementById("registerPrompt");
	if (username.value == "") {		
		prompt.innerHTML = "请输入用户名";
		return false;
	} else if (username.value.length < 3) {
		prompt.innerHTML = '用户名不能少于3字符';
		return false;
	} else {
		prompt.innerHTML = "&nbsp;";
		verifyUsedUsername();
		return true;
	}
}

function checkPassword() {
	var password = document.getElementById("password");
	var prompt = document.getElementById("registerPrompt");
	if (password.value == "") {		
		prompt.innerHTML = "请输入密码";
		return false;
	} else if (password.value.search(/^\w+$/) == -1) {
		prompt.innerHTML = "密码仅能包含字母、数字、下划线";
		return false;
	} else if (password.value.search(/^\d+$/) != -1) {
		prompt.innerHTML = "密码不能为纯数字";
		return false;
	} else if (password.value.length < 6) {
		prompt.innerHTML = '密码不能少于6字符';
		return false;
	} else {
		prompt.innerHTML = "&nbsp;";
		return true;
	}
}

function checkRePassword() {
	var rePassword = document.getElementById("rePassword");
	var password = document.getElementById("password");
	var prompt = document.getElementById("registerPrompt");
	if (rePassword.value == "") {		
		prompt.innerHTML = "请再次输入密码";
		return false;
	} else if (password.value != rePassword.value) {
		prompt.innerHTML = "两次输入的密码不一致";
		return false;
	} else {
		prompt.innerHTML = "&nbsp;";
		return true;
	}
}

function checkEmail() {
	var email = document.getElementById("email");
	var prompt = document.getElementById("registerPrompt");
	if (email.value == "") {		
		prompt.innerHTML = "请输入邮箱";
		return false;
	} else if (email.value.search(/^(\w)+(\.\w+)*@(\w)+((\.\w+)+)$/) == -1) {
		prompt.innerHTML = "请确认邮箱格式";
		return false;
	} else {
		prompt.innerHTML = "&nbsp;";
		return true;
	}
}

function checkAll() {
	if (checkUsername() && checkPassword() && checkRePassword() && checkEmail()) {
		return true;
	} else {
		return false;
	}
}

function verifyUsedUsername()    {   
    getMyHTML("get", "register?username=" + document.getElementById("username").value, "registerPrompt"); 
    return true;
}   

document.getElementById("username").onblur = checkUsername;
document.getElementById("password").onblur = checkPassword;
document.getElementById("rePassword").onblur = checkRePassword;
document.getElementById("email").onblur = checkEmail;
document.getElementById("registerForm").onsubmit = checkAll;