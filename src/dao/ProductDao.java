package dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import domain.Product;
import utils.DataSourceUtils;

public class ProductDao {

	//获得最热商品 
	public List<Product> findHotProduct() throws SQLException{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "SELECT * FROM product WHERE is_hot = ? LIMIT ?,?";
		
		List<Product> list = qr.query(sql, new BeanListHandler<>(Product.class), 1,0,9);
		return list;
	}

	public List<Product> findNewProduct() throws SQLException{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "SELECT * FROM product ORDER BY pdate DESC LIMIT ?,?";
		
		List<Product> list = qr.query(sql, new BeanListHandler<>(Product.class), 0,9);
		return list;
	}

	public List<Product> findProductListByCid(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "SELECT * FROM product WHERE cid = ?";
		
		List<Product> list = qr.query(sql, new BeanListHandler<>(Product.class), cid);
		return list;
	}

	public int getTotalCount(String cid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT COUNT(*) from product WHERE cid = ?";
		
		Long query = (Long) qr.query(sql, new ScalarHandler(), cid);
		return query.intValue();
		
	}

	public List<Product> findProductListByPage(String cid, int index, int currentCount) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "SELECT * FROM product WHERE cid = ? limit ?,?";
		
		List<Product> list = qr.query(sql, new BeanListHandler<>(Product.class), cid,index,currentCount);
		return list;
	}

	public Product findProductByPid(String pid) throws SQLException {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "SELECT * FROM product WHERE pid = ?";
		
		Product product = qr.query(sql, new BeanHandler<>(Product.class), pid);
		
		return product;
		
		
	}

	public List<Product> getAllProducts() throws SQLException {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
				
		String sql = "SELECT * FROM product";
		
		List<Product> list = qr.query(sql, new BeanListHandler<>(Product.class));
		return list;
	}

	public void addProduct(Product product) throws SQLException {

		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		runner.update(sql, product.getPid(),product.getPname(),product.getMarket_price(),
					product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
					product.getPdesc(),product.getPflag(),product.getCategory().getCid());
	}

	public void deleteProduct(String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "DELETE FROM product WHERE pid = ?";
		
		runner.update(sql, pid);
	}

	public void updateProduct(Product product, String pid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE product SET pname = ?,market_price=?,shop_price=?,pimage=?,pdate=?,is_hot=?"
				+ "pdesc=?,pflag=?,cid=? WHERE pid =?";
		runner.update(sql, product.getPname(),product.getMarket_price(),
					product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),
					product.getPdesc(),product.getPflag(),product.getCategory().getCid(),product.getPid());
	}

	public Product findProductByPname(String pname) throws SQLException {
		
		QueryRunner qr  = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE pname = ?";
		
		return qr.query(sql, new BeanHandler<>(Product.class), pname);
	}

	public List<Object> findProductByWord(String word) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM product WHERE pname like ? limit 0,4";
		String pname  = "%"+word+"%";
		List<Object> list = qr.query(sql, new ColumnListHandler("pname"), pname);
		//太多会导致div不够，限制条数
		//把product返回太浪费，直接返回名字即可
		return list;
	}

}
