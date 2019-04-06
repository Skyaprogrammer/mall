package web.admin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.CategoryService;
import utils.CommonUtils;

public class AdminCategoryServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//我们接受到前台的数据
		request.setCharacterEncoding("utf-8");
		String cname = request.getParameter("cname");
		
		//2.生成cid
		String cid = CommonUtils.getUUID();
		
		//3.调用service插入cid和cname
		CategoryService service = new CategoryService();
		service.addCategory(cid,cname);
		//默认我添加成功，不判定
		
		//添加成功后转回category
		response.sendRedirect(request.getContextPath()+"/adminGetAllCategory");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}