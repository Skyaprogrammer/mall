package web.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CategoryService;

public class UpdateCategory extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		String cid = request.getParameter("cid");
		String cname = request.getParameter("cname");
		
		//更新操作
		CategoryService service = new CategoryService();
		service.updateCategory(cid,cname);
		
		
		response.sendRedirect(request.getContextPath()+"/adminGetAllCategory");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
