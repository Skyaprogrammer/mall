package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import utils.DataSourceUtils;
import vo.Order;
import vo.OrderItem;

public class OrderDao {

	public void addOrders(Order order) throws SQLException{
		//插入order数据
		//这里不要获取池，我们保持事务的话一定要conn一致才能控制
		QueryRunner runner = new QueryRunner();
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Connection conn = DataSourceUtils.getConnection();
		runner.update(conn,sql, order.getOid(),order.getOrderTime(),order.getTotal(),order.getState(),
				order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid());
	}

	public void addOrderItems(Order order) throws SQLException{
		QueryRunner qr = new QueryRunner();
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		List<OrderItem> orderItems = order.getOrderItems();
		Connection conn = DataSourceUtils.getConnection();
		for (OrderItem orderItem : orderItems) {
			
			qr.update(conn, sql, orderItem.getItmid(),orderItem.getCount(),orderItem.getSubTotal(),
								orderItem.getProduct().getPid(),orderItem.getOrder().getOid());
		}
	}

	public void updateOrder(Order order, String oid) throws SQLException {
		//这里不使用事务
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE orders	set address=?,name=?,telephone=? WHERE oid=?";
		qr.update(sql, order.getAddress(),order.getName(),order.getTelephone(),oid);
	}

	public void updateOrderState(String oid) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "UPDATE orders SET state = ? WHERE oid = ?";
				
		qr.update(sql, 1,oid);
	}

	public List<Order> finOidOrderItemsByPage(String uid,int index,int currentCount) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM orders WHERE uid=? limit ?,?";
		
		return qr.query(sql, new BeanListHandler<>(Order.class), uid,index,currentCount);
				
	}

	public List<Map<String, Object>> findAllOrderItemsByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select i.count,i.subtotal,p.pimage,p.pname,p.shop_price from orderitem i,product p where i.pid=p.pid and i.oid=?";
		List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
		return mapList;
	}

	public int getTotalCount(String uid) throws SQLException {
		
		//获得该用户的所有记录
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT count(*) from orders where uid=?";
		
		Long query = (Long) qr.query(sql, new ScalarHandler(), uid);
		return query.intValue();
	}

	public List<Order> findAllOrders() throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "SELECT * FROM orders";
		
		return qr.query(sql, new BeanListHandler<>(Order.class));
	}

	public List<Map<String, Object>> findOrderInfoByOid(String oid) throws SQLException {
		QueryRunner runner = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select p.pimage,p.pname,p.shop_price,i.count,i.subtotal from orderitem i,product p where i.pid=p.pid and i.oid=?";
		List<Map<String, Object>> mapList = runner.query(sql, new MapListHandler(), oid);
		return mapList;
	}
	

}
