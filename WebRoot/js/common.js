function _change() {
	$("#vCode").attr("src", "/goods/VerifyCodeServlet?" + new Date().getTime());
}