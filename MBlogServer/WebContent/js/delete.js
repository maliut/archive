/**
 * delete
 * 删除文章时还要删除对应的评论！！！！！要改！！！！！
 */

// delete post in post page
function deletePost(postID, userID) {
	if (confirm("确认删除吗？删除后不可恢复")) {
		getMyHTML("get", "delete?type=post&id=" + postID);
		alert("删除成功！");
		window.location.href = "home?u=" + userID;
	}
}

function deleteOther(type, id) {
	if (confirm("确认删除吗？删除后不可恢复")) {
		getMyHTML("get", "delete?type=" + type + "&id=" + id);
		//document.getElementById(id).style.display = "none";  // 曾经的简单粗暴隐藏法
		fadeOut(id);
	}
}

function fadeOut(id) {
	document.getElementById(id).style.overflow = "hidden";  // 设定溢出隐藏
	var v = document.getElementById(id).offsetHeight / 20;  // 设定速度，速度由该元素整体高度决定（padding+height）
	setInterval("change("+id+","+v+")", 10);
}

function change(id,v) {
	// 分别得到三个值，
	// 1.对于写在外部css里的属性不能用.style.paddingTop之类访问……坑爹啊！！
	// 2.得到的值是带单位的字符串，要手动转数字……坑爹啊！！
	var i=parseInt(window.getComputedStyle(document.getElementById(id),null).paddingBottom); 
	var k=parseInt(window.getComputedStyle(document.getElementById(id),null).paddingTop); 
	var j=parseInt(window.getComputedStyle(document.getElementById(id),null).height);
	// 按照顺序减为0，制造从下到上消失的效果
	if (i > 0) {
		i = Math.max(0,i-v);  // 保证最后减到0，不然变负数了会自动回复原状
		document.getElementById(id).style.paddingBottom = i + "px";  // 也要带单位
	} else if (j > 0) {
		j = Math.max(0,j-v);
		document.getElementById(id).style.height = j + "px";
	} else if (k > 0) {
		k = Math.max(0,k-v);
		document.getElementById(id).style.paddingTop = k + "px";
	} else {
		document.getElementById(id).style.borderBottomWidth="0px";  // 把边框去了
		clearInterval();
	}
}
