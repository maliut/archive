package com.fudan.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fudan.biz.impl.*;
import com.fudan.entity.*;
import com.fudan.util.SearchUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class HouseAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private House ho = new House();
	private List<Comment> co = new ArrayList<Comment>();
	private List<Reply> re = new ArrayList<Reply>();
	private List<House> hos = new ArrayList<House>();
	private User u;
	private int houseId;
	private int page;//房屋列表分页
	private String address;//搜索功能的房屋地址
	private String dayPrice;//用于房屋价格检查
	private String area;
	String[] result1 = {"","","","",""};//用于返回发布房屋的检测结果2015/07/24
	private Map<String,Object> dataMap = new HashMap<String, Object>();;  //返回房屋是否成功发布
	private String verification;//验证码
	boolean toWarn =false;//用于回显发布房屋是否正确
	private SearchUtil su = new SearchUtil();//房屋搜索条件2015/07/21
	private List<House> hoList;//返回搜索结果2015/07/21
	HouseBizImpl hoBiz = new HouseBizImpl();
	CommentBizImpl coBiz = new CommentBizImpl();
	ReplyBizImpl reBiz = new ReplyBizImpl();
	UserBizImpl uBiz = new UserBizImpl();
	
	private int userId;  // 用户id，用于后台用户管理
	private int maxPage;  // 最大页数，后台用户管理
	private int id;
	//发布房源,2015/7/24
	public String build(){
		System.out.println("build");
		String num = (String)ActionContext.getContext().getSession().get("num");//验证码
		if(ho.getName().equals("")){//判断房屋名称
			result1[0]="房屋名称不能为空";
			System.out.println("房屋名称不能为空");
			toWarn=true;
		}
		if(dayPrice.equals("")){//判断房屋价格
			result1[1]="价格不能为空";
			System.out.println("价格不能为空");
			toWarn=true;
		}
		else if(true){//判断房屋价格
			try {
				ho.setDayPrice(Double.parseDouble(dayPrice));
			}
			catch(Exception e){
				result1[1]="价格应为小数或整数";
				System.out.println("价格应为小数或整数");
				toWarn=true;
			}
		}
		if(ho.getAddress().equals("")){//判断地址
			result1[2]="地址不能为空";
			System.out.println("地址不能为空");
			toWarn=true;
		}
		if(area.equals("")){//判断房屋面积
			result1[3]="房屋面积不能为空";
			toWarn=true;
		}
		else if(true){//判断房屋面积
			try {
				ho.setDayPrice(Double.parseDouble(area));
			}
			catch(Exception e){
				result1[3]="价格应为小数或整数";
				toWarn=true;
			}
		}
		if(!num.equals(verification)) {
			result1[4] = "验证码错误";
			toWarn=true;
		}	
		if(toWarn){//创建房屋有问题
			dataMap.put("success","not");
			dataMap.put("result0",result1[0]);
			dataMap.put("result1",result1[1]);
			dataMap.put("result2",result1[2]);
			dataMap.put("result3",result1[3]);
			dataMap.put("result4",result1[4]);
			return "error";
		}
		else {
			ho.setUserId(((User) (ActionContext.getContext().getSession().get("user"))).getId());//将user对象放入session中，2015/07/22);
			if(hoBiz.add(getHo())) {
				//添加房屋成功
				dataMap.put("success","yes");
				houseId=hoBiz.findLatestHouses(1).get(0).getId();
				dataMap.put("houseId",houseId);//储存房屋id
				return "success";
			}
			else {
				
				
				return "error";
			}
		}
	}
	
	//到显示房屋的详细界面
	public String toHouseDetail(){
		//需要评论，房子信息，回复，该房主的信息，以及房主的其他房屋
		ho=hoBiz.findHouse(houseId);
		System.out.println(houseId);
		co = coBiz.findCommentByHouse(houseId);
		
		if(co!=null){
			for (int i=0;i<co.size();i++){
				Reply temp = reBiz.findReplyByComment(co.get(i).getId());
				if(temp==null)
					re.add(null);
				else {
					re.add(temp);
				}
			}
		}
		if(ho!=null) {
			System.out.println(ho.getName());
			u = uBiz.findUser(ho.getUserId());
			hos = hoBiz.findHousesByUserId(ho.getUserId(),1);
			return "toHouseDetail";
		}
		else {
			System.out.println(ho);
			return "error";
		}
	}

	/**
	 * 删除房屋
	 * 2015/08/03
	 */
	public String delete(){
		return (hoBiz.delete(ho.getId())) ? "success" : "error";
	}
	
	/**
	 * 通过审核
	 * 2015/08/03
	 */
	public String acceptHouse() {
		House h = hoBiz.findHouse(id);
		return (hoBiz.updateState(h, House.POSTED)) ? "success" : "error";	
	}
	
	/**
	 * 拒绝审核
	 * 2015/08/03
	 */
	public String refuseHouse() {
		House h = hoBiz.findHouse(id);
		return (hoBiz.updateState(h, House.REJECTED)) ? "success" : "error";	
	}
	//载入修改房屋页面2015/7/20
	public String updateBegin(){
		setHo(hoBiz.findHouse(getHouseId()));
		return "success";
	}
	//修改房屋2015/7/20
	public String update(){
		if(hoBiz.update(getHo()))
			return "success";
		else {
			return "error";
		}
	}
	//根据id找房屋2015/7/20
	public String findHouse(){
		setHo(hoBiz.findHouse(getHouseId()));
		return "success";
	}
	
	//根据搜索条件搜索房屋2015/07/21
	public String SearchHouses(){
		setHoList(hoBiz.searchHouses(getSu()));
		return "success";
	}
	//待完成
	//搜索功能，根据地址搜索2015/7/20
	public String findHousesByAddress(){
		return null;
	}
	//搜索功能，根据日期搜索2015/7/20
	public String findHousesByDay(){
		return null;
	}
	
    /**
	 * 去房屋管理的页面
	 * 2015/07/31
	 */
	public String toHouseManage() {
		//System.out.println(page); 使用url的 ?page= 传值
		// 从session中读取user的id
		User u = (User) ActionContext.getContext().getSession().get("user");
		int userId = u.getId();
		// 处理page相关
		maxPage = hoBiz.maxPageOfHousesByUserId(userId);
		page = (page > maxPage || page < 1) ? 1 : page;  // 不合法的page，默认第一页
		// 设置主要参数
		hoList = hoBiz.findHousesByUserId(userId, page);
		return "success";
	}
	
    /**
	 * 去房屋审核的页面
	 * 2015/08/02
	 */
	public String toHouseAdmin() {
		//System.out.println(page); 使用url的 ?page= 传值
		User u = (User) ActionContext.getContext().getSession().get("user");
		// 判断权限
		if (u.getState() == 0) {  // 不是管理员
			return "error";
		}
		// 处理page相关
		maxPage = hoBiz.maxPageOfHouses();
		page = (page > maxPage || page < 1) ? 1 : page;  // 不合法的page，默认第一页
		// 设置主要参数
		hoList = hoBiz.findHouses(page);
		return "success";
	}
	
//	//上传图片2015/07/24
//	public String upload(){
//		save(getMf1(),1);
//		save(getMf2(),2);
//		save(getMf3(),3);
//		return "success";
//		
//	}
//	void save(File fl,int i){
//		//将临时文件对象f拷贝到目标位置
//				BufferedInputStream bis = null;
//				BufferedOutputStream bos = null;
//				try {
//						bis = new BufferedInputStream(new FileInputStream(fl));
//						//String destPath = ServletActionContext.getServletContext().getRealPath("\\upload"); 有问题，使用绝对路径
//						String destFile = "D:\\Users\\duocai\\workspace\\ShortRent\\WebContent\\upload\\"+"null"+i;
//						System.out.println("---------"+destFile);
//						bos = new BufferedOutputStream(new FileOutputStream(destFile));
//						byte[] bts = new byte[1024];
//						int len = -1;
//						while((len = bis.read(bts)) != -1){
//							//将bts字节信息写入目标文件
//							bos.write(bts,0,len);
//						}	
//				} catch (Exception e) {
//					e.printStackTrace();
//				}finally{
//					try {
//						bis.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					try {
//						bos.close();
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//	}
	//getter and setter methods
	public House getHo() {
		return ho;
	}
	public void setHo(House ho) {
		this.ho = ho;
	}
	public int getHouseId() {
		return houseId;
	}
	public void setHouseId(int houseId) {
		this.houseId = houseId;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public SearchUtil getSu() {
		return su;
	}
	public void setSu(SearchUtil su) {
		this.su = su;
	}
	public List<House> getHoList() {
		return hoList;
	}
	public void setHoList(List<House> hoList) {
		this.hoList = hoList;
	}
	public String getDayPrice() {
		return dayPrice;
	}
	public void setDayPrice(String dayPrice) {
		this.dayPrice = dayPrice;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public Map<String,Object> getDataMap() {
		return dataMap;
	}
	public void setDataMap(Map<String,Object> dataMap) {
		this.dataMap = dataMap;
	}
	public String getVerification() {
		return verification;
	}
	public void setVerification(String verification) {
		this.verification = verification;
	}
	public List<Comment> getCo() {
		return co;
	}
	
	public void setCo(List<Comment> co) {
		this.co = co;
	}
	
	public List<Reply> getRe() {
		return re;
	}
	
	public void setRe(List<Reply> re) {
		this.re = re;
	}
	
	public List<House> getHos() {
		return hos;
	}
	
	public void setHos(List<House> hos) {
		this.hos = hos;
	}

	public User getU() {
		return u;
	}

	public void setU(User u) {
		this.u = u;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getMaxPage() {
		return maxPage;
	}

	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
