package web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;

import service.OrderService;
import utils.PaymentUtil;
import vo.Order;

public class ConfirmOrder extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		//前台表单的数据进行获取，先更新数据库
		Map<String, String[]> map = request.getParameterMap();
		Order order = new Order();
		try {
			BeanUtils.populate(order, map);
		} catch (IllegalAccessException|InvocationTargetException e) {
			
			e.printStackTrace();
		} 
		//功能1：先完善order的信息
		String oid = request.getParameter("oid");
		OrderService service = new OrderService();
		service.updateOrder(order,oid);
		
		//功能2：在线支付
		//1.先获得选择的银行
		//必须先得到所有的必要数据
		HttpSession session = request.getSession();
		Order sorder = (Order) session.getAttribute("order");
		//1.oid
		//2.支付金额
//		String money = sorder.getTotal()+"";
		String money = "0.01";
		//3.选择的银行
		String pd_FrpId = request.getParameter("pd_FrpId");
		
		// 发给支付公司需要哪些数据
		String p0_Cmd = "Buy";		//只有固定值buy
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString(	//加载配置文件
				"p1_MerId");
		String p2_Order = oid;
		String p3_Amt = money;
		String p4_Cur = "CNY";//人民币
		String p5_Pid = "";		//值 可以非必须
		String p6_Pcat = "";	
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("callback");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
		// 生成url --- url?
		//重定向到支付平台
		String url = "https://www.yeepay.com/app-merchant-proxy/node?pd_FrpId="+pd_FrpId+
				"&p0_Cmd="+p0_Cmd+
				"&p1_MerId="+p1_MerId+
				"&p2_Order="+p2_Order+
				"&p3_Amt="+p3_Amt+
				"&p4_Cur="+p4_Cur+
				"&p5_Pid="+p5_Pid+
				"&p6_Pcat="+p6_Pcat+
				"&p7_Pdesc="+p7_Pdesc+
				"&p8_Url="+p8_Url+
				"&p9_SAF="+p9_SAF+
				"&pa_MP="+pa_MP+
				"&pr_NeedResponse="+pr_NeedResponse+
				"&hmac="+hmac;

		//重定向到第三方支付平台
		response.sendRedirect(url);

		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}