package web.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.OrderService;
import vo.Order;

public class AdminGetAllOrder extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获得所有的分类
		OrderService service = new OrderService();
		List<Order> orderList = service.findAllOrders();
		
		
		request.setAttribute("orderList", orderList);
		
		request.getRequestDispatcher("/admin/order/list.jsp").forward(request, response);
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}