package web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;
import service.ProductService;

public class SearchProduct extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//利用pname找到商品，然后找到product
		request.setCharacterEncoding("utf-8");
		String pname = request.getParameter("pname");
		ProductService service = new ProductService();
		Product product = service.findProductByPname(pname);
		
		//找到product放入request域中，然后request的转发
		request.setAttribute("product", product);

		//这里需要改进，我们还要同步的处理历史记录等问题，所以我们还需要其他数据
		request.getRequestDispatcher("product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}