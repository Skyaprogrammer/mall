package web.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import domain.Category;
import service.CategoryService;

public class FindAllCategory extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//1.找到所有的category
		CategoryService service = new CategoryService();
		List<Category> categorys = service.getCategorys();
		
		Gson gson = new Gson();
		String json = gson.toJson(categorys);
		
		response.setContentType("text/json;charset=utf-8");
		//或者response.setContentTye("text/html")  也可以
		response.getWriter().write(json);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}