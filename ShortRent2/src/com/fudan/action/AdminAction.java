 package com.fudan.action;

import java.util.List;

import com.fudan.biz.impl.*;
import com.fudan.entity.House;
import com.fudan.entity.User;

public class AdminAction {
	private User admin;
	private List<User> userList;
	private User u = new User();
	private List<House> houseList;
	private int page;
	UserBizImpl userBiz = new UserBizImpl();
	HouseBizImpl houseBiz = new HouseBizImpl();
	
	//得到用户列表2015/07/20
	public String userList(){
		setUserList(userBiz.findUsers(getPage()));
		return "success";
	}
	//房屋审核列表2015/07/20
	public String houseList(){
		setHouseList(houseBiz.findHouses(getPage()));
		return "success";
	}
	//删除用户
	public String delete(){
		if(userBiz.delete(u))
			return "success";
		else {
			return "error";
		}
	}
	
	//getter and setter method2015/07/17
	public User getAdmin() {
		return admin;
	}
	public void setAdmin(User admin) {
		this.admin = admin;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public List<House> getHouseList() {
		return houseList;
	}
	public void setHouseList(List<House> houseList) {
		this.houseList = houseList;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public User getU() {
		return u;
	}
	public void setU(User u) {
		this.u = u;
	}
	
}
