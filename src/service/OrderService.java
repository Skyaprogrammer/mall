package service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import dao.OrderDao;
import domain.Product;
import utils.DataSourceUtils;
import vo.Order;
import vo.OrderItem;
import vo.PageBean;

public class OrderService {

	/*
	 * 提交订单的代码
	 * 注意这里要利用事务操作，以免用户取消订单还进入了数据库
	 */
	public void submitOrder(Order order) {
		//开启事务
		OrderDao dao = new OrderDao();
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();
			//2.调用Dao存储order的方法
			dao.addOrders(order);
			//3.调用Dao存储orderItem的方法
			dao.addOrderItems(order);
		
		}catch(SQLException e) {
			//如果有异常需要回滚数据
			try {
				DataSourceUtils.rollback();
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			try {
				DataSourceUtils.commitAndRelease();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
	}

	public void updateOrder(Order order, String oid) {
		OrderDao dao = new OrderDao();
		try {
			dao.updateOrder(order,oid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public void updateOrderState(String oid) {
		OrderDao dao = new OrderDao();
		try {
			dao.updateOrderState(oid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	public PageBean findAllOrdersByUid(int currentPage,int currentCount,String uid) {
		PageBean pageBean = new PageBean<>();
		
		OrderDao dao = new OrderDao();
		//在这里封装好pageBean返回给web层
		
		int index = (currentPage-1)*currentCount;//数据从0开始，所以不用加一
		//eg 0,4  4,4, 8,4
		int totalCount = 0;
		List<Order> list = null;
		try {
			totalCount = dao.getTotalCount(uid);
			//找集合的时候我们是点击一次找一下，所以要传入现在是哪一页，找对应的数据
			
			 list = dao.finOidOrderItemsByPage(uid,index,currentCount);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
//				int totalPage = (int) Math.floor(totalCount/currentCount);这是下取整，我们要上取整
		int totalPage = (int) Math.ceil(1.0*totalCount/currentCount);
		
		
		
		pageBean.setCurrentPage(currentPage);
		pageBean.setCurrentCount(currentCount);
		pageBean.setTotalCount(totalCount);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		
		return pageBean;
	}

	public List<Map<String, Object>> findAllOrderItemsByOid(String oid) {
		OrderDao dao = new OrderDao();
		List<Map<String, Object>> orderItemList = null;
		try {
			orderItemList = dao.findAllOrderItemsByOid(oid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return orderItemList;
	}

	public List<Order> findAllOrders() {
		OrderDao dao = new OrderDao();
		List<Order> list = null;
		try {
			list = dao.findAllOrders();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, Object>> findOrderInfoByOid(String oid) {
		
		OrderDao dao = new OrderDao();
		List<Map<String, Object>> orderItemList = null;
		try {
			orderItemList = dao.findOrderInfoByOid(oid);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return orderItemList;
	}

	
}
