/**
 * check whether form has been complete 
 */

function checkUsername() {
	var username = document.getElementById("LUsername");
	var prompt = document.getElementById("loginPrompt");
	if (username.value == "") {		
		prompt.innerHTML = "请填写用户名";
		return false;
	} else {
		prompt.innerHTML = "&nbsp;";
		return true;
	}
}

function checkPassword() {
	var password = document.getElementById("LPassword");
	var prompt = document.getElementById("loginPrompt");
	if (password.value == "") {		
		prompt.innerHTML = "请填写密码";
		return false;
	} else {
		prompt.innerHTML = "&nbsp;";
		return true;
	}
}

function checkAll() {
	if (checkUsername() && checkPassword()) {
		verifyLogin();
	} else {
		return false;
	}
}

function verifyLogin() {
	getMyHTML("post", "login?username=" + document.getElementById("LUsername").value + "&password=" + document.getElementById("LPassword").value, "loginPrompt"); 
	//return true;
}

function verifyLoginRedirect(responseText) {
	if (responseText == "登录成功") {
		window.location.reload();   // refresh current page
	}
}

// 因为登录判断使用了ajax所以登录按钮不是submit，所以需要人为设置按下回车后登录
function keyDown() {
    if (event.keyCode == 13) {
    	verifyLogin();
    }
}

document.getElementById("LUsername").onblur = checkUsername;
document.getElementById("LUsername").onkeydown = keyDown;
document.getElementById("LPassword").onblur = checkPassword;
document.getElementById("LPassword").onkeydown = keyDown;
document.getElementById("LSubmit").onclick = verifyLogin;
//document.getElementById("loginForm").onsubmit = verifyLogin;
