package web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import domain.Category;
import redis.clients.jedis.Jedis;
import service.CategoryService;
import utils.JedisUtils;

public class GetAllCategory extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryService cservice = new CategoryService();
		//现在不从数据库获取类，从redis中获取
		//查询顺序，先从缓存中查询数据，缓存中如果没有再从数据库中获取数据
		//1.先获取redis对象
		
		Jedis jedis = JedisUtils.getJedis();//从池中获取
		String categoryListJson = jedis.get("categoryListJson");//在redis中本就是json格式，键值对
		//判断redis中是否有类别，没有再转数据库
		if(categoryListJson==null) {
			System.out.println("缓存为空");
			List<Category> categoryList = cservice.getCategorys();
			
			Gson gson = new Gson();
			categoryListJson = gson.toJson(categoryList);
			jedis.set("categoryListJson", categoryListJson);
		}
		//将得到的list回给前台，记住要转为json格式
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(categoryListJson);
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}