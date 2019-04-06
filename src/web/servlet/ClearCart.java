package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ClearCart extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * 执行清空购物车选项
		 */
		//就是把session中的cart销毁
		HttpSession session = request.getSession();
//		session.invalidate();  全清除，不太合适
		session.removeAttribute("cart");
		
		//转回购物车
		response.sendRedirect(request.getContextPath()+"/cart.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}