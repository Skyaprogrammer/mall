package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import domain.User;
import service.UserService;
import utils.CommonUtils;
import utils.MailUtils;

public class RegisterServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		//将前台的输入放入map集合中
		Map<String, String[]> map = request.getParameterMap();
		//进行封装
		User user = new User();
		try {
			//这里的Date数据不好处理，会报错
			//自己指定一个映射器，将String转为Date
			//其他数据都不会进来，只有BeanUtils映射时发现所匹配的数据是date类型的时候，才会启动注册的转换器
			ConvertUtils.register(new Converter() {
				@Override
				//class是转到哪个类   value是要强转的字符串
				public Object convert(Class clazz, Object value) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date date = null;
					try {
						date = sdf.parse(value.toString());
					} catch (ParseException e) {
						
						e.printStackTrace();
					}
					return date;
				}
			}, Date.class);
			
			
			//映射
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException | InvocationTargetException e) {
			
			e.printStackTrace();
		} 
		
		//前台得到的数据全部得到
		//为了得到一个完整的对象，将其他没有的数据进行对应的封装
		//uid UUID telephone  state code
		
		user.setUid(UUID.randomUUID().toString());//UUID的方法太长，我们进行封装
		
		user.setTelephone(null);
		user.setState(0);//未激活，0
		String activeCode = CommonUtils.getUUID();
		user.setCode(activeCode);		//激活码只要不重复就可以
		
		
		//将user传给service层，进行注册工作
		UserService service = new UserService();
		boolean isRegisterSuccess = service.register(user);
		
		//利用后台得到的数据，判断注册是否成功
		if(isRegisterSuccess) {
			//成功，往注册成功页面跳，然后发邮件激活
			//注册成功会发送一封邮件给你去激活
			//把注册码给前台进行校验知道是哪位用户进行校验
			String emailMsg = "恭喜您注册成功,请点击下面的链接进行激活账户<a href='http://localhost:8080/Shop/active?activeCode="+activeCode+"'>"
					+"http://localhost:8080/Shop/active?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(),emailMsg );
			} catch (AddressException e) {
				
				e.printStackTrace();
			} catch (MessagingException e) {
				
				e.printStackTrace();
			}
			
			
			response.sendRedirect(request.getContextPath()+"/registerSuccess.jsp");
		}else {
			//不成功
			response.sendRedirect(request.getContextPath()+"/registerFail.jsp");
		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}