package web.servlet;

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

//该servlet主要是访问后携带热门商品和最新商品到index.jsp
public class IndexServlet extends HttpServlet {
	
	//热门商品，数据库有表示is_hot    最新商品，看数据库中的date，日期排序，越上面的越新
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		ProductService service = new ProductService();
		//准备热门商品 List<Product>
		List<Product> hotProducts = service.findHotProduct();
		//准备最新商品 List<Product>
		List<Product> newProducts = service.findNewProduct();
	
		
		/*//还要把分类的数据带给前台
		CategoryService cservice = new CategoryService();
		//不能放request，要不然会跳其他页面，request丢失，就没有了类
		// 1. ajax
		// 2. filter
		List<Category> categoryList = cservice.getCategorys();*/
		
		//将得到的数据放到域中
		request.setAttribute("hotProducts", hotProducts);
		request.setAttribute("newProducts", newProducts);
		
		
		//转发
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}