package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;

public class CheckUsername extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		//1.获得用户名
		String username = request.getParameter("username");
		//2.调用service查询用户名
		UserService service = new UserService();
		
		boolean isExist = service.checkUsername(username);
		//存在是true，不存在是false
		
		String json = "{\"isExist\":"+isExist+"}";
		
		//写回
		response.getWriter().write(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
