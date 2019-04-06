package service;

import java.sql.SQLException;
import java.util.List;

import dao.CategoryDao;
import domain.Category;


public class CategoryService {

	CategoryDao dao = new CategoryDao();
	public List<Category> getCategorys() {
		List<Category> list = null;
		try {
			list = dao.getCategorys();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
	}
	public void addCategory(String cid, String cname) {
		try {
			dao.addCategory(cid,cname);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public void deleteCategory(String cid) {
		try {
			dao.deleteCategory(cid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public Category findCategoryByCid(String cid) {
		Category category = null;
		try {
			category = dao.findCategoryByCid(cid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return category;
	}
	public void updateCategory(String cid, String cname) {
		try {
			dao.updateCategory(cid,cname);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	
}
