<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>book_main.jsp</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/book/main.css'/>">
  </head>
  
  <body>
<table class="table" align="center" width="100%" height="100%" border="0">
	<tr style="height: 120px; ">
		<td align="center" width="200px;">
			<iframe frameborder="0" src="<c:url value='/admin/AdminBookServlet?method=findCategoryAll'/>" name="left"></iframe>
		</td>
		<td>
			<iframe frameborder="0" src="<c:url value='/adminjsps/admin/book/body.jsp'/>" name="body"></iframe>
		</td>
	</tr>
</table>
  </body>
</html>
