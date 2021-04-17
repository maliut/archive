package com.fudan.biz.impl;

import com.fudan.biz.IReplyBiz;
import com.fudan.dao.impl.ReplyDaoImpl;
import com.fudan.entity.Reply;

public class ReplyBizImpl implements IReplyBiz {
	ReplyDaoImpl dao = new ReplyDaoImpl();
	
	//房东回复房客2015/7/20
	@Override
	public boolean add(Reply r) {
		// TODO Auto-generated method stub
		return dao.add(r);
	}

	//删除回复2015/7/20
	@Override
	public boolean delete(Reply r) {
		// TODO Auto-generated method stub
		return dao.delete(r);
	}

	//查询回复2015/7/20
	@Override
	public Reply findReplyByComment(int commentId) {
		// TODO Auto-generated method stub
		return dao.findReplyByComment(commentId);
	}

}
