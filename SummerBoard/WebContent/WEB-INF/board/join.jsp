<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<link href="<%=request.getContextPath()%>/css/main.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.1.js">
</script>
<c:if test="${errCode == null }">
	<c:set var="errCode" value="\"\""></c:set>
</c:if>
<script type="text/javascript">
	function errCodeCheck() {
				
		var errCode = ${errCode};
		if(errCode != null || errCode != "") {
			switch(errCode){
			case 1:
				alert("�̹� ���Ե� �̸��� �ּ��Դϴ�");
				break;
			
			case 2:
				alert("ȸ������ ó���� �����Ͽ����ϴ�. ��� �� �ٽ� �õ��� �ֽʽÿ�.");
				break;
			}
		}
	}
		

	function passwordCheck() {
			
		if($("#userPw").val() != $("#userPwCheck").val()) {
			alert("�н����� �Է��� ��ġ���� �ʽ��ϴ�.");
			$("#userPwCheck").focus();
			return false;
		}
		return true;
	} 
</script>

</head>
<body onload="errCodeCheck()">


<div class="wrapper">
	<h3>ȸ�� ����</h3>
	<spring:hasBindErrors name="MemberModel"/>
	<form:errors path="MemberModel"/>
	<form action="join.do" method="post" onsubmit="return passwordCheck()">
		<fieldset>
			<label for="userId">&nbsp;�����ּ� :</label>
			<input type="text" id="userId" name="userId" class="loginInput"/>
			<span class="error"><form:errors path="MemberModel.userId"/></span><br/>
			
			<label for="userPw">&nbsp;��й�ȣ :</label>
			<input type="password" id="userPw" name="userPw" class="loginInput"/>
			<span class="error"><form:errors path="MemberModel.userPw"/></span><br/>
			
			<label for="userPwCheck">&nbsp;��й�ȣ Ȯ�� :</label>
			<input type="password" id="userPwCheck" name="userPwCheck" class="loginInput"/><br/>
			
			<label for="userName">&nbsp;ȸ���̸� : </label>
			<input type="text" id="userName" name="userName" class="loginInput"/>
			<span class="error"><form:errors path="MemberModel.userName"/></span><br/><br/>
			
			<center>
			<input type="submit" value="Ȯ��" class="submitBt"/>
			<input type="reset" value="���ۼ�" class="submitBt"/><br/><br/>
			
			<a href="<%=request.getContextPath() %>/login.do">�α��� �������� ���ư���</a>
			</center>
		</fieldset>
	</form>
</div>

</body>
</html>