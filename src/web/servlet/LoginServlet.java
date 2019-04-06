package web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.User;
import service.UserService;


public class LoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		UserService service = new UserService();
		//验证码校验
		
		String code = request.getParameter("checkImg");
//		System.out.println(code);
		//获取servlet生成的验证码
		String checkImg = (String)request.getSession().getAttribute("checkcode_session");
//		System.out.println(checkImg);
		//这里加一步自动登录实现
		
		if(checkImg.equals(code)) {

			//1.获取用户名和密码
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			//2.调用方法去数据库进行查询
//			System.out.println(username+":"+password);
			User user = service.login(username, password);
			
			
			if(user!=null) {
				//登录成功
				//登录成功我们判定一下他是否选择了自动登录
				String autoLogin = request.getParameter("autoLogin");
				if("autoLogin".equals(autoLogin)){
					//要自动登录
					//创建存储用户名的cookie
					Cookie cookie_username = new Cookie("cookie_username",user.getUsername());
					cookie_username.setMaxAge(10*60);
					//创建存储密码的cookie
					Cookie cookie_password = new Cookie("cookie_password",user.getPassword());
					cookie_password.setMaxAge(10*60);

					response.addCookie(cookie_username);
					response.addCookie(cookie_password);

				}

				
				request.getSession().setAttribute("user", user);
				response.sendRedirect(request.getContextPath());//index首页
			}else {
				//登录失败
				//转发回首页并携带错误信息
				request.setAttribute("loginInfo", "用户名或密码错误");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		}else {
			//登录失败,验证码输入错误
			//转发回首页并携带错误信息
			request.setAttribute("loginInfo", "验证码错误");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return;//return终结代码
		}
		
		
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}