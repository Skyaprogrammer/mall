package web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.ProductService;
import vo.PageBean;
import domain.Product;

public class ProductListByCid extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//利用前台传入的cid到后台去取的数据
		//然后实现分页
		String cid = request.getParameter("cid");
		String currentPageStr = request.getParameter("currentPage");
		//最开始点击分类时，没有页数，所以会造成空指针异常，
		if(currentPageStr==null) {
			currentPageStr = 1+"";
		}
		int currentPage = Integer.parseInt(currentPageStr);
		
		int currentCount = 12;
		ProductService service = new ProductService();
		PageBean pageBean = service.findProductListByCid(currentPage,currentCount,cid);
		
		//我们实现分页需要封装多个数据
		//有总页数，总个数，一页显示多少个
		
		/*
		 * 历史记录的实现 
		 */
		List<Product> historyProductList = new ArrayList<Product>();
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					String pids = cookie.getValue();
					String[] split = pids.split("-");
					for (String pid : split) {
						Product product = service.findProductByPid(pid);
						historyProductList.add(product);
					}
				}
			}
		}
		
		
		//放在域中，转发到页面
		request.setAttribute("pageBean", pageBean);
		request.setAttribute("historyProductList", historyProductList);
		request.setAttribute("cid", cid);
		
		
		request.getRequestDispatcher("/product_list.jsp").forward(request, response);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
} 