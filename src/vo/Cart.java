package vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {

	//一个购物车内有N个购物项
	
	//购物项包括商品信息，商品数量，小计金额
	private Map<String,CartItem> cartItems = new HashMap<String,CartItem>();
	//后期好处理，点击就可以删除
	
	//直接用pid访问map然后根据pid删除商品
	//在购物车中还有总金额功能
	private double total;

	public Map<String, CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(Map<String, CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
	
}
