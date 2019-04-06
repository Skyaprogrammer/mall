<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>

<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>会员登录</title>
<link rel="stylesheet" href="css/bootstrap.min.css" type="text/css" />
<script src="js/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="js/bootstrap.min.js" type="text/javascript"></script>
<!-- 引入自定义css文件 style.css -->
<link rel="stylesheet" href="css/style.css" type="text/css" />

<style>
body {
	margin-top: 20px;
	margin: 0 auto;
}

.carousel-inner .item img {
	width: 100%;
	height: 300px;
}
</style>
</head>

<body>


	<!-- 引入header.jsp -->
	<jsp:include page="/header.jsp"></jsp:include>

	<div class="container">
		<div class="row">
			<div style="margin: 0 auto; margin-top: 10px; width: 950px;">
				<strong>我的订单</strong>
				<table class="table table-bordered">
				<!-- 每个tbody一个订单 -->
					<c:forEach items="${pageBean.list }" var="orderList">
					<tbody>
						
						<tr class="success">
							<th colspan="5">订单编号:${orderList.oid }&nbsp;&nbsp;${order.state==0?"未付款":"已付款" }</th>
						</tr>
						<tr class="warning">
							<th>图片</th>
							<th>商品</th>
							<th>价格</th>
							<th>数量</th>
							<th>小计</th>
						</tr>
						<c:forEach items="${orderList.orderItems }" var="orderItem">
						<tr class="active">
							<td width="60" width="40%"><input type="hidden" name="id"
								value="22"> <img src="${pageContext.request.contextPath }/${orderItem.product.pimage }" width="70"
								height="60"></td>
							<td width="30%"><a target="_blank">${orderItem.product.pname }</a></td>
							<td width="20%">￥:${orderItem.product.shop_price }</td>
							<td width="10%">${orderItem.count }</td>
							<td width="15%"><span class="subtotal">￥:${orderItem.subTotal }</span></td>
						</tr>
						</c:forEach>
					</tbody>
					</c:forEach>
					
				</table>
			</div>
		</div>
		<!--分页 -->
		<div style="width: 380px; margin: 0 auto; margin-top: 50px;">
			<ul class="pagination" style="text-align: center; margin-top: 10px;">
				<!-- 上一页 -->
				<c:if test="${pageBean.currentPage==1 }">
					<li class="disabled">
						<a href="javascript:void(0);" aria-label="Previous">
							<span aria-hidden="true">&laquo;</span>
						</a>
					</li>
				</c:if>
				<c:if test="${pageBean.currentPage!=1 }">
				<li>
					<a href="${pageContext.request.contextPath }/getOrderList?currentPage=${pageBean.currentPage-1 }" aria-label="Previous">
						<span aria-hidden="true">&laquo;</span>
					</a>
				</li>
				</c:if>
				
				<!-- 显示每一页 -->
				
				<c:forEach begin="1" end="${pageBean.totalPage }" var="page">
					<!-- 要每次点击的时候判断是否是当前页,是就选中状态，不是就可以点击 -->
					
					<c:if test="${page==pageBean.currentPage }">
						<!-- 当前页已经选中，不能在进行点击操作 -->
						<li class="active"><a href="javascript:void(0);">${page }</a></li>
					</c:if>
					<!-- 不是当前页时可以点击 -->
					<c:if test="${page!=pageBean.currentPage }">
						<!-- 当前页已经选中，不能在进行点击操作 -->
						<li><a href="${pageContext.request.contextPath }/getOrderList?currentPage=${page }">${page }</a></li>
					</c:if>
				</c:forEach>
				<!-- 下一页 -->
				<c:if test="${pageBean.currentPage==pageBean.totalPage }">
				<li class="disabled">
					<a href="javascript:void(0);" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li> 
				</c:if>
				<c:if test="${pageBean.currentPage!=pageBean.totalPage }">
				<li>
					<a href="${pageContext.request.contextPath }/getOrderList?currentPage=${pageBean.currentPage+1 }" aria-label="Next">
						<span aria-hidden="true">&raquo;</span>
					</a>
				</li> 
				</c:if>
				
				
			</ul>
		</div>
	</div>

	<!-- 引入footer.jsp -->
	<jsp:include page="/footer.jsp"></jsp:include>
	
</body>

</html>