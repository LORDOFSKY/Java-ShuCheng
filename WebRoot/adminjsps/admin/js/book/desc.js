$(function () {
	// 日期框
	$("#publishtime").datepick({dateFormat:"yy-mm-dd"});
	$("#printtime").datepick({dateFormat:"yy-mm-dd"});

	// 编辑和删除按钮样式
	$("#editBtn").addClass("editBtn1");
	$("#delBtn").addClass("delBtn1");
	$("#editBtn").hover(
		function() {
			$("#editBtn").removeClass("editBtn1");
			$("#editBtn").addClass("editBtn2");
		},
		function() {
			$("#editBtn").removeClass("editBtn2");
			$("#editBtn").addClass("editBtn1");
		}
	);
	$("#delBtn").hover(
		function() {
			$("#delBtn").removeClass("delBtn1");
			$("#delBtn").addClass("delBtn2");
		},
		function() {
			$("#delBtn").removeClass("delBtn2");
			$("#delBtn").addClass("delBtn1");
		}
	);
});



function editForm() {
	var bname = $("#bname").val();
	var currPrice = $("#currPrice").val();
	var price = $("#price").val();
	var discount = $("#discount").val();
	var author = $("#author").val();
	var press = $("#press").val();
	var pid = $("#pid").val();
	var cid = $("#cid").val();
	
	if(!bname || !currPrice || !price || !discount || !author || !press || !pid || !cid) {
		alert("图名、当前价、定价、折扣、作者、出版社、1级分类、2级分类不能为空！");
		return false;
	}
	
	if(isNaN(currPrice) || isNaN(price) || isNaN(discount)) {
		alert("当前价、定价、折扣必须是合法小数！");
		return false;
	}
	
	var method = $("<input>").attr("type", "hidden").attr("name", "method").attr("value", "edit");
	$("#form").append(method);
	$("#form").submit();
}

function delForm() {
	var method = $("<input>").attr("type", "hidden").attr("name", "method").attr("value", "delete");
	$("#form").append(method);
	$("#form").submit();
}