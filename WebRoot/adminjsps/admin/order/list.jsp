<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>订单列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
    <link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/order/list.css'/>" />
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>" />

  </head>
  
  <body>
<p class="pLink">
  <a href="<c:url value='/adminjsps/admin/order/list.jsp'/>">未付款</a>  | 
  <a href="<c:url value='/adminjsps/admin/order/list.jsp'/>">已付款</a>  | 
  <a href="<c:url value='/adminjsps/admin/order/list.jsp'/>">已发货</a>  | 
  <a href="<c:url value='/adminjsps/admin/order/list.jsp'/>">交易成功</a>  | 
  <a href="<c:url value='/adminjsps/admin/order/list.jsp'/>">已取消</a>
</p>
<div class="divMain">
	<div class="title">
		<div style="margin-top:7px;">
			<span style="margin-left: 150px;margin-right: 280px;">商品信息</span>
			<span style="margin-left: 40px;margin-right: 100px;">金额</span>
			<span style="margin-left: 50px;margin-right: 53px;">订单状态</span>
			<span style="margin-left: 100px;">操作</span>
		</div>
	</div>
	<br/>
	<table align="center" border="0" width="100%" cellpadding="0" cellspacing="0">
	
	
	
	
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>








		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
		<tr class="tt">
			<td width="320px">订单号：<a  href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">E3A1EB6D0543489F9729B2B5BC5DB365</a></td>
			<td width="200px">下单时间：2013-06-01 19:30:22</td>
			<td width="178px">&nbsp;</td>
			<td width="205px">&nbsp;</td>
			<td>&nbsp;</td>
		</tr>
		<tr style="padding-top: 10px; padding-bottom: 10px;">
			<td colspan="2">

	<img border="0" width="70" src="<c:url value='/book_img/23254532-1_b.jpg'/>"/>

			</td>
			<td style="padding-left: 0">
				<span class="price_t">&yen;203.5</span>
			</td>
			<td>
				等待付款
<!-- 
				准备发货
				等待确认
				交易成功
				取消
 -->
			</td>
			<td>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">查看</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">取消</a><br/>
				<a href="<c:url value='/adminjsps/admin/order/desc.jsp'/>">发货</a>
			</td>
		</tr>
	</table>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>
  </body>
</html>
