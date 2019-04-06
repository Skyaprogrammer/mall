package web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.User;
import service.OrderService;
import service.ProductService;
import utils.CommonUtils;
import vo.Cart;
import vo.CartItem;
import vo.Order;
import vo.OrderItem;

public class SubmitOrder extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//判断用户是否登录
		//使用过滤器过滤
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		/*if(user==null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			//后面操作不执行
			return;
		}*/
		
		//只有用户登录好后才进行下一步操作
		//目的：封装好一个order对象给service层
		Order order = new Order();
		//1.当前订单的id
		order.setOid(CommonUtils.getUUID());
		//2.下单时间
		order.setOrderTime(new Date());
		//3.订单的总金额,就是购物车的总金额
		Cart cart = (Cart)session.getAttribute("cart");
		/*if(cart!=null) {
			double total = cart.getTotal();
		}*/
		//暂时不进行业务判断
		double total = cart.getTotal();
		order.setTotal(total);
		//4.订单支付状态，默认为0未付款
		order.setState(0);
		//5.收货地址，默认为空
		order.setAddress(null);
		//6.收货人，默认为空
		order.setName(null);
		//7.手机号码
		order.setTelephone(null);
		//8.该订单属于哪个用户
		order.setUser(user);
		
		//9.封装订单的订单项，类比于购物车的购物项
		List<OrderItem> orderItems = order.getOrderItems();
		//利用购物车的一一对应
		Map<String, CartItem> cartItems = cart.getCartItems();
		//这里不要pid的String，键，我们只要值的一一对应
		for (Map.Entry<String,CartItem> entry : cartItems.entrySet()) {
			OrderItem orderItem = new OrderItem();
			//1.设置订单项id
			orderItem.setItmid(CommonUtils.getUUID());
			//2.设置订单项内购买的数量
			orderItem.setCount(entry.getValue().getBuyNums());
			//3.订单项小计
			orderItem.setSubTotal(entry.getValue().getSubTotal());
			//4.订单项中的商品
			orderItem.setProduct(entry.getValue().getProduct());
			//5.订单项属于哪个订单
			orderItem.setOrder(order);
			
			orderItems.add(orderItem);
		}
		
		
		//封装好了order对象 
		//传递数据到service
		OrderService service = new OrderService();
		service.submitOrder(order);
		
		//service完成后，将order放到session中
		session.setAttribute("order", order);
		
		response.sendRedirect(request.getContextPath()+"/order_info.jsp");
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}