package com.fudan.action;

import java.util.List;

import com.fudan.biz.IOrderBiz;
import com.fudan.biz.impl.OrderBizImpl;
import com.fudan.entity.Comment;
import com.fudan.entity.Order;
import com.fudan.entity.User;
import com.opensymphony.xwork2.ActionContext;

public class OrderAction {
	private Order or = new Order();
	private int userId;
	private int houseId;
	IOrderBiz orBiz = new OrderBizImpl();
	
	private List<Order> orList;
	private int id;  // order id
	private Comment comment = new Comment();
	
	//增加订单2015/07/20
	public String add(){
		if(orBiz.add(getOr()))
			return "success";
		else {
			return "error";
		}
	}
	
	/**
	 * 去我是房东的订单页面
	 * 2015/08/02
	 */
	public String toOrderOwner() {
		// 从session中读取user的id
		User u = (User) ActionContext.getContext().getSession().get("user");
		int userId = u.getId();
		// 设置主要参数
		orList = orBiz.findOrderByOwner(userId);
		return "success";
	}
	
	/**
	 * 去我是房客的订单页面
	 * 2015/08/02
	 */
	public String toOrderUser() {
		// 从session中读取user的id
		User u = (User) ActionContext.getContext().getSession().get("user");
		int userId = u.getId();
		// 设置主要参数
		orList = orBiz.findOrderByUser(userId);
		return "success";
	}
	
	/**
	 * 拒绝订单
	 * 2015/08/03
	 */
	public String refuseOrder() {
		Order o = orBiz.findOrder(id);
		return (orBiz.update(o, Order.REJECTED)) ? "success" : "error";		
	}
	
	/**
	 * 接受订单
	 * 2015/08/03
	 */
	public String acceptOrder() {
		Order o = orBiz.findOrder(id);
		return (orBiz.update(o, Order.ACCEPTED)) ? "success" : "error";		
	}
	
	/**
	 * 取消订单
	 * 2015/08/03
	 */
	public String cancelOrder() {
		Order o = orBiz.findOrder(id);
		return (orBiz.update(o, Order.CANCELED)) ? "success" : "error";		
	}
	
	/**
	 * 完成订单
	 * 2015/08/03
	 */
	public String finishOrder() {
		Order o = orBiz.findOrder(id);
		return (orBiz.update(o, Order.FINISHED)) ? "success" : "error";		
	}
	
	//getter and setter methods
	public Order getOr() {
		return or;
	}
	public void setOr(Order or) {
		this.or = or;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}

	public List<Order> getOrList() {
		return orList;
	}

	public void setOrList(List<Order> orList) {
		this.orList = orList;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Comment getComment() {
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}
}
