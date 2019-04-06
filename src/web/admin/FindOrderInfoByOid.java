package web.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.OrderService;

public class FindOrderInfoByOid extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
		//1.先查找oid
		String oid =request.getParameter("oid");
		
		OrderService service = new OrderService();
		List<Map<String, Object>> FindOrderInfoByOid = service.findOrderInfoByOid(oid);
		
		Gson gson = new Gson();
		String json = gson.toJson(FindOrderInfoByOid);
//		System.out.println(json);
		/*
		 * [
		 * {"pimage":"products/1/c_0031.jpg","shop_price":2299.0,"pname":"宏碁（acer）ATC705-N50 台式电脑","subtotal":2299.0,"count":1},
		 * {"pimage":"products/1/c_0034.jpg","shop_price":4499.0,"pname":"联想（Lenovo）小新V3000经典版","subtotal":4499.0,"count":1}
		 * ]
		 */
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write(json);
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
