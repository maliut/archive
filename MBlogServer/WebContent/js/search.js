/**
 * search
 */
function showOption() {
	if (document.getElementById("switch").innerHTML == "高级") {
		document.getElementById("search-option").style.display = "block";
		document.getElementById("switch").innerHTML = "基本";
	} else {
		document.getElementById("search-option").style.display = "none";
		document.getElementById("switch").innerHTML = "高级";
	}
}

function verifyKeyword() {
	var keyword = document.getElementById("keyword").value.replace(/\s+/,"");
	return (keyword == "") ? false : true; 
}

document.getElementById("searchForm").onsubmit = verifyKeyword;