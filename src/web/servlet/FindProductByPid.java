package web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.Product;
import service.CategoryService;
import service.ProductService;

public class FindProductByPid extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从前台获取得到的商品的id信息
		String pid = request.getParameter("pid");
		String cid = request.getParameter("cid");
		String currentPageStr = request.getParameter("currentPage");
		
		int currentPage = Integer.parseInt(currentPageStr);
		//利用pid去查找商品
		ProductService service = new ProductService();
		Product product = service.findProductByPid(pid);
		
		/*
		 * 历史记录功能实现 
		 */
		//客户端存储了记录pid的cookie
		//得到客户端的cookie
		String pids=pid;	//第一次访问的话没有数据
		Cookie[] cookies = request.getCookies();
		if(cookies!=null) {
			for (Cookie cookie : cookies) {
				if("pids".equals(cookie.getName())) {
					pids = cookie.getValue();
					//注意格式1-3-2
					//考虑下8-1-3  再次访问3  3-8-1   先删除访问的，再在头插入访问pid
					String[] split = pids.split("-");
					List<String> asList = Arrays.asList(split);
					LinkedList<String> list = new LinkedList<String>(asList);
					//如果是第二次访问
					if(list.contains(pid)) {
						list.remove(pid);
						list.addFirst(pid);
					}else {
						list.addFirst(pid);
					}
					
					//添加完成后，还需要将集合转为字符串，带-
					StringBuffer sb = new StringBuffer();
					for (int i = 0; i < list.size()&&i<7; i++) {
						//如果大于7个则会超过预留地方
						/*if(i==list.size()-1) {
							sb.append(list.get(i));
						}else {
							sb.append(list.get(i)+"-");
						}*/
						sb.append(list.get(i));
						sb.append("-");
						
					}
					//直接截取就可
					pids = sb.substring(0, sb.length()-1);//记住sb的特性，包头不包尾
					
					
				}
			}
		}
		//把处理好的历史记录放回cookie
		
		Cookie cookie_pids = new Cookie("pids", pids);
		response.addCookie(cookie_pids);
		//设置持久化
		
		
		
		//得到商品后放入到request中，转发
		request.setAttribute("product", product);
//		request.setAttribute("category", category);
		request.setAttribute("cid", cid);
		request.setAttribute("currentPage", currentPage);
		
		request.getRequestDispatcher("/product_info.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}