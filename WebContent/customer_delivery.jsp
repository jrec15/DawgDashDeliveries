<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII" isELIgnored="false"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<title>Delivery</title>
	</head>
	<body>
		<c:if test="${empty delivery}">
			<h1>Error: No such delivery or not authorized</h1>
		</c:if>
		<c:if test="${not empty delivery}">
			<h1>Delivery ${delivery.iD}</h1>
			<p>Description: ${delivery.clientComment}</p><br>
			<p>Date: ${delivery.timeCompleted}</p><br>
			<p>Price: ${delivery.price}</p><br>
			<p>Carrier comments: ${delivery.workerComment}</p>
		</c:if>
	</body>
</html>