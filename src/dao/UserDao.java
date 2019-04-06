package dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import domain.User;
import utils.DataSourceUtils;

public class UserDao {

	
	public int register(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(new DataSourceUtils().getDataSource());
		
		String sql = "INSERT INTO user values(?,?,?,?,?,?,?,?,?,?)";
		
		int row = qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),
					   user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode());
		
		return row;
	}

	public void active(String activeCode) throws SQLException {
		
		QueryRunner qr = new QueryRunner(new DataSourceUtils().getDataSource());
		//直接更新数据条件是code匹配
		String sql = "UPDATE user set state = ? WHERE code = ?";
		
		qr.update(sql, 1,activeCode);
		
		
		
	}

	public Long checkUsername(String username) throws SQLException {
		QueryRunner qr = new QueryRunner(new DataSourceUtils().getDataSource());
		
		String sql = "SELECT count(*) from user WHERE username = ?";
		
		Long query = (Long) qr.query(sql, new ScalarHandler(), username);
		
		return query;
	}

	//用户登录的方法
	public User login(String username, String password) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		return runner.query(sql, new BeanHandler<User>(User.class), username,password);
	}


}
