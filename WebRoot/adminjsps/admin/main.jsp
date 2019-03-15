<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>main.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="content-type" content="text/html;charset=utf-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/main.css'/>">
  </head>
  
  <body>
<table class="table" align="center">
	<tr>
		<td colspan="2" align="center" height="100px;">
			<iframe frameborder="0" src="<c:url value='/adminjsps/admin/top.jsp'/>" name="top"></iframe>
		</td>
	</tr>
	<tr>
		<td>
			<iframe frameborder="0" src="<c:url value='/adminjsps/admin/body.jsp'/>" name="body"></iframe>
		</td>
	</tr>
</table>
  </body>
</html>
