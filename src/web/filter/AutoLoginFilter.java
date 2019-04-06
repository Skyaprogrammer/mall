package web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import domain.User;
import service.UserService;


public class AutoLoginFilter implements Filter {


	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//获得cookie中的数据，实现自动登录
		//先将request转为HttpRequest
		HttpServletRequest req = (HttpServletRequest) request;
		
		//1.先看下这次会话有没有结束，如果session中还存在user我们就不用设置
		User user  = (User) req.getSession().getAttribute("user");
		if(user==null) {
			//2.获取cookie中的用户名和密码
			String cookie_username = null;
			String cookie_password = null;
			
			Cookie[] cookies = req.getCookies();
			if(cookies!=null) {
				for (Cookie cookie : cookies) {
					if("cookie_username".equals(cookie.getName())) {
						cookie_username = cookie.getValue();
//						System.out.println(cookie_username);
					}
					if("cookie_password".equals(cookie.getName())) {
						cookie_password = cookie.getValue();
//						System.out.println(cookie_password);
								
					}
				}
			}
			/*System.out.println(cookie_username);
			System.out.println(cookie_password);*/
			//如果username和password不为空，我们就校验下账号密码登录
			if(cookie_username!=null && cookie_password!=null) {
				UserService service = new UserService();
				//前面已经取了session如果空就赋值即可
				user = service.login(cookie_username, cookie_password);
				req.getSession().setAttribute("user", user);
			}
			
			//如果空直接放行，没有用户登录
		}
	
		
		//如果存在直接放行
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	
}
