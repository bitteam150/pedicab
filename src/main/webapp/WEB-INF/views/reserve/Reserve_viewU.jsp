<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="../commons/Header.jsp" %>
<h1>내예약</h1>
<c:forEach items="${list}" var="reserve">
<div>${reserve.rmemo}</div>
</c:forEach>
</body>
</html>