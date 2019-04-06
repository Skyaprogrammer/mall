package web.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Category;
import domain.Product;
import service.CategoryService;
import service.ProductService;

public class EditProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//1.利用pid找到页面，然后跳转到edit.jsp
		String pid = request.getParameter("pid");
		//2.调用
		ProductService service = new ProductService();
		Product p = service.findProductByPid(pid);
		//获得所有类的分类
		CategoryService service1 = new CategoryService();
		
		List<Category> list = service1.getCategorys();
		
		request.setAttribute("categoryList",list);
		
		request.setAttribute("editproduct", p);
//		System.out.println(p.getCategory().getCid());
		request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}