package web.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import domain.Category;
import service.CategoryService;

public class AdminGetAllCategory extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获得所有的category，放入session中，
		HttpSession session = request.getSession();
		CategoryService service = new CategoryService();
		List<Category> categoryList = service.getCategorys();
		
		session.setAttribute("categoryList", categoryList);
		response.sendRedirect(request.getContextPath()+"/admin/category/list.jsp");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}