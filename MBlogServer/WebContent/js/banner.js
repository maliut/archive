/**
 * show banner
 */
function showBanner(url) {
	document.getElementById('blog-header').style.background="url(" + url + ")";
	document.getElementById('blog-header').style["background-size"] = "100% auto";
}

function setBannerTextColor(colorIndex) {
	if (colorIndex == 0) {
		document.getElementById('blog-name-a').style.color = "black";
		document.getElementById('blog-description').style.color = "black";
	} else {
		document.getElementById('blog-name-a').style.color = "white";
		document.getElementById('blog-description').style.color = "white";
	}
}