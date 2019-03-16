<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单详细</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/order/desc.css'/>">
  </head>
  
<body>
	<div class="divOrder">
		<span>订单号：${oder.oid }
				(等待付款)
<!-- 
				(准备发货)
				(等待确认)
				(交易成功)
				(已取消)
 -->
		　　　下单时间：2013-06-01 19:30:22
		</span>
	</div>
	<div class="divRow">
		<div class="divContent">
			<dl>
				<dt>收货人信息</dt>
				<dd>北京市 昌平区 西三旗 金燕龙办公楼1层 传智播客 张三爷</dd>
			</dl>
		</div>
		<div class="divContent">
			<dl>
				<dt>商品清单</dt>
				<dd>
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th class="tt">商品名称</th>
							<th class="tt" align="left">单价</th>
							<th class="tt" align="left">数量</th>
							<th class="tt" align="left">小计</th>
						</tr>





						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>
								  Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）
								</div>
							</td>
							<td class="td" >
								<span>&yen;40.7</span>
							</td>
							<td class="td">
								<span>1</span>
							</td>
							<td class="td">
								<span>&yen;40.7</span>
							</td>			
						</tr>




						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>
								  Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）
								</div>
							</td>
							<td class="td" >
								<span>&yen;40.7</span>
							</td>
							<td class="td">
								<span>1</span>
							</td>
							<td class="td">
								<span>&yen;40.7</span>
							</td>			
						</tr>
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>
								  Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）
								</div>
							</td>
							<td class="td" >
								<span>&yen;40.7</span>
							</td>
							<td class="td">
								<span>1</span>
							</td>
							<td class="td">
								<span>&yen;40.7</span>
							</td>			
						</tr>
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>
								  Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）
								</div>
							</td>
							<td class="td" >
								<span>&yen;40.7</span>
							</td>
							<td class="td">
								<span>1</span>
							</td>
							<td class="td">
								<span>&yen;40.7</span>
							</td>			
						</tr>
						<tr style="padding-top: 20px; padding-bottom: 20px;">
							<td class="td" width="400px">
								<div class="bookname">
								  <img align="middle" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>
								  Spring实战(第3版)（In Action系列中最畅销的Spring图书，近十万读者学习Spring的共同选择）
								</div>
							</td>
							<td class="td" >
								<span>&yen;40.7</span>
							</td>
							<td class="td">
								<span>1</span>
							</td>
							<td class="td">
								<span>&yen;40.7</span>
							</td>			
						</tr>							
							
							
							
					</table>
				</dd>
			</dl>
		</div>
		<div class="divBtn">
			<span class="spanTotal">合　　计：</span>
			<span class="price_t">&yen;203.5</span><br/>

	<a id="deliver" href="javascript:alert('订单发货成功！')">发　　货</a>
	<a id="cancel" href="javascript:alert('订单取消成功！')">取　　消</a>

		</div>
	</div>
</body>
</html>

