package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import domain.Product;
import domain.User;
import service.OrderService;
import vo.Order;
import vo.OrderItem;
import vo.PageBean;

public class GetOrderList extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.首先得校验用户是否登录，如果没登录要先登录
		//检验用户登录可以使用filter过滤器过滤
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		/*if(user == null) {
			response.sendRedirect(request.getContextPath()+"/login.jsp");
			return;
		}*/
		
		//2.用户登录
		//3.查询该用户的所有订单
		OrderService service  = new OrderService();
		
		//我们要实现分页操作
		String currentPageStr = request.getParameter("currentPage");
		if(currentPageStr==null) {
			currentPageStr = 1+"";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		int currentCount = 6;
		PageBean pageBean = service.findAllOrdersByUid(currentPage,currentCount,user.getUid());
		
		
		List<Order> orderList = pageBean.getList();
		
		//对订单集合进行遍历，用户下了几次单，订单集合中就有几个订单
		if(orderList!=null) {
			for (Order order : orderList) {
				//对所有的order进行补全
				//1.获取每次订单的订单号
				String oid = order.getOid();
				//2.查询该订单的所有订单项    里面的product利用多表查询 -----------封装的是订单项和商品信息
				List<Map<String, Object>> mapList = service.findAllOrderItemsByOid(oid);
				//键是我们查询的所需的字段名，object是对应的值
			
				//需要将mapList转为orderItem
				for (Map<String, Object> map : mapList) {
					//从map中取出count,subtotal，封装到orderItem，再取出其他的放入product
					//再讲product封装到orderItem
					//可以直接利用BeanUtil直接封装
					/*orderItem.setCount(Integer.parseInt(map.get("count").toString()));
					orderItem.setSubTotal((double) map.get("subtotal"));*/
					
					try {
						OrderItem orderItem = new OrderItem();
						Product product = new Product();
						BeanUtils.populate(orderItem, map);
						BeanUtils.populate(product, map);
						//再封装product
						//再OrderItem封装到Order内的OrderItemList
						orderItem.setProduct(product);
						order.getOrderItems().add(orderItem);
					} catch (IllegalAccessException |InvocationTargetException e) {
						
						e.printStackTrace();
					}
					
				}
				
			}//for
		}//if
		
		
		/*//order已经封装完毕
		//注意session中有一个order，不要同名
		request.setAttribute("orderList", orderList);*/
		
		request.setAttribute("pageBean", pageBean);
		
		//转发
		request.getRequestDispatcher("/order_list.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}