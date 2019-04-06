package web.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CategoryService;

public class DeleteCategoryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//1.接收到cid
		String cid = request.getParameter("cid");
		//2.利用cid删除类别
		CategoryService service = new CategoryService();
		service.deleteCategory(cid);
		
//		3.重回
		response.sendRedirect(request.getContextPath()+"/adminGetAllProduct");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}