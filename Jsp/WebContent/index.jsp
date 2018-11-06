<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>   
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>servlet</title>
</head>
<body>
<form action="ms" method="post">
	姓名：<input type="text" name="username"><br>
	密码：<input type="password" name="password">
	<input type="submit" value="提交"/>
	<%
		int i = 0;
		Map<String,String> map = new HashMap<>();
		map.put("name", "mike");
		map.put("na", "lusy");
	%>
	<%=map.get("name") %>
	<c:forEach var="attr" items="${request.name }" varStatus="status">
		<c:out value="${status.count}" escapeXml="true" default="haha"></c:out>
		<c:out value="${status.current.name}" escapeXml="true" default="haha"></c:out>
		
	</c:forEach>
	
	<c:import url="https://1111.tmall.com/"></c:import>
	
</form>
</body>
</html>