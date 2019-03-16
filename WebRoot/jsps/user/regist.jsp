<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>注册页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/jsps/css/user/regist.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jquery/jquery-1.5.1.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/jsps/js/user/regist.js"></script>

</head>

<body>
	<div id="divmain">
		<div id="divTitle">
			<span id="spanTitle">新用户注册</span>
		</div>
		<div id="divBody">
			<form action="${pageContext.request.contextPath}/UserServlet"
				method="post">
				<input type="hidden" name="method" value="regist">
				<table id="tableForm">
					<tr>
						<td class="tdText">用户名：</td>
						<td class="tdInput"><input class="inputClass" type="text"
							name="loginname" id="loginname" value="${form.loginname}" /></td>
						<td class="tdError"><label class="errorClass"
							id="loginnameError">${errors.loginname}</label></td>
					</tr>
					<tr>
						<td class="tdText">登录密码：</td>
						<td><input class="inputClass" type="password"
							name="loginpass" id="loginpass" value="${form.loginpass}" /></td>
						<td><label class="errorClass" id="loginpassError">${errors.loginpass}</label></td>
					</tr>
					<tr>
						<td class="tdText">确认密码：</td>
						<td><input class="inputClass" type="password"
							name="reloginpass" id="reloginpass" value="${form.reloginpass}" /></td>
						<td><label class="errorClass" id="reloginpassError">${errors.reloginpass}</label></td>
					</tr>
					<tr>
						<td class="tdText">Email：</td>
						<td><input class="inputClass" type="text" name="email"
							id="email" value="${form.email}" /></td>
						<td><label class="errorClass" id="emailError">${errors.email}</label></td>
					</tr>
					<tr>
						<td class="tdText">图形验证码：</td>
						<td><input class="inputClass" type="text" name="verifyCode"
							id="verifyCode" value="${form.verifyCode}" /></td>
						<td><label class="errorClass" id="verifyCodeError">${errors.verifyCode}</label></td>
					</tr>
					<tr>
						<td></td>
						<td>
							<div id="divVerifyCode">
								<img id="imgVerifyCode"
									src="${pageContext.request.contextPath}/VerifyCodeServlet" />
							</div>
						</td>
						<td><label><a href="javascript:_hyz()">换一张</a></label></td>
					</tr>
					<tr>
						<td></td>
						<td><input type="image"
							src="${pageContext.request.contextPath}/images/regist1.jpg"
							id="submitBtn" /></td>
						<td><label></label></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>
