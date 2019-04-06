package web.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import service.CategoryService;

public class EditCategory extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//1.先利用
		String cid = request.getParameter("cid");
		
		CategoryService service = new CategoryService();
		Category category = service.findCategoryByCid(cid);
		
		//2.转发
		request.setAttribute("editCategory", category);
		
		request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}