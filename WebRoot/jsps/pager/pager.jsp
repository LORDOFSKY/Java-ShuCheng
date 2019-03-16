<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	function _go() {
		var pc = $("#pageCode").val();//获取文本框中的当前页码
		if(!/^[1-9]\d*$/.test(pc)) {//对当前页码进行整数校验
			alert('请输入正确的页码！');
			return;
		}
		if(pc > ${pb.tp}) {//判断当前页码是否大于最大页
			alert('请输入正确的页码！');
			return;
		}
		location = "${pb.url}&pc=" + pc;
	}
</script>


<div class="divBody">
	<div class="divContent">
		<%--上一页 --%>
		<c:choose>
			<c:when test="${pb.pc eq 1}">
				<span class="spanBtnDisabled">上一页</span>
			</c:when>
			<c:otherwise>
				<a href="${pb.url }&pc=${pb.pc-1}" class="aBtn bold">上一页</a>
			</c:otherwise>
		</c:choose>



		<%--我们需要计算页码列表的开始和结束位置，即两个变量begin和end
计算它们需要通过当前页码！
1. 总页数不足6页--> begin=1, end=最大页
2. 通过公式设置begin和end，begin=当前页-1，end=当前页+3
3. 如果begin<1，那么让begin=1，end=6
4. 如果end>tp, 让begin=tp-5, end=tp   --%>
 <c:choose>
 	<c:when test="${pb.tp <= 6 }">
 		<c:set var="begin" value="1"/>
 		<c:set var="end" value="${pb.tp }"/>
 	</c:when>
 	<c:otherwise>
 		<c:set var="begin" value="${pb.pc-2 }"/>
 		<c:set var="end" value="${pb.pc + 3}"/>
 		<c:if test="${begin < 1 }">
 		  <c:set var="begin" value="1"/>
 		  <c:set var="end" value="6"/>
 		</c:if>
 		<c:if test="${end > pb.tp }">
 		  <c:set var="begin" value="${pb.tp-5 }"/>
 		  <c:set var="end" value="${pb.tp }"/>
 		</c:if> 		
 	</c:otherwise>
 </c:choose>

			<%--遍历输出--%>

		<c:forEach begin="${begin}" end="${end}" var="i">
			<c:choose>
				<c:when test="${i eq pb.pc}">
					<span class="spanBtnSelect">${i }</span>
				</c:when>
				<c:otherwise>
					<a href="${pb.url }&pc=${i}" class="aBtn">${i }</a>
				</c:otherwise>
			</c:choose>
		</c:forEach>




		<%-- 显示页码列表 
          <span class="spanBtnSelect">1</span>
          <a href="" class="aBtn">2</a>
          <a href="" class="aBtn">3</a>
          <a href="" class="aBtn">4</a>
          <a href="" class="aBtn">5</a>
          <a href="" class="aBtn">6</a>
    --%>


		<%-- 显示点点点 --%>
		<c:if test="${end < pb.tp }">
			<span class="spanApostrophe">...</span>
		</c:if>


		<%--下一页 --%>
		<c:choose>
			<c:when test="${pb.pc eq pb.tp }">
				<span class="spanBtnDisabled">下一页</span>
			</c:when>
			<c:otherwise>
				<a href="${pb.url }&pc=${pb.pc+1}" class="aBtn bold">下一页</a>
			</c:otherwise>
		</c:choose>


		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

		<%-- 共N页 到M页 --%>
		<span>共${pb.tp}页</span> <span>到</span> <input type="text"
			class="inputPageCode" id="pageCode" value="${pb.pc }" /> <span>页</span>
		<a href="javascript:_go();" class="aSubmit">确定</a>
	</div>
</div>