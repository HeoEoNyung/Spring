<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<c:if test="${errCode == null }">
	<c:set var="errCode" value="\"\""></c:set>
</c:if>
<script type="text/javascript">
	function checkErrCode() {
		var errCode = ${errCode};
		if(errCode != null || errCode != "") {
			switch(errCode) {
			case 1:
				alert("���Ե� �̸��� �ּҰ� �ƴմϴ�!");
				break;
			
			case 2:
				alert("��й�ȣ�� ��ġ���� �ʽ��ϴ�");
				break;
				
			case 3:
				alert("ȸ������ ó���� �Ϸ�Ǿ����ϴ�! �α��� �� �ּ���!");
				location.href = "<%=request.getContextPath()%>/login.do";
				break;	
			}
		}
	}
</script>
<body onload="checkErrCode()">
<div class="wrapper">
<h3>������ �Խ���</h3>
	<spring:hasBindErrors name="LoginModel"/>
	<form:errors path="LoginModel"/>
	<form action="login.do" method="post">
		<fieldset>
			<label for="userId">�����ּ� : </label>
			<input type="text" id="userId" name="userId" class="loginInput" value="${userId }"/>
			<span class="error"><form:errors path="LoginModel.userId"/></span><br/>
			
			<label for="userPw">��й�ȣ : </label>
			<input type="text" id="userPw" name="userPw" class="loginInput" value="${userId }"/>
			<span class="error"><form:errors path="LoginModel.userPw"/></span><br/><br/>
			
			<center>
			<input type="submit" value="�α���" class="submitBt"/><br/><br/>
			<a href="<%=request.getContextPath() %>/member/join.do">ȸ�� ����</a>
			</center>
		</fieldset>
	</form>
</div>

</body>
</html>