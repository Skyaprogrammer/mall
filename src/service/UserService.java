package service;

import java.sql.SQLException;

import dao.UserDao;
import domain.User;

public class UserService {

	private static UserDao dao = new UserDao();
	public boolean register(User user) {
		
		int row=0;
		try {
			row = dao.register(user);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		boolean isRegisterSuccess = false;
		if(row>0) {
			isRegisterSuccess = true;
		}
		return isRegisterSuccess;
	}
	public void active(String activeCode) {
		
		try {
			dao.active(activeCode);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public boolean checkUsername(String username) {
		//校验用户名
		Long isExist = 0L;
		try {
			isExist = dao.checkUsername(username);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return isExist>0?true:false;
	}
	public User login(String username, String password) {
		User user = null;
		try {
			user  = dao.login(username,password);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return user;
	}

}
