/**
 * use in admin
 */
function deleteOne(type, id) {
	if (confirm("确认删除吗？删除后不可恢复")) {
		getMyHTML("get", "delete?type=" + type + "&id=" + id);
		document.getElementById(id).style.display = "none";
		//window.location.href = "admin-content.jsp?type=" + type;
	}
}

function addOne(type, uid, name) {
	getMyHTML("get", "admin?deed=add&type=" + type + "&uid=" + uid + "&name=" + name);
	alert("增加成功！");
	window.location.reload();
}

function saveChange(type, id, name) {
	getMyHTML("get", "admin?deed=rename&type=" + type + "&id=" + id + "&name=" + name);
	alert("修改成功！");
}