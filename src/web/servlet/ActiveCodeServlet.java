package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

public class ActiveCodeServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//传入的时候带了一个activeCode
		String activeCode = request.getParameter("activeCode");
		//利用code去找用户，修改state为1
		UserService service = new UserService();
		
		service.active(activeCode);
		//默认都激活成功，激活成功直接登录
		response.sendRedirect(request.getContextPath()+"/login.jsp");
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}