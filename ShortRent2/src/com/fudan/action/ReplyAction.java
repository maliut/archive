package com.fudan.action;

import com.fudan.biz.impl.ReplyBizImpl;
import com.fudan.entity.Reply;

public class ReplyAction {
	private Reply re = new Reply();
	private int commentId;
	ReplyBizImpl reBiz = new ReplyBizImpl();
	
	//增加评论2015/7/20
	public String add(){
		if(reBiz.add(getRe()))
			return "success";
		else {
			return "error";
		}
	}
	//删除评论2015/7/20
	public String delete(){
		if(reBiz.delete(getRe()))
			return "success";
		else {
			return "error";
		}
	}
	//得到房主对用户的回复2015/7/20
	public String findReply(){
		setRe(reBiz.findReplyByComment(getCommentId()));
		return "success";
	}
	
	//getter and setter methods
	public Reply getRe() {
		return re;
	}
	public void setRe(Reply re) {
		this.re = re;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	
}
