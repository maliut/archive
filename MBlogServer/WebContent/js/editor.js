/**
 * use in editor
 */
function deletePost_tag(postID, tagID) {
	// use ajax
	getMyHTML("get","new?postID=" + postID + "&tagID=" + tagID);
	document.getElementById("tip" + tagID).style.display = "none";
}