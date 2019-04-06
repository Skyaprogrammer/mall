package vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import domain.User;

public class Order {

	private String oid;			//订单的订单号
	private Date orderTime;	//订单的下单时间
	private double total;		//订单的总金额
	private int state;			//该订单是否完成（支付状态）	1.已付款，2未付款
	private String address;		//订单的地址
	private String name;		//订单的姓名
	private String telephone;	//订单的手机号码
	private User user;			//订单的用户
	
	//商品列表，因为需要实时的修改，删除等，所以是Map
	//订单，不能实时修改，如果需要修改只能重新下单		所以需要list就可以
	
	private List<OrderItem> orderItems = new ArrayList<OrderItem>();
	
	
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrderTime() {
		return orderTime;
	}
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
}
