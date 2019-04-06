package vo;

import domain.Product;

public class OrderItem {

	private String itmid;	//订单项的id
	private int count;		//订单项内商品的购买数量
	private double subTotal;//订单项的小计
//	private String pid;		订单项的商品的pid  我们用面向对象的思想，来Product
	private Product product;
//	private String oid;		//订单项对应的oid，order的id
	private Order order;
	
	public String getItmid() {
		return itmid;
	}
	public void setItmid(String itmid) {
		this.itmid = itmid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
	
	
}
