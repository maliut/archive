package com.fudan.action;

import java.util.List;

import com.fudan.biz.impl.HouseBizImpl;
import com.fudan.entity.*;
import com.fudan.util.SearchUtil;

public class DefaultAction {
	private List<House> list;
	private List<String> pics;
	HouseBizImpl hoBiz = new HouseBizImpl();
	
	//用于获取信息后，跳转到主页2015/07/20
	public String  execute() {
		System.out.println("ok");
		setList(hoBiz.findLatestHouses(6));
		setPics(hoBiz.getPictures(5));
		System.out.println("ok");
		return "success";
	}
	
	//getter and setter methods2015/07/20
	public List<House> getList() {
		return list;
	}
	public void setList(List<House> list) {
		this.list = list;
	}
	public List<String> getPics() {
		return pics;
	}
	public void setPics(List<String> pics) {
		this.pics = pics;
	}
}
