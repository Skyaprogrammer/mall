package web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import vo.Cart;
import vo.CartItem;

public class DelProFromCart extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//1.接收传入的pid
		String pid = request.getParameter("pid");
		
		//2.得到session中的cart对象
		HttpSession session = request.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		
		if(cart!=null) {
			//3.利用pid去找cart中的cartItems删除
			Map<String, CartItem> cartItems = cart.getCartItems();
			
			double total = cart.getTotal();
			double subTotal = 0.0;
			if(cartItems.containsKey(pid)) {
				//获取该参数的总金额
				CartItem cartItem = cartItems.get(pid);
				subTotal = cartItem.getSubTotal();
				cartItems.remove(pid);
			}
			//移除后还需要修改total
			total -=subTotal;
			
			//放回
			cart.setTotal(total);
		}
		session.setAttribute("cart", cart);
		
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}