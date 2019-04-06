package dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import domain.Category;
import utils.DataSourceUtils;

public class CategoryDao {

	public List<Category> getCategorys() throws SQLException {
		QueryRunner qr  = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "SELECT * FROM category";
		
		return qr.query(sql, new BeanListHandler<>(Category.class));
	}

	public void addCategory(String cid, String cname) throws SQLException {
		QueryRunner qr = new  QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "INSERT INTO category VALUES(?,?)";
		
		qr.update(sql, cid,cname);
	}

	public void deleteCategory(String cid) throws SQLException {
		
		QueryRunner qr = new  QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "DELETE FROM category WHERE cid = ?";
		
		qr.update(sql, cid);
	}

	public Category findCategoryByCid(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "SELECT * FROM category WHERE cid = ?";
		
		return  qr.query(sql, new BeanHandler<>(Category.class), cid);
	}

	public void updateCategory(String cid, String cname) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "UPDATE category SET cname = ? WHERE cid = ?";
		
		qr.update(sql, cname,cid);
	}

}
