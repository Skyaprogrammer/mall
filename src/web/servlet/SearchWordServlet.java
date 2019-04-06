package web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import service.ProductService;

public class SearchWordServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("utf-8");
		String word = request.getParameter("word");
		
		//利用输入的关键字查询商品
		ProductService service = new ProductService();
		List<Object> list = null;
		
		list = service.findProductByWord(word);
		
		
		
		Gson gson = new Gson(); 
		String products = gson.toJson(list);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(products);
	
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}