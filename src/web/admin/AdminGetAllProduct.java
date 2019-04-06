package web.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;
import service.ProductService;

public class AdminGetAllProduct extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//向服务器获取所有的product
		ProductService service = new ProductService();
		List<Product> productList = service.getAllProducts();
		
		//得到数据后回传给product
		request.setAttribute("productList", productList);
		
		request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}