
function openReply() {
	document.getElementById("popback").style.display = "block";
	document.getElementById("popReply").style.display = "block";
	document.body.style.overflow = "hidden";
}

function closeReply() {
	document.getElementById("popback").style.display = "none";
	document.getElementById("popReply").style.display = "none";
	document.body.style.overflow = "auto";
}
function reply(){
	
	$.ajax({
        cache: true,
        type: "POST",
        url:"ReplyAction!add.action",
        data:$('#replyForm').serialize(),// 你的formid
        async: false,
        error: function(request) {
           

        },
        success: function(data) {
if(data){
	 $("#ReplyErrorMessage").html("您的输入不合法，请修改");
}else{
	 history.go(0);
}
        }
    });


}