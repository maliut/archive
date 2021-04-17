/**
 * use for user to like and unlike, using ajax
 */
function changeLike(postID, userID) {
	getMyHTML("post", "post?postID=" + postID + "&userID=" + userID);
	if (document.getElementById("likeText").innerHTML.replace(/\s/g, "") == "赞") {
		document.getElementById("likeText").innerHTML = "取消赞";
		document.getElementById("likeNum").innerHTML = Number(document.getElementById("likeNum").innerHTML) + 1
	} else {
		document.getElementById("likeText").innerHTML = "赞";
		document.getElementById("likeNum").innerHTML = Number(document.getElementById("likeNum").innerHTML) - 1
	}
}