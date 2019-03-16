
$(function() {
	/**
	 * 得到错误信息，遍历，调用方法来判断是否显示信息；
	 */
	$(".errorClass").each(function() {
		showError($(this)); //遍历所有信息，使用每一个元素调用showError方法
	});



	/**
	 * 按钮事件-切帧
	 */
	$("#submitBtn").hover(
		function() {
			$("#submitBtn").attr("src", "/goods/images/regist2.jpg");
		},
		function() {
			$("#submitBtn").attr("src", "/goods/images/regist1.jpg");
		}
	);



	/**
	 * 输入框得到焦点，隐藏错误信息；
	 * 
	 */
	$(".inputClass").focus(
		function() {
			var labelId = $(this).attr("id") + "Error"; //通过输入栏找到对应lable的id
			$("#" + labelId).text(""); //将lable内容清空
			showError($("#" + labelId)); //隐藏没有信息的lable
		}
	)


	/**
	 * 失去焦点校验信息
	 * 
	 */
	$(".inputClass").blur(
		function() {
			var id = $(this).attr("id"); //获取当前输入框的ID
			var funName = "validate" + id.substring(0, 1).toUpperCase() + id.substring(1) + "()"; //得到对应的校验函数名
			eval(funName); //将字符串当成代码执行
		}
	)

	/**
	 * 表单提交时校验
	 * 
	 */
	$("#registForm").submit(
		function() {
			var bool = true;
			if (!validateLoginname()) {
				return false;
			}
			if (!validateLoginpass()) {
				return false;
			}
			if (!validateReloginpass()) {
				return false;
			}
			if (!validateEmail()) {
				return false;
			}
			if (!validateVerifyCode()) {
				return false;
			}
			return bool;
		}
	)


});
/**
 * 登录名校验方法
 */
function validateLoginname() {
	var id = "loginname";
	var value = $("#" + id).val(); //获取输入内容
	/*
	 * 1.非空校验
	 */
	if (!value) {
		/*
		 * 1.获取对应lable
		 * 2.添加错误信息
		 * 3.显示lable
		 */
		$("#" + id + "Error").text("用户名不能为空");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 *2.长度校验 
	 */
	if (value.length < 3 || value.length > 20) {
		$("#" + id + "Error").text("用户名长度必须在3~20之间!");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 3.是否注册校验
	 */
	$.ajax({
		url : "/goods/UserServlet",
		data : {
			method : "ajaxValidateLoginname",
			loginname : value
		},
		type : "POST",
		dataType : "json",
		async : false, //是否异步，若为异步，则不等服务器返回，这个函数就会向下运行
		cache : false, //是否缓存
		success : function(result) {
			if (!result) { //校验结果失败
				$("#" + id + "Error").text("用户名已被注册!");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});


	return true;
}

/**
 * 密码校验方法
 */
function validateLoginpass() {
	var id = "loginpass";
	var value = $("#" + id).val(); //获取输入内容
	/*
	 * 1.非空校验
	 */
	if (!value) {
		/*
		 * 1.获取对应lable
		 * 2.添加错误信息
		 * 3.显示lable
		 */
		$("#" + id + "Error").text("密码不能为空");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 *2.长度校验 
	 */
	if (value.length < 3 || value.length > 20) {
		$("#" + id + "Error").text("密码长度必须在3~20之间!");
		showError($("#" + id + "Error"));
		false;
	}

	return true;
}


/**
 *确认密码校验方法
 */
function validateReloginpass() {
	var id = "reloginpass";
	var value = $("#" + id).val(); //获取输入内容
	/*
	 * 1.非空校验
	 */
	if (!value) {
		/*
		 * 1.获取对应lable
		 * 2.添加错误信息
		 * 3.显示lable
		 */
		$("#" + id + "Error").text("确认密码不能为空");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 *2.两次输入是否一致校验 
	 */
	if (value != $("#loginpass").val()) {
		$("#" + id + "Error").text("两次输入不一致!");
		showError($("#" + id + "Error"));
		return false;
	}

	return true;
}

/**
 * Email校验方法  
 */
function validateEmail() {
	var id = "email";
	var value = $("#" + id).val(); //获取输入内容
	/*
	 * 1.非空校验
	 */
	if (!value) {
		/*
		 * 1.获取对应lable
		 * 2.添加错误信息
		 * 3.显示lable
		 */
		$("#" + id + "Error").text("Email不能为空");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 *2.Email格式校验（用正则表达式）
	 */
	if (!/^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/.test(value)) {
		$("#" + id + "Error").text("Email格式错误!");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 3.是否注册校验
	 */
	$.ajax({
		url : "/goods/UserServlet",
		data : {
			method : "ajaxValidateEmail",
			email : value
		},
		type : "POST",
		dataType : "json",
		async : false, //是否异步，若为异步，则不等服务器返回，这个函数就会向下运行
		cache : false, //是否缓存
		success : function(result) {
			if (!result) { //校验结果失败
				$("#" + id + "Error").text("Email已被注册!");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});

	return true;
}

/**
 * 验证码校验方法
 */
function validateVerifyCode() {
	var id = "verifyCode";
	var value = $("#" + id).val(); //获取输入内容
	/*
	 * 1.非空校验
	 */
	if (!value) {
		/*
		 * 1.获取对应lable
		 * 2.添加错误信息
		 * 3.显示lable
		 */
		$("#" + id + "Error").text("验证码不能为空");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 *2.长度校验 
	 */
	if (value.length != 4) {
		$("#" + id + "Error").text("错误的验证码!");
		showError($("#" + id + "Error"));
		return false;
	}
	/*
	 * 3.验证码是否正确
	 */
	$.ajax({
		url : "/goods/UserServlet",
		data : {
			method : "ajaxValidateVerifyCode",
			verifyCode:value
		},
		type : "POST",
		dataType : "json",
		async : false, //是否异步，若为异步，则不等服务器返回，这个函数就会向下运行
		cache : false, //是否缓存
		success : function(result) {
			if (!result) { //校验结果失败
				$("#" + id + "Error").text("请输入正确的验证码!");
				showError($("#" + id + "Error"));
				return false;
			}
		}
	});

	return true;
}





/**
 *判断当前元素是否存在，若存在则显示，不存在则不显示； 
 *
 */
function showError(ele) {
	var text = ele.text(); //获取元素内容
	if (!text) { //如果没有内容
		ele.css("display", "none"); //隐藏元素
	} else {
		ele.css("display", "");
	}
}



/**
*换一张验证码
*   步骤：
*   1.获取<img>元素；
*   2.重新设置它的src；
*   3.使用毫秒来添加参数；
*/
function _hyz() {
	$("#imgVerifyCode").attr("src", "/goods/VerifyCodeServlet?a=" + new Date().getTime());
}