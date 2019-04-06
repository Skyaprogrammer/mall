package service;

import java.sql.SQLException;
import java.util.List;

import dao.ProductDao;
import domain.Product;
import vo.PageBean;

public class ProductService {

	private static ProductDao dao = new ProductDao();
	public List<Product> findHotProduct() {
		List<Product> list = null;
		try {
			list = dao.findHotProduct();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Product> findNewProduct() {
		
		List<Product> list = null;
		try {
			list = dao.findNewProduct();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public PageBean findProductListByCid(int currentPage,int currentCount,String cid){
		PageBean pageBean = new PageBean<>();
		
		
		//在这里封装好pageBean返回给web层
		
		int index = (currentPage-1)*currentCount;//数据从0开始，所以不用加一
		//eg 0,4  4,4, 8,4
		int totalCount = 0;
		List<Product> list = null;
		try {
			totalCount = dao.getTotalCount(cid);
			//找集合的时候我们是点击一次找一下，所以要传入现在是哪一页，找对应的数据
			
			 list = dao.findProductListByPage(cid,index,currentCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
//		int totalPage = (int) Math.floor(totalCount/currentCount);这是下取整，我们要上取整
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		
		
		
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		
		return pageBean;
		
		
	}

	public Product findProductByPid(String pid) {
		
		Product product = null;
		try {
			product = dao.findProductByPid(pid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return product;
	}

	public List<Product> getAllProducts() {
		
		List<Product> list = null;
		try {
			list = dao.getAllProducts();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	public void addProduct(Product product) {
		try {
			dao.addProduct(product);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	public void deleteProduct(String pid) {
			
		try {
			dao.deleteProduct(pid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public void updateProduct(Product product, String pid) {
		try {
			dao.updateProduct(product,pid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public Product findProductByPname(String pname) {
		Product product = null;
		try {
			product = dao.findProductByPname(pname);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return product;
	}

	public List<Object> findProductByWord(String word) {
		
		List<Object> list = null;
		
		try {
			list = dao.findProductByWord(word);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
	}

}
