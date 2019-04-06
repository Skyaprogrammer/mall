package web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Product;
import service.ProductService;
import vo.Cart;
import vo.CartItem;

public class AddProductToCart extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ProductService service = new ProductService();
		//1.先获取购物车中的商品信息
		String pid = request.getParameter("pid");
		Product product = service.findProductByPid(pid);
		
		//2.获取用户添加的数量
		String buyNumStr = request.getParameter("buyNum");
		int buyNum = Integer.parseInt(buyNumStr);
		
		//3.设置该商品项的总金额
		Double subTotal = buyNum*product.getShop_price();
		
		//4.封装成cartItem
		CartItem cartItem = new CartItem();
		cartItem.setProduct(product);
		cartItem.setBuyNums(buyNum);
		cartItem.setSubTotal(subTotal);
		
		HttpSession session = request.getSession();
		//5.获取购物车，购物车是在session中的，我们需要获取cart，再添加
		Cart cart = (Cart)session.getAttribute("cart");
		if(cart==null) {
			//生成购物车
			cart = new Cart();
		}
		
		//将购物项放入
		//为什么要在后台直接new好，这里可以直接获取，然后可以直接使用
		Map<String, CartItem> cartItems = cart.getCartItems();
		
		
		
		//这里要进行判断，如果map中存在了pid，直接改变数量和金额就可以，不存在直接添加 
		if(cartItems.containsKey(product.getPid())) {
			//改变数量和金额
			CartItem item = cartItems.get(product.getPid());
			int buyNums = item.getBuyNums();
			buyNums += buyNum;
			item.setBuyNums(buyNums);
			double SubTotal = item.getSubTotal();
			SubTotal += subTotal;
			item.setSubTotal(SubTotal);
			
			cartItems.put(product.getPid(), item);
		}else {
			cartItems.put(product.getPid(), cartItem);
		}
		
		double total = cart.getTotal();
		total = total + subTotal;
		cart.setTotal(total);
		
		//将车再放回session
		session.setAttribute("cart", cart);

		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}