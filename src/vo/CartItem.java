package vo;

import domain.Product;

public class CartItem {

	private Product product;
	private int buyNums;
	private double subTotal;
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getBuyNums() {
		return buyNums;
	}
	public void setBuyNums(int buyNums) {
		this.buyNums = buyNums;
	}
	public double getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	
	
}
